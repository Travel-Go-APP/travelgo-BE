package com.travelgo.backend.domain.rank.controller;

import com.travelgo.backend.domain.rank.entity.Rank;
import com.travelgo.backend.domain.rank.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "랭크", description = "랭크 조회 API")
@RequestMapping("/api/rank")
public class RankController {

    private final RankService rankService;

    @Operation(summary = "본인 랭킹 조회", description = "유저 개인 랭크 조회")
    @GetMapping("/user-rank")
    public ResponseEntity<Rank> getUserRankByEmail(@RequestParam(name = "email") String email) {
        Rank rank = rankService.getUserRankByEmail(email);
        return new ResponseEntity<>(rank, HttpStatus.OK);
    }

    @Operation(summary = "전체 랭킹 조회", description = "전체 랭킹 중 10등까지 조회ㄴ")
    @GetMapping("/all-user-rank")
    public ResponseEntity<List<Rank>> getAllUserRanks() {
        List<Rank> ranks = rankService.getAllUserRanks();
        return new ResponseEntity<>(ranks, HttpStatus.OK);
    }

    @Operation(summary = "수동 랭크 갱신용(사용 API 아님)")
    @PostMapping("/set-rank")
    public void SetRank(){
        rankService.updateRanks();
    }
}