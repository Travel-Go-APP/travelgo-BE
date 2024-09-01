package com.travelgo.backend.domain.review.repository;

import com.travelgo.backend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser_EmailOrderByDateDesc(String email);

    List<Review> findByAttraction_AttractionIdOrderByDateDesc(Long attractionId);

    List<Review> findByAttraction_AttractionIdOrderByDateAsc(Long attractionId);

    List<Review> findByAttraction_AttractionIdOrderByRatingDescDateDesc(Long attractionId);

    List<Review> findByAttraction_AttractionIdOrderByRatingAscDateDesc(Long attractionId);
}
