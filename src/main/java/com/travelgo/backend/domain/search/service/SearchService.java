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

    public String selectEvent() {
        List<EventProbability> eventProbabilities = List.of(
                new EventProbability("동전을 주웠다!", 15),
                new EventProbability("이벤트에 당첨되었다!", 10),
                new EventProbability("마음의 양식을 쌓았다!", 15),
                new EventProbability("헬스장을 다녀왔다!", 5),
                new EventProbability("보조배터리로 핸드폰을 충전했다", 5),
                new EventProbability("카페에서 편안한 휴식을 즐겼다!", 10),
                new EventProbability("드링크를 마시니깐 힘이 생긴다", 5),
                new EventProbability("몸살이 걸린 것 같다...", 5),
                new EventProbability("젠장 함정에 빠졌다!", 3),
                new EventProbability("온 우주가 나에게 들어오는 기분이다", 1),
                new EventProbability("지갑을 발견했다!", 6.5),
                new EventProbability("카메라를 발견했다!", 6.5),
                new EventProbability("수상한 상인이다", 6.5),
                new EventProbability("오늘 복권 발표날이다", 6.5)
        );

        double totalProbability = eventProbabilities.stream().mapToDouble(EventProbability::getProbability).sum();
        double randomValue = new Random().nextDouble() * totalProbability;

        double cumulativeProbability = 0.0;
        for (EventProbability event : eventProbabilities) {
            cumulativeProbability += event.getProbability();
            if (randomValue <= cumulativeProbability) {
                return event.getEventName();
            }
        }

        return null;
    }

    public UserSearchResponse handleSelectedEvent(User user, String selectedEvent) {
        Integer tgChange = null;
        Integer expChange = null;
        Integer possibleSearchChange = null;
        String result;

        switch (selectedEvent) {
            case "동전을 주웠다!":
                tgChange = 100;
                user.addTg(tgChange);
                result = "100TG를 획득했습니다!";
                break;
            case "이벤트에 당첨되었다!":
                tgChange = 300;
                user.addTg(tgChange);
                result = "300TG를 획득했습니다!";
                break;
            case "마음의 양식을 쌓았다!":
                expChange = 100;
                user.addExperience(expChange);
                result = "100EXP를 획득했습니다!";
                break;
            case "헬스장을 다녀왔다!":
                expChange = 500;
                user.addExperience(expChange);
                result = "500EXP를 획득했습니다!";
                break;
            case "보조배터리로 핸드폰을 충전했다":
//                possibleSearchChange = 50; // 임시 예시로 50%
                result = "조사하기 게이지가 50% 회복되었습니다!";
                break;
            case "카페에서 편안한 휴식을 즐겼다!":
//                possibleSearchChange = 100; // 임시 예시로 100%
                result = "조사하기 게이지가 100% 회복되었습니다!";
                break;
            case "드링크를 마시니깐 힘이 생긴다":
                possibleSearchChange = 1;
                user.recoverPossibleSearch(possibleSearchChange);
                result = "일일 조사 가능 횟수가 1회 증가했습니다!";
                break;
            case "몸살이 걸린 것 같다...":
                possibleSearchChange = -2;
                user.decreasePossibleSearch(2);
                result = "일일 조사 가능 횟수가 2회 감소했습니다...";
                break;
            case "젠장 함정에 빠졌다!":
                tgChange = -user.getTg() * 20 / 100;
                user.loseTgPercentage(20);
                result = "보유 TG의 20%를 잃었습니다!";
                break;
            case "온 우주가 나에게 들어오는 기분이다.":
                user.levelUp();
                result = "LV 1이 증가했습니다!";
                break;
            case "지갑을 발견했다!":
                result = "지갑을 발견했습니다!";
                break;
            case "카메라를 발견했다!":
                result = handleCameraEvent(user.getEmail(), "카메라를 발견했다!").getResult();
                break;
            case "수상한 상인이다.":
                result = handleMerchantEvent(user.getEmail(), "수상한 상인이다!").getResult();
                break;
            case "오늘 복권 발표날이다.":
                result = handleLotteryEvent(user.getEmail(), "오늘 복권 발표날이다!").getResult();
                break;
            default:
                throw new CustomException(ErrorCode.BAD_REQUEST); // 예외 처리
        }
        userRepository.save(user);

        return new UserSearchResponse(user, selectedEvent, result, tgChange, expChange, possibleSearchChange);
    }

    public UserSearchResponse handleWalletEvent(String email, boolean takeAction, String eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Integer tgChange = null;
        String result;

        if (takeAction) {
            tgChange = 500;//0-500 랜덤 (money response 추가)
            user.addTg(tgChange);
            result = "지갑을 갖다준다";
        } else {
            tgChange = -500;
            user.addTg(tgChange); // 임의로 500TG 감소
            result = "지갑을 가져갔습니다!";
        }

        userRepository.save(user);

        return new UserSearchResponse(user, eventCategory, result, tgChange, null, null);
    }

    public UserSearchResponse handleMerchantEvent(String email, String eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Random rand = new Random(); //
        List<String> merchantResults = List.of(
                "TG " + (rand.nextInt(4000) - 2000),
                "TG " + (rand.nextInt(4000) - 2000),
                "EXP " + rand.nextInt(1001),
                "EXP " + rand.nextInt(1001),
                "꽝"
        );
        String result = String.join(", ", merchantResults);

        return new UserSearchResponse(user, eventCategory, result, null, null, null);
    }

    public UserSearchResponse handleLotteryEvent(String email, String eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (user.getTg() < 1000) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
            //이 부분 돈이 충분하지 않다고 바꿔야 됨. 참가 비용 부족 에러.
        }

        user.setTg(user.getTg() - 1000);
        int prize = new Random().nextInt(10001);
        user.setTg(user.getTg() + prize);

        userRepository.save(user);

        String result = "복권 상금으로 TG "+prize+"획득";

        return new UserSearchResponse(user, eventCategory, result, prize, null, null);
    }

    public UserSearchResponse handleCameraEvent(String email, String eventCategory) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Random rand = new Random();
        List<String> results = List.of("TG", "꽝", "EXP", "일일한도");
        String result = results.get(rand.nextInt(results.size()));

        Integer tgChange = null;
        Integer expChange = null;

        if ("TG".equals(result)) {
            tgChange = rand.nextInt(1000);
            user.addTg(tgChange);
        } else if ("EXP".equals(result)) {
            expChange = rand.nextInt(100);
            user.addExperience(expChange);
        }

        userRepository.save(user);

        return new UserSearchResponse(user, eventCategory, result, tgChange, expChange, null);
    }

    static class EventProbability {
        private final String eventName;
        private final double probability;

        public EventProbability(String eventName, double probability) {
            this.eventName = eventName;
            this.probability = probability;
        }

        public String getEventName() {
            return eventName;
        }

        public double getProbability() {
            return probability;
        }
    }
}