package com.travelgo.backend.auth.dto;

import com.travelgo.backend.domain.user.entity.Role;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.exception.UserException;
import com.travelgo.backend.domain.user.model.Bag;
import com.travelgo.backend.domain.user.model.Shoes;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new UserException(ErrorCode.NOT_FOUND_USER);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2UserInfo.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .profile((String) response.get("profile_image"))
                .build();
    }

    public User toEntity() {
        System.out.println(name);
        return User.builder()
                .email(email)
                .nickname(null)
                .username(name)
                .detectionRange(0)
                .experience(0)
                .workCount(0)
                .level(1)
                .quest(0)
                .tg(0)
                .shoes(Shoes.맨발)
                .bag(Bag.초급)
                .maxSearch(10)
                .possibleSearch(10)
                .experienceX(1.0)
                .tgX(1.0)
                .role(Role.USER)
                .build();
    }
}
