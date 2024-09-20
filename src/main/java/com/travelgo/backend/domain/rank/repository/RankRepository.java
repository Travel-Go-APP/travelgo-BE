package com.travelgo.backend.domain.rank.repository;

import com.travelgo.backend.domain.rank.entity.Rank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
    List<Rank> findAllByOrderByRankIdAsc(Pageable pageable);  // 랭킹 순서대로 조회
    Optional<Rank> findByEmail(String email);  // 이메일로 유저 랭크 조회
}
