package com.travelgo.backend.domain.user.controller;

import com.travelgo.backend.auth.utils.SecurityUtil;
import com.travelgo.backend.domain.user.dto.Response.UserDto;
import com.travelgo.backend.domain.user.entity.Role;
import com.travelgo.backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 Api (#7)")
@RequestMapping("/api")
public class UserOauthController {
    private final UserService userService;

    @Operation(summary = "유저 정보", description = "유저의 정보를 받아오기.")
    @GetMapping("/user")
    public ResponseEntity<UserDto> userInfo(Authentication authentication) {
        String email = SecurityUtil.getCurrentName(authentication);

        return new ResponseEntity<>(userService.userInfo(email), HttpStatusCode.valueOf(200));
    }


    @Operation(summary = "관리자 설정", description = "유저를 관리자로 설정한다.")
    @PatchMapping("/user/role")
    public ResponseEntity<Role> changeAdmin(Authentication authentication) {
        String email = SecurityUtil.getCurrentName(authentication);

        return ResponseEntity.ok(userService.changeAdmin(email));
    }

    @Operation(summary = "유저 리스트(관리자)", description = "유저의 리스트를 반환한다.")
    @GetMapping("/admin/list")
    public ResponseEntity<List<UserDto>> getList(Authentication authentication) {
        String email = SecurityUtil.getCurrentName(authentication);
        log.info("{}", email);
        return ResponseEntity.ok(userService.getList());
    }
}
