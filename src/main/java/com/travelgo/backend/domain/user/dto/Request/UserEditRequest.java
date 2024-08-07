package com.travelgo.backend.domain.user.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record UserEditRequest(
        @NotBlank String username,
        @NotBlank String phoneNumber
) {
}
