package com.travelgo.backend.domain.user.entity;

import com.travelgo.backend.domain.user.dto.AgreeDto;
import com.travelgo.backend.domain.user.model.Checking;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgree {
    @Id
    @Column(name = "agree_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agreeId;

    @Enumerated(EnumType.STRING)
    private Checking snsAgree;

    @Enumerated(EnumType.STRING)
    private Checking locationAgree;

    @Enumerated(EnumType.STRING)
    private Checking stepCountAgree;

    public UserAgree(AgreeDto agreeDto) {
        this.snsAgree = agreeDto.getSnsAgree();
        this.locationAgree = agreeDto.getLocationAgree();
        this.stepCountAgree = agreeDto.getStepCountAgree();
    }
}
