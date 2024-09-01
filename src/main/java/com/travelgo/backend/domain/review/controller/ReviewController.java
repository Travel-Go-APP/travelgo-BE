package com.travelgo.backend.domain.review.controller;


import com.travelgo.backend.domain.review.dto.ReviewRequest;
import com.travelgo.backend.domain.review.dto.ReviewResponse;
import com.travelgo.backend.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰", description = "리뷰 api(#46)")
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 저장", description = "정보를 받아 리뷰를 저장한다.")
    @PostMapping()
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return new ResponseEntity<>(reviewService.saveReview(reviewRequest),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 Id를 받아 리뷰를 삭제한다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> deleteReview(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.delete(reviewId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저의 전체 리뷰 삭제", description = "로그인중인 유저의 전체 리뷰를 삭제한다.")
    @DeleteMapping("/deleteAll-User")
    public ResponseEntity<?> deleteReviewList(@RequestParam(name = "email") String email) {
        reviewService.deleteList(email);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "리뷰 Id로 리뷰 정보 받아오기", description = "리뷰 Id를 통해 단일 리뷰의 정보를 받아온다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getDetail(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.getDetail(reviewId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저별 리뷰 리스트", description = "유저별 최신순 리뷰 리스트를 보여준다")
    @GetMapping("/search-user")
    public ResponseEntity<List<ReviewResponse>> getListByUser(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(reviewService.getListByUser(email), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "최신 날짜순 리뷰 리스트", description = "명소의 전체 리뷰를 최신 날짜순으로 보여준다.")
    @GetMapping("/search-orderByDate-Desc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByDateDESC(@RequestParam("attractionId") Long attractionId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateDesc(attractionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "오래된 날짜순 리뷰 리스트", description = "명소의 전체 리뷰를 오래된 날짜순으로 보여준다.")
    @GetMapping("/search-orderByDate-Asc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByDateASC(@RequestParam("attractionId") Long attractionId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateAsc(attractionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "높은 별점 순 리뷰 리스트", description = "명소의 전체 리뷰를 높은 별점순으로 보여준다.")
    @GetMapping("/search-orderByRating-Desc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByRatingDESC(@RequestParam("attractionId") Long attractionId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingDesc(attractionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "낮은 별점 순 리뷰 리스트", description = "명소의 전체 리뷰를 낮은 별점순으로 보여준다.")
    @GetMapping("/search-orderByRating-Asc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByRatingAsc(@RequestParam("attractionId") Long attractionId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingAsc(attractionId), HttpStatusCode.valueOf(200));
    }
}
