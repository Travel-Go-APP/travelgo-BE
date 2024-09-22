package com.travelgo.backend.domain.item.service;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.attractionImage.service.S3UploadService;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.dto.response.ItemResponse;
import com.travelgo.backend.domain.item.dto.response.ShopResponse;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.service.UserItemsService;
import com.travelgo.backend.domain.util.entity.geo.service.GeoCodingService;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.userItems.repository.UserItemsRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserItemsRepository userItemsRepository;
    private final S3UploadService s3UploadService;
    private final GeoCodingService geoCodingService;
    private final UserItemsService userItemsService;
    private final Random rand = new Random();

    @Transactional
    public void addItem(ItemRequest request, MultipartFile imageFile) throws IOException {
        // 아이템이 이미 존재하는지 확인
        Optional<Item> existingItem = itemRepository.findByItemId(request.getItemId());

        if (existingItem.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ITEM); // 아이템이 이미 존재하는 경우
        }

        // 이미지 URL 처리
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3UploadService.upload(imageFile, "items");
        }

        // 문자열을 Area Enum으로 변환
        Area area;
        try {
            area = Arrays.stream(Area.values())
                    .filter(a -> a.getValue().equalsIgnoreCase(request.getArea()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AREA)); // 유효하지 않은 Area 값 처리
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AREA); // 유효하지 않은 Area 값 처리
        }

        // 새로운 Item 객체 생성
        Item newItem = Item.createItem(request.getItemName(), imageUrl, request.getItemRank(),
                area, request.getSummary(), request.getDescription());

        // 아이템 저장
        itemRepository.save(newItem);
    }

    @Transactional
    public ItemResponse acquireItem(String email, Double latitude, Double longitude) {
        // 유저 정보 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 역지오코딩을 통해 지역명 분석
        String[] areaAndVisitArea = geoCodingService.reverseGeocode(latitude, longitude);
        String areaName = areaAndVisitArea[0];

        Area area;
        try {
            area = Area.fromString(areaName);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AREA);  // 유효하지 않은 지역명 처리
        }

        // 해당 지역의 아이템 목록 가져오기
        List<Item> itemsInArea = itemRepository.findByArea(area);
        if (itemsInArea.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ITEM);  // 해당 지역에 아이템이 없을 때 처리
        }

        // 랜덤으로 아이템 선택
        Random random = new Random();
        Item selectedItem = itemsInArea.get(random.nextInt(itemsInArea.size()));

        // 유저가 이미 해당 아이템을 획득했는지 확인
        boolean alreadyAcquired = userItemsRepository.existsByUserAndItem(user, selectedItem);
        if (alreadyAcquired) {
            throw new CustomException(ErrorCode.BAD_REQUEST);  // 이미 아이템을 획득한 경우
        }

        // UserItems 테이블에 아이템 저장
        UserItems userItems = UserItems.builder()
                .user(user)
                .item(selectedItem)
                .build();

        userItemsRepository.save(userItems);

        // 응답 생성
        return ItemResponse.fromEntity(selectedItem);
    }

    @Transactional
    public void updateItem(Long itemId, String itemName, int itemRank){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        item.updateItemName(itemName);
        item.updateItemRank(itemRank);

        itemRepository.save(item);
    }

    @Transactional
    public ItemResponse.DeleteItem deleteItem(Long itemId){
        Item item = itemRepository.findByItemId(itemId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        itemRepository.delete(item);

        return new ItemResponse.DeleteItem(itemId, "아이템이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
        return itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }

    @Transactional
    public ShopResponse buyShop(String email, Integer gachaLevel, Double latitude, Double longitude) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        double itemProbability;
        double moneyProbability;
        double expProbability;
        int minMoney, maxMoney, minExp, maxExp;

        // 각 gachaLevel에 따라 확률과 보상 범위를 설정
        switch (gachaLevel) {
            case 1:
                if (user.getTg() < 3000) {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                } else {
                    user.addTg(-3000);
                }
                itemProbability = 0.4;
                moneyProbability = 0.3;
                expProbability = 0.3;
                minMoney = 2000;
                maxMoney = 5000;
                minExp = 30;
                maxExp = 80;
                break;
            case 2:
                if (user.getTg() < 5000) {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                } else {
                    user.addTg(-5000);
                }
                itemProbability = 0.5;
                moneyProbability = 0.25;
                expProbability = 0.25;
                minMoney = 3000;
                maxMoney = 8000;
                minExp = 50;
                maxExp = 110;
                break;
            case 3:
                if (user.getTg() < 9999) {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                } else {
                    user.addTg(-9999);
                }
                itemProbability = 0.6;
                moneyProbability = 0.2;
                expProbability = 0.2;
                minMoney = 6000;
                maxMoney = 18000;
                minExp = 80;
                maxExp = 200;
                break;
            default:
                throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        ShopResponse response;
        double roll = rand.nextDouble();

        if (roll < itemProbability) {
            // 아이템 획득 로직 추가 (UserItemsService 이용)
            Map<String, Object> itemResponse = userItemsService.add(email, latitude, longitude);
            response = new ShopResponse(
                    "item",
                    (String) itemResponse.get("itemName"),
                    (String) itemResponse.get("itemSummary"),
                    (String) itemResponse.get("itemDescription"),
                    (Integer) itemResponse.get("itemPiece")
            );
        } else if (roll < itemProbability + moneyProbability) {
            int money = rand.nextInt(maxMoney - minMoney + 1) + minMoney;
            user.addTg(money);
            response = new ShopResponse("tg", user.getTg(), user.getExperience(), money, 0);
        } else {
            int experience = rand.nextInt(maxExp - minExp + 1) + minExp;
            user.addExperience(experience);
            response = new ShopResponse("exp", user.getTg(), user.getExperience(), 0, experience);
        }

        userRepository.save(user);
        return response;
    }

    private Item getRandomItem(){
        List<Item> items = itemRepository.findByArea(Area.valueOf("일반"));
        return items.get(rand.nextInt(items.size()));
    }
}