package com.travelgo.backend.domain.user.controller;

import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.service.GeoCodingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "지오 코딩", description = "지오 주소 API")
@RequestMapping("/api/geo")
public class GeoCodingController {
    private final GeoCodingService geoCodingService;

    @Operation(summary = "지오코딩", description = "주소로 위도 경도 받아오기")
    @PostMapping
    public ResponseEntity<Double[]> signupUser(@RequestParam(name = "address") String address) {
        Double[] geocode = geoCodingService.geocode(address);
        return new ResponseEntity<>(geocode, HttpStatus.OK);
    }

    @Operation(summary = "역지오코딩", description = "주소로 위도 경도 받아오기")
    @PostMapping("/reverse")
    public ResponseEntity<String[]> signupUser(@RequestParam(name = "latitude") Double latitude,
                                               @RequestParam(name = "longitude") Double longitude) {
        String[] reverse = geoCodingService.reverseGeocode(latitude, longitude);
        return new ResponseEntity<>(reverse, HttpStatus.OK);
    }
}
