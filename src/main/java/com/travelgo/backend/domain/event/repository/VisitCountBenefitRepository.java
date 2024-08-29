package com.travelgo.backend.domain.event.repository;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.event.entity.VisitCountBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitCountBenefitRepository extends JpaRepository<VisitCountBenefit, Long> {
    VisitCountBenefit findByAreaCode(AreaCode areaCode);
}
