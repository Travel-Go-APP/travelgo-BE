package com.travelgo.backend.domain.attractionachievement.repository;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attractionachievement.entity.AttractionAchievement;
import com.travelgo.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionAchievementRepository extends JpaRepository<AttractionAchievement, Long> {
    AttractionAchievement findByUserAndAttraction(User user, Attraction attraction);

    List<AttractionAchievement> findAllByUser(User user);

    List<AttractionAchievement> findAllByUserAndAttraction_Area(User user, AreaCode areaCode);

    Long countByUser(User user);

    Long countByUserAndAttraction_Area(User user, AreaCode areaCode);
}
