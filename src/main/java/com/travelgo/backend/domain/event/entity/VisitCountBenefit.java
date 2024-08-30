package com.travelgo.backend.domain.event.entity;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitCountBenefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_id")
    private Long benefitId;

    @Enumerated(EnumType.STRING)
    private AreaCode areaCode;

    private int ranking;

    private String benefitType;

    private double benefitRatio;

    @Builder
    public VisitCountBenefit(AreaCode areaCode, int ranking, String benefitType, double benefitRatio) {
        this.areaCode = areaCode;
        this.ranking = ranking;
        this.benefitType = benefitType;
        this.benefitRatio = benefitRatio;
    }
}
