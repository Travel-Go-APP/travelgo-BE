package com.travelgo.backend.domain.rank.service;

import com.travelgo.backend.domain.rank.entity.Rank;
import com.travelgo.backend.domain.rank.repository.RankRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.visit.repository.VisitRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankService {

    private final RankRepository rankRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 0 0 * * ?")  // 스케쥴러 매일 자정
    @Transactional
    public void updateRanks() {

        rankRepository.deleteAll();
        rankRepository.flush(); // 트랜잭션 즉시 커밋

        resetAutoIncrement();

        List<User> users = userRepository.findAll();  // 모든 유저 조회

        List<Rank> ranks = users.stream()
                .map(user -> {
                    int itemCount = (int) user.getUserItems().stream().filter(UserItems::isCompleted).count();  // 수집한 아이템 개수
                    int visitCount = visitRepository.countByUser(user).intValue();  // 방문한 명소 개수
                    int totalScore = user.getLevel() + itemCount + (visitCount / 3);  // 총점 계산

                    return new Rank(null, user.getEmail(), user.getNickname(), user.getLevel(), itemCount, visitCount, totalScore);
                })
                .sorted(Comparator.comparingInt(Rank::getScore).reversed())  // 내림차순 정렬
                .collect(Collectors.toList());

        // Rank에 순위를 부여
        for (int i = 0; i < ranks.size(); i++) {
            ranks.get(i).setRankId((long) (i + 1));
        }

        // 저장
        rankRepository.saveAll(ranks);
    }

    public Rank getUserRankByEmail(String email) {
        return rankRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public List<Rank> getAllUserRanks() {
        Pageable topTen = PageRequest.of(0,10); // 상위 10위 조회
        return rankRepository.findAllByOrderByRankIdAsc(topTen);
    }

    // ID값 Auto Increment 때문에 저장시 1로 초기화
    @Transactional
    public void resetAutoIncrement(){
        entityManager.createNativeQuery("ALTER TABLE user_rank AUTO_INCREMENT = 1").executeUpdate();
    }
}
