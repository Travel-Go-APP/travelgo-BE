package com.travelgo.backend.domain.userItems.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.dto.response.ItemResponse;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.domain.item.service.ItemService;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.userItems.dto.response.UserItemsResponse;
import com.travelgo.backend.domain.userItems.repository.UserItemsRepository;
import com.travelgo.backend.domain.util.entity.geo.service.GeoCodingService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserItemsService {

    @Autowired
    private final UserItemsRepository userItemsRepository;
    private final GeoCodingService geoCodingService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final Random rand = new Random();

    private static final List<String> AREAS = Arrays.asList(
            "Daejeon", "Ulsan", "Gyeonsangbukdo", "Gyeonsangnamdo",
            "Busan", "Gangwondo", "Sejong", "Jejudo", "Chungcheongbukdo",
            "Seoul", "Gwangju", "Jeollabukdo", "Jeollanamdo", "Incheon",
            "Gyeonggido", "Chungcheongnamdo", "Daegu", "Common"
    );

    @Transactional
    public Map<String, Object> add(String email, Double latitude, Double longitude) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 랜덤으로 아이템 rank 결정 (1~4 일반 아이템, 5는 지역 아이템)
        int rank = determineRandomItemRank();  // 아이템 확률 로직 적용
        Item randomItem;
        Map<String, Object> response = new HashMap<>();

        if (rank == 5) {
            // 지역 아이템 지급 (latitude와 longitude 전달)
            randomItem = acquireRegionalItem(latitude, longitude);

            UserItems userItems = userItemsRepository.findByUserAndItem(user, randomItem)
                    .orElse(null);

            int currentPieces = 0;

            if (userItems != null) {
                userItems.addPiece();
                userItemsRepository.save(userItems);
                currentPieces = userItems.getPiece();
                if (userItems.isCompleted()) {
                    Map<String, Integer> reward = rewardUser(user);
                    response.putAll(reward);
                }
                response.put("itemId", randomItem.getItemId());
                response.put("itemPiece", currentPieces);
            } else {
                userItems = UserItems.builder()
                        .user(user)
                        .item(randomItem)
                        .build();
                userItemsRepository.save(userItems);
                currentPieces = userItems.getPiece();

                response.put("itemId", randomItem.getItemId());
                response.put("itemPiece", currentPieces);
            }
        } else {
            // 일반 아이템 지급 로직
            randomItem = findRandomItemByRank(rank);
            UserItems userItems = userItemsRepository.findByUserAndItem(user, randomItem)
                    .orElse(null);

            int currentPieces = 0;

            if (userItems != null) {
                userItems.addPiece();
                userItemsRepository.save(userItems);
                currentPieces = userItems.getPiece();
                if (userItems.isCompleted()) {
                    Map<String, Integer> reward = rewardUser(user);
                    response.putAll(reward);
                }
                response.put("itemId", randomItem.getItemId());
                response.put("itemPiece", currentPieces);
            } else {
                userItems = UserItems.builder()
                        .user(user)
                        .item(randomItem)
                        .build();
                userItemsRepository.save(userItems);
                currentPieces = userItems.getPiece();

                response.put("itemId", randomItem.getItemId());
                response.put("itemPiece", currentPieces);
            }
        }
        return response;
    }

    private Item acquireRegionalItem(Double latitude, Double longitude) {
        String[] areaAndVisitArea = geoCodingService.reverseGeocode(latitude, longitude);
        String areaName = areaAndVisitArea[0];

        Area area = Area.fromString(areaName);
        List<Item> itemsInArea = itemRepository.findByArea(area);
        if (itemsInArea.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ITEM);
        }

        return itemsInArea.get(new Random().nextInt(itemsInArea.size()));
    }

    private Item findRandomItemByRank(int rank) {
        List<Item> items = itemRepository.findByItemRank(rank);
        if (items.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ITEM);
        }
        return items.get(new Random().nextInt(items.size()));
    }

    private int determineRandomItemRank() {
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;

        if (randomValue <= 50) {
            return 1;  // 50% 확률로 rank 1 아이템
        } else if (randomValue <= 75) {
            return 2;  // 25% 확률로 rank 2 아이템
        } else if (randomValue <= 90) {
            return 3;  // 15% 확률로 rank 3 아이템
        } else if (randomValue <= 95) {
            return 4;  // 5% 확률로 rank 4 아이템
        } else {
            return 5;  // 5% 확률로 rank 5 지역 아이템
        }
    }

    private Map<String, Integer> rewardUser(User user) {
        Map<String, Integer> reward = new HashMap<>();
        int rewardType = rand.nextInt(2);
        if (rewardType == 0) {
            int tgReward = rand.nextInt(1000) + 500;
            user.addTg(tgReward);
            reward.put("tg", tgReward);
        } else {
            int expReward = rand.nextInt(50) + 20;
            user.addExperience(expReward);
            reward.put("exp", expReward);
        }
        userRepository.save(user);
        return reward;  // 반환값을 null이 아닌 reward로 수정
    }


    // 아이템 수 설정
    private static final Map<String, Integer> AREA_ITEM_COUNT = new HashMap<>();

    // 아이템 갯수 설정
    static {
        for (String area : AREAS) {
            AREA_ITEM_COUNT.put(area, 2); // 각 지역별 4개의 아이템
        }
    }

    // 아이템 랭크별 수 설정
    private static final Map<String, Integer> ITEM_RANK_TOTAL_COUNT = new HashMap<>();

    // 랭크별 갯수 설정
    static {
        ITEM_RANK_TOTAL_COUNT.put("1", 14); // 일반 아이템 총 14개 Normal
        ITEM_RANK_TOTAL_COUNT.put("2", 8);    // 희귀 아이템 총 8개 Rare
        ITEM_RANK_TOTAL_COUNT.put("3", 6);  // 영웅 아이템 총 6개 Legend
        ITEM_RANK_TOTAL_COUNT.put("4", 4); // 전설 아이템 총 4개 Special
        ITEM_RANK_TOTAL_COUNT.put("5", 34);   // 지역 아이템 총 68개 Local
    }

    public Map<Long, Integer> getItemIdsByUserEmail(String email) {
        List<UserItems> userItemsList = userItemsRepository.findAllByUser_Email(email);
        return userItemsList.stream()
                .collect(Collectors.toMap(
                        userItems -> userItems.getItem().getItemId(),
                        UserItems::getPiece
                ));
    }

    public UserItemsResponse getUserItemsResponse(String email) {
        List<UserItems> userItemsList = userItemsRepository.findAllByUser_Email(email);
        List<Long> itemIds = userItemsList.stream()
                .map(userItems -> userItems.getItem().getItemId())
                .collect(Collectors.toList());

        int totalItemCount = getTotalItemCount();
        // 완성된 아이템 리스트
        List<Long> completedItemIds = userItemsList.stream()
                .filter(UserItems::isCompleted)  // is_completed 필터링
                .map(userItems -> userItems.getItem().getItemId())
                .collect(Collectors.toList());

        int completedEarnItemCount = completedItemIds.size();
        double earnPercentage = (double) completedEarnItemCount / totalItemCount * 100;

        Map<String, Integer> areaTotalItemCounts = new HashMap<>(AREA_ITEM_COUNT);
        Map<String, Integer> areaEarnItemCounts = getEarnedItemCountByArea(userItemsList);

        Map<String, Integer> rankTotalItemCounts = new HashMap<>(ITEM_RANK_TOTAL_COUNT);
        Map<String, Integer> rankEarnItemCounts = getEarnedItemCountByRank(userItemsList);

        // 지역별 획득 아이템 개수를 0으로 초기화
        for (String area : AREA_ITEM_COUNT.keySet()) {
            areaEarnItemCounts.putIfAbsent(area, 0);
        }

        // 랭크별 획득 아이템 개수를 0으로 초기화
        for (String rank : ITEM_RANK_TOTAL_COUNT.keySet()){
            rankEarnItemCounts.putIfAbsent(rank, 0);
        }

        int areaItemCount = rankTotalItemCounts.getOrDefault("5", 0);  // 랭크 5인 전체 아이템 수
        int earnAreaItemCount = rankEarnItemCounts.getOrDefault("5", 0);  // 랭크 5인 획득한 아이템 수
        double earnAreaPercentage = areaItemCount > 0 ? (double) earnAreaItemCount / areaItemCount * 100 : 0;

        return UserItemsResponse.builder()
                .itemIds(itemIds)
                .totalItemCount(totalItemCount)
                .earnItemCount(completedEarnItemCount)
                .earnPercentage(earnPercentage)
                .areaItemCount(areaItemCount)
                .earnAreaItemCount(earnAreaItemCount)
                .earnAreaPercentage(earnAreaPercentage)
                .areaTotalItemCounts(areaTotalItemCounts)
                .areaEarnItemCounts(areaEarnItemCounts)
                .rankTotalItemCounts(rankTotalItemCounts)
                .rankEarnItemCounts(rankEarnItemCounts)
                .build();
    }

    // 지역별 유저 획득 아이템 집계
    public Map<String, Integer> getEarnedItemCountByArea(List<UserItems> userItemsList) {
        return userItemsList.stream()
                .filter(UserItems::isCompleted)//완성 여부
                .collect(Collectors.groupingBy(
                        userItem -> Optional.ofNullable(userItem.getItem().getArea())
                                .map(Area::getValue)
                                .orElse("UNKNOWN"),
                        Collectors.summingInt(userItem -> 1)
                ));
    }

    // 랭크별 유저 획득 아이템 집계
    public Map<String, Integer> getEarnedItemCountByRank(List<UserItems> userItemsList) {
        return userItemsList.stream()
                .filter(UserItems::isCompleted)//완성 여부
                .collect(Collectors.groupingBy(
                        userItem -> {
                            Integer rank = userItem.getItem().getItemRank();
                            return rank != null ? String.valueOf(rank) : "0"; // null인 경우 기본값 설정
                        },
                        Collectors.summingInt(userItem -> 1)
                ));
    }

    public int getTotalItemCount() {
        return (int) itemRepository.count();
    }

    public int getEarnedAreaItemCountByRank(List<UserItems> userItemsList, int rank) {
        // 유저가 획득한 랭크 5인 아이템 개수를 계산
        return (int) userItemsList.stream()
                .filter(userItem -> userItem.getItem().getItemRank() == rank)
                .count();
    }
}
