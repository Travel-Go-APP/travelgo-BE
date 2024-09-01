package com.travelgo.backend.domain.attraction.repository;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Attraction findByAttractionNameAndCustomFlagFalse(String name);

    List<Attraction> findAllByAreaAndCustomFlagFalse(AreaCode areaCode);

    List<Attraction> findAllByCustomFlagTrueOrderByLikesDesc();

    Long countByAreaAndCustomFlagFalse(AreaCode areaCode);

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) *" +
            " cos(radians(a.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(a.latitude))))";

    @Query("SELECT a FROM Attraction a WHERE " + HAVERSINE_FORMULA + " < :distance AND a.customFlag = false ORDER BY " + HAVERSINE_FORMULA)
    List<Attraction> findAttractionsWithInDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance);
}
