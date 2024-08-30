package com.travelgo.backend.domain.search.service;

import com.travelgo.backend.domain.user.dto.Response.UserSearchResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final Random rand = new Random();

    // 이벤트를 확률에 따라 선택하는 메서드
    public int selectEvent() {
        List<EventProbability> eventProbabilities = List.of(
                new EventProbability(1, 15.0),  //"동전을 주웠다!"
                new EventProbability(2, 10.0),  //"이벤트에 당첨되었다!"
                new EventProbability(3, 15.0),  //"마음의 양식을 쌓았다!"
                new EventProbability(4, 5.0),   //"헬스장을 다녀왔다!"
                new EventProbability(5, 5.0),   //"보조배터리로 핸드폰을 충전했다"
                new EventProbability(6, 10.0),  //"카페에서 편안한 휴식을 즐겼다!"
                new EventProbability(7, 5.0),   //"드링크를 마시니깐 힘이 생긴다"
                new EventProbability(8, 5.0),   //"몸살이 걸린 것 같다..."
                new EventProbability(9, 3.0),   //"젠장 함정에 빠졌다!"
                new EventProbability(10, 1.0),  //"온 우주가 나에게 들어오는 기분이다"
                new EventProbability(11, 6.5),  //"지갑을 발견했다!"
                new EventProbability(12, 6.5),  //"카메라를 발견했다!"
                new EventProbability(13, 6.5),  //"수상한 상인이다"
                new EventProbability(14, 6.5)   //"오늘 복권 발표날이다"
        );

        // 확률에 따라 이벤트 선택
        double totalProbability = eventProbabilities.stream().mapToDouble(EventProbability::getProbability).sum();
        double randomValue = rand.nextDouble() * totalProbability;

        double cumulativeProbability = 0.0;
        for (EventProbability event : eventProbabilities) {
            cumulativeProbability += event.getProbability();
            if (randomValue <= cumulativeProbability) {
                return event.getEventCode();
            }
        }
        return 0;  // 예외 처리 필요 시 조정 가능
    }

    public UserSearchResponse handleSelectedEvent(User user, int selectedEvent) {
        Integer tgChange = null;
        Integer expChange = null;
        Integer possibleSearchChange = null;
        List<String> merchantResults = null;
        UserSearchResponse response = null;

        if(user.getPossibleSearch() == 0){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }else{
            user.decreasePossibleSearch(1);
        }

        // 일반 이벤트 (1~10번) 처리
        if (selectedEvent >= 1 && selectedEvent <= 10) {
            switch (selectedEvent) {
                case 1:
                    tgChange = 100;
                    user.addTg(tgChange);
                    break;
                case 2:
                    tgChange = 300;
                    user.addTg(tgChange);
                    break;
                case 3:
                    expChange = 100;
                    user.addExperience(expChange);
                    break;
                case 4:
                    expChange = 500;
                    user.addExperience(expChange);
                    break;
                case 7:
                    possibleSearchChange = 1;
                    user.recoverPossibleSearch(possibleSearchChange);
                    break;
                case 8:
                    possibleSearchChange = -2;
                    user.decreasePossibleSearch(2);
                    break;
                case 9:
                    tgChange = -user.getTg() * 20 / 100;
                    user.loseTgPercentage(20);
                    break;
                case 10:
                    user.addExperience(1000);  // 임의로 예시를 넣음
                    user.levelUp();
                    break;
            }
            userRepository.save(user);
            return new UserSearchResponse(user, selectedEvent, tgChange, expChange, possibleSearchChange);
        }

        // 특수 이벤트 (11~14번) 처리 - 이벤트 ID만 반환
        if (selectedEvent >= 11 && selectedEvent <= 14) {
            return new UserSearchResponse(user, selectedEvent);
        }

        throw new CustomException(ErrorCode.BAD_REQUEST);  // 예외 처리
    }

    // 지갑 이벤트 처리
    public UserSearchResponse handleWalletEvent(String email, boolean takeAction, int eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Integer tgChange = takeAction ? rand.nextInt(501) : rand.nextInt(3001) - 2000;
        user.addTg(tgChange);
        userRepository.save(user);

        return new UserSearchResponse(user, eventCategory, tgChange);
    }

    // 상인 이벤트 처리
    public UserSearchResponse handleMerchantEvent(String email, int eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<String> merchantResults = List.of(
                "TG " + (rand.nextInt(4000) - 2000),
                "TG " + (rand.nextInt(4000) - 2000),
                "EXP " + rand.nextInt(1001),
                "EXP " + rand.nextInt(1001),
                "꽝"
        );

        userRepository.save(user);
        return new UserSearchResponse(user, eventCategory, merchantResults);
    }

    // 룰렛 선택 이벤트 처리
    public UserSearchResponse roulette(String email, String result) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Integer tgChange = null;
        Integer expChange = null;

        // result 문자열 파싱
        if (result.startsWith("TG ")) {
            // "TG 320"처럼 "TG "로 시작하는 경우
            tgChange = Integer.parseInt(result.split(" ")[1]);
            user.addTg(tgChange);  // TG 증가
        } else if (result.startsWith("EXP ")) {
            // "EXP 300"처럼 "EXP "로 시작하는 경우
            expChange = Integer.parseInt(result.split(" ")[1]);
            user.addExperience(expChange);  // EXP 증가
        } else {
            throw new CustomException(ErrorCode.BAD_REQUEST);  // 잘못된 result 값 처리
        }

        // 변경된 유저 정보 저장
        userRepository.save(user);

        // 결과 반환
        return new UserSearchResponse(user, 0, tgChange, expChange, null);
    }

    // 복권 이벤트 처리
    public UserSearchResponse handleLotteryEvent(String email, int eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (user.getTg() < 1000) {
            throw new CustomException(ErrorCode.BAD_REQUEST);  // 참가 비용 부족 예외 처리
        }

        user.setTg(user.getTg() - 1000);
        int prize = rand.nextInt(10001);
        user.setTg(user.getTg() + prize);

        userRepository.save(user);
        return new UserSearchResponse(user, eventCategory, prize, null, null);
    }

    // 카메라 이벤트 처리
    public UserSearchResponse handleCameraEvent(String email, int eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<String> results = List.of("TG", "꽝", "EXP", "일일한도");
        String result = results.get(rand.nextInt(results.size()));

        Integer tgChange = null;
        Integer expChange = null;
        Integer possibleSearchChange = null;

        if ("TG".equals(result)) {
            tgChange = rand.nextInt(1000);
            user.addTg(tgChange);
        } else if ("EXP".equals(result)) {
            expChange = rand.nextInt(100);
            user.addExperience(expChange);
        } else if ("일일한도".equals(result)) {
            possibleSearchChange = 1;
            user.recoverPossibleSearch(possibleSearchChange);
        }

        userRepository.save(user);
        return new UserSearchResponse(user, eventCategory, tgChange, expChange, possibleSearchChange);
    }

    // 이벤트 확률 클래스
    static class EventProbability {
        private final int eventCode;
        private final double probability;

        public EventProbability(int eventCode, double probability) {
            this.eventCode = eventCode;
            this.probability = probability;
        }

        public int getEventCode() {
            return eventCode;
        }

        public double getProbability() {
            return probability;
        }
    }
}