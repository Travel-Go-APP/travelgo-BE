package com.travelgo.backend.domain.drop.entity;

import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "DROPS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drop_id")
    private Long dropId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "drop_time")
    private LocalDateTime dropTime;

    @Builder
    public Drop(Item item, User user, LocalDateTime dropTime) {
        this.item = item;
        this.user = user;
        this.dropTime = dropTime;
    }
}
