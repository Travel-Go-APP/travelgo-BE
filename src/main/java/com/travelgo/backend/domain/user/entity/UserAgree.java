package com.travelgo.backend.domain.user.entity;

import com.travelgo.backend.domain.user.dto.AgreeDto;
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

    private Boolean snsAgree;

    private Boolean locationAgree;

    private Boolean stepCountAgree;

    public UserAgree(AgreeDto agreeDto) {
        this.snsAgree = agreeDto.getSnsAgree();
        this.locationAgree = agreeDto.getLocationAgree();
        this.stepCountAgree = agreeDto.getStepCountAgree();
    }
}
