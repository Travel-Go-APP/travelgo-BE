package com.travelgo.backend.domain.visit.repository;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.visit.entity.Visit;
import com.travelgo.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit findByUserAndAttraction(User user, Attraction attraction);

    List<Visit> findAllByUser(User user);

    List<Visit> findAllByUserAndAttraction_Area(User user, AreaCode areaCode);

    Long countByUser(User user);

    Long countByUserAndAttraction_Area(User user, AreaCode areaCode);
}
