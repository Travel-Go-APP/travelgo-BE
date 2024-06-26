package com.travelgo.backend.domain.attractionImage.repository;

import com.travelgo.backend.domain.attractionImage.entity.AttractionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionImageRepository extends JpaRepository<AttractionImage, Long> {
    List<AttractionImage> findAllByAttraction_AttractionId(Long attractionId);

    void deleteAllByAttraction_AttractionId(Long attractionId);
}
