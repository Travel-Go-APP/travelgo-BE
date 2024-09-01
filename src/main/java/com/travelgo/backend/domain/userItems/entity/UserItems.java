package com.travelgo.backend.domain.userItems.entity;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserItems extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int piece; // 아이템 조각 수

    private boolean isCompleted; // 아이템 완성 여부

    @Builder
    public UserItems(User user, Item item){
        this.user = user;
        this.item = item;
        this.piece = 1;
        this.isCompleted = false;
    }

    public Area getArea(){
        return this.item.getArea();
    }

    public void addPiece() {
        if (!isCompleted) {
            this.piece++;  // 조각 수를 1 증가
            if (this.piece >= 10) {
                this.isCompleted = true;  // 조각이 10개 이상이면 완성 처리
            }
        }
    }

    public int getPiece() {
        return this.piece;  // 현재 조각 수를 반환
    }
}
