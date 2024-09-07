package com.travelgo.backend.domain.search.controller;

import com.travelgo.backend.domain.search.service.SearchService;
import com.travelgo.backend.domain.user.dto.Response.UserSearchResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "조사하기(이벤트)", description = "조사하기 API")
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository;

    @Operation(summary = "조사하기 회복", description = "TG를 지불하고 조사하기 회복")
    @PostMapping("/SearchCountRecover")
    public ResponseEntity<Map<String, Object>> SearchCountRecover(@RequestParam(name = "email") String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Map<String, Object> result = searchService.recoverSearchCount(email);

        if(result.get("status").equals("성공")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "조사하기(이벤트-상호작용)", description = "이벤트 통합 처리 포인트")
    @PostMapping("/event")
    public UserSearchResponse handleEvent(@RequestParam(name = "email") String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        int eventCategory = searchService.selectEvent();
        UserSearchResponse response = searchService.handleSelectedEvent(user, eventCategory);

        userRepository.save(user);

        return response;
    }

    @Operation(summary = "조사하기(이벤트-상호작용)", description = "지갑을 발견했다!")
    @PostMapping("/event/wallet")
    public UserSearchResponse handleWalletEvent(@RequestParam(name = "email") String email,
                                                @RequestParam(name = "takeAction") boolean takeAction) {
        int eventCategory = 11;
        return searchService.handleWalletEvent(email, takeAction, eventCategory);
    }

    @Operation(summary = "조사하기(이벤트-상호작용)", description = "수상한 상인이다!")
    @PostMapping("/event/merchant")
    public UserSearchResponse handleMerchantEvent(@RequestParam(name = "email") String email) {
        int eventCategory = 12;
        return searchService.handleMerchantEvent(email, eventCategory);
    }

    @Operation(summary = "조사하기(이벤트-상호작용)", description = "복권 사는 날")
    @PostMapping("/event/lottery")
    public UserSearchResponse handleLotteryEvent(@RequestParam(name = "email") String email) {
        int eventCategory = 13;
        return searchService.handleLotteryEvent(email, eventCategory);
    }

    @Operation(summary = "조사하기(이벤트-상호작용)", description = "카메라를 발견했다!")
    @PostMapping("/event/camera")
    public UserSearchResponse handleCameraEvent(@RequestParam(name = "email") String email) {
        int eventCategory = 14;
        return searchService.handleCameraEvent(email, eventCategory);
    }

    @Operation(summary = "룰렛 데이터 반영", description = "수상한 상인 이벤트 반영")
    @PostMapping("/event/merchant/roulette")
    public UserSearchResponse roulette(@RequestParam(name = "email") String email
                                      ,@RequestParam(name = "result") String result){
        return searchService.roulette(email,result);
    }
}