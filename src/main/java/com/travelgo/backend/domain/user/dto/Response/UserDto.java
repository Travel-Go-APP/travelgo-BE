package com.travelgo.backend.domain.user.dto.Response;

import com.travelgo.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
