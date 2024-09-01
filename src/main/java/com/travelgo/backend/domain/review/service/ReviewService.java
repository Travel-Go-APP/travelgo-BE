package com.travelgo.backend.domain.review.service;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.domain.review.dto.ReviewRequest;
import com.travelgo.backend.domain.review.dto.ReviewResponse;
import com.travelgo.backend.domain.review.entity.Review;
import com.travelgo.backend.domain.review.repository.ReviewRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AttractionService attractionService;
    private final UserService userService;

    @Transactional
    public ReviewResponse saveReview(ReviewRequest reviewRequest) {
        User user = userService.getUser(reviewRequest.getEmail());
        Attraction attraction = attractionService.getAttraction(reviewRequest.getAttractionId());

        notCustomAttraction(attraction);

        Review review = createReview(reviewRequest, attraction, user);
        Review savedReview = reviewRepository.save(review);

        return getDetail(savedReview.getReviewId());
    }

    private static void notCustomAttraction(Attraction attraction) {
        if (attraction.getCustomFlag())
            throw new CustomException(ErrorCode.NOT_COMMON_ATTRACTION);
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Long delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return reviewId;
    }

    @Transactional
    public void deleteList(String email) {
        List<Review> reviewList = reviewRepository.findByUser_EmailOrderByDateDesc(email);
        reviewRepository.deleteAll(reviewList);
    }

    public List<ReviewResponse> getListByUser(String email) {
        List<Review> reviewPage = createReviewPageByUser(email);
        return createReviewResponseList(reviewPage);
    }

    public List<ReviewResponse> getListOrderByDateAsc(Long attractionId) {
        List<Review> reviewPage = createReviewListByItemOrderByDateAsc(attractionId);
        return createReviewResponseList(reviewPage);
    }

    public List<ReviewResponse> getListOrderByDateDesc(Long attractionId) {
        List<Review> reviewPage = createReviewListByItemOrderByDateDesc(attractionId);
        return createReviewResponseList(reviewPage);
    }

    public List<ReviewResponse> getListOrderByRatingAsc(Long attractionId) {
        List<Review> reviewPage = createReviewListByItemOrderByRatingAsc(attractionId);
        return createReviewResponseList(reviewPage);
    }

    public List<ReviewResponse> getListOrderByRatingDesc(Long attractionId) {
        List<Review> reviewPage = createReviewListByItemOrderByRatingDesc(attractionId);
        return createReviewResponseList(reviewPage);
    }


    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
    }

    private static ReviewResponse createReviewResposnse(Review review) {
        return new ReviewResponse(review);
    }


    private static List<ReviewResponse> createReviewResponseList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }

    private List<Review> createReviewPageByUser(String email) {
        return reviewRepository.findByUser_EmailOrderByDateDesc(email);
    }

    private List<Review> createReviewListByItemOrderByDateAsc(Long attractionId) {
        return reviewRepository.findByAttraction_AttractionIdOrderByDateAsc(attractionId);
    }

    private List<Review> createReviewListByItemOrderByDateDesc(Long attractionId) {
        return reviewRepository.findByAttraction_AttractionIdOrderByDateDesc(attractionId);
    }

    private List<Review> createReviewListByItemOrderByRatingAsc(Long attractionId) {
        return reviewRepository.findByAttraction_AttractionIdOrderByRatingAscDateDesc(attractionId);
    }

    private List<Review> createReviewListByItemOrderByRatingDesc(Long attractionId) {
        return reviewRepository.findByAttraction_AttractionIdOrderByRatingDescDateDesc(attractionId);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getDetail(Long reviewId) {
        Review review = getReview(reviewId);
        return createReviewResposnse(review);
    }

    @Transactional
    public Review createReview(ReviewRequest reviewRequest, Attraction attratiocn, User user) {
        return Review.builder()
                .content(reviewRequest.getContent())
                .rating(reviewRequest.getRating())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .attraction(attratiocn)
                .user(user)
                .build();
    }


}

