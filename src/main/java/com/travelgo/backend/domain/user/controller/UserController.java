package com.travelgo.backend.domain.user.controller;

import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.exception.UserNotFoundException;
import com.travelgo.backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 API")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 후 닉네임과 기본 값들 설정")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signupUser(@Valid @RequestBody UserRequest.SignUp request){
        return new ResponseEntity<>(userService.signUp(request), HttpStatusCode.valueOf(200));
    }

    //1개짜리 이메일만 있으면 됨
    @Operation(summary = "유저삭제", description = "유저 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse.DeleteUser> deleteUser(@PathVariable(name = "userId") Long userId){
        UserResponse.DeleteUser response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    //1개짜리 이메일만 있으면 됨
    @Operation(summary = "닉네임 중복 체크", description = "DB 대조를 통한 닉네임 가능 여부 체크")
    @PostMapping("/check-nickname")
    public ResponseEntity<Void> checkNickname(@Valid @RequestBody UserRequest.CheckNickname request){
        boolean exists = userService.CheckNicknameExists(request);
        if(exists){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(summary = "닉네임 변경", description = "닉네임 변경")
    @PutMapping("/update-nickname")
    public ResponseEntity<UserResponse.UpdateNickname> updateUser(@RequestParam(name = "email") String email,
                                                                  @RequestParam(name = "nickName") String nickName) {
        return new ResponseEntity<>(userService.updateUser(email, nickName), HttpStatusCode.valueOf(200));
    }


    @Operation(summary = "로그인", description = "이메일로 로그인 시도")
    @PostMapping("/login")
    public ResponseEntity<UserResponse.Login> login(@Valid @RequestBody UserRequest.Login request){
        try{
            UserResponse.Login response = userService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "경험치 업데이트", description = "유저 경험치 업데이트")
    @PostMapping("/exp")
    public ResponseEntity<?> updateExperience(@RequestBody UserRequest.UpdateExp request){
        try{
            UserResponse.UpdateExp response = userService.updateExp(request);
            return ResponseEntity.ok(response);
        } catch(UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
