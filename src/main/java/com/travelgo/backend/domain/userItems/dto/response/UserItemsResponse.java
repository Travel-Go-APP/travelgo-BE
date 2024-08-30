package com.travelgo.backend.domain.userItems.dto.response;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserItemsResponse {
    private List<Long> itemIds; // 아이템 ID 리스트
    private int totalItemCount; // 전체 아이템 개수
    private int earnItemCount; // 획득한 아이템 개수
    private double earnPercentage; // 전체 수집률
    private int areaItemCount; // 지역 아이템 총 개수
    private int earnAreaItemCount; // 지역 아이템 획득 개수
    private double earnAreaPercentage; // 지역 아이템 수집률
    private Map<String, Integer> areaTotalItemCounts; // 지역별 전체 아이템 수
    private Map<String, Integer> areaEarnItemCounts; // 지역별 획득 아이템 수
    private Map<String, Integer> rankTotalItemCounts; // 랭크별 전체 아이템 수
    private Map<String, Integer> rankEarnItemCounts; // 랭크별 전체 아이템 수

}
