package com.travelgo.backend.domain.userItems.service;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.userItems.dto.response.UserItemsResponse;
import com.travelgo.backend.domain.userItems.repository.UserItemsRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserItemsService {

    @Autowired
    private final UserItemsRepository userItemsRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private static final List<String> AREAS = Arrays.asList(
            "Daejeon", "Ulsan", "Gyeonsangbukdo", "Gyeonsangnamdo",
            "Busan", "Gangwondo", "Sejong", "Jejudo", "Chungcheongbukdo",
            "Seoul", "Gwangju", "Jeollabukdo", "Jeollanamdo", "Incheon",
            "Gyeonggido", "Chungcheongnamdo", "Daegu", "Common"
    );

    public void add(String email, Long itemId){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Item item = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));

        UserItems userItems = UserItems.builder()
                .user(user)
                .item(item)
                .build();

        userItemsRepository.save(userItems);
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
        ITEM_RANK_TOTAL_COUNT.put("5", 68);   // 지역 아이템 총 68개 Local
    }

    public List<Long> getItemIdsByUserEmail(String email) {
        List<UserItems> userItemsList = userItemsRepository.findAllByUser_Email(email);
        return userItemsList.stream()
                .map(userItems -> userItems.getItem().getItemId())
                .collect(Collectors.toList());
    }

    public UserItemsResponse getUserItemsResponse(String email) {
        List<UserItems> userItemsList = userItemsRepository.findAllByUser_Email(email);
        List<Long> itemIds = userItemsList.stream()
                .map(userItems -> userItems.getItem().getItemId())
                .collect(Collectors.toList());

        int totalItemCount = getTotalItemCount();
        int earnItemCount = itemIds.size();
        double earnPercentage = (double) earnItemCount / totalItemCount * 100;

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
                .earnItemCount(earnItemCount)
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
                .collect(Collectors.groupingBy(
                        userItem -> Optional.ofNullable(userItem.getItem().getArea())
                                .map(Area::getValue)
                                .orElse("UNKNOWN"), // null인 경우 기본값 설정
                        Collectors.summingInt(userItem -> 1)
                ));
    }

    // 랭크별 유저 획득 아이템 집계
    public Map<String, Integer> getEarnedItemCountByRank(List<UserItems> userItemsList) {
        return userItemsList.stream()
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
