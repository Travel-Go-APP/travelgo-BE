package com.travelgo.backend.domain.attraction.repository;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Attraction findByAttractionName(String name);
}
