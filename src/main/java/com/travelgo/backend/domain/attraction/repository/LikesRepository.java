package com.travelgo.backend.domain.attraction.repository;

import com.travelgo.backend.domain.attraction.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByAttraction_AttractionIdAndUser_Email(Long attractionId, String email);

    void deleteByAttraction_AttractionIdAndUser_Email(Long attractionId, String email);
}
