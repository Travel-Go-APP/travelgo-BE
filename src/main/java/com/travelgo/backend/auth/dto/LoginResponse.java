package com.travelgo.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponse(@NotBlank String accessToken) {
}
