package com.travelgo.backend.domain.attraction.service;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.entity.Likes;
import com.travelgo.backend.domain.attraction.repository.LikesRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final UserRepository userRepository;
    private final AttractionService attractionService;
    private final LikesRepository likesRepository;

    public void save(Long attractionId, String email) {
        User user = getUser(email);
        Attraction attraction = attractionService.getAttraction(attractionId);

        Likes likes = new Likes(user, attraction);
        likesRepository.save(likes);
    }

    public void delete(Long attractionId, String email) {
        likesRepository.deleteByAttraction_AttractionIdAndUser_Email(attractionId, email);
    }

    public boolean duplicateLikes(Long attractionId, String email) {
        return likesRepository.existsByAttraction_AttractionIdAndUser_Email(attractionId, email);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }
}
