package com.travelgo.backend.config;

import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    private final UserRepository userRepository;

    @Autowired
    public SchedulingConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")// 서울 시간 자정에 실행
    @Transactional
    public void resetUserStatus() {
        List<User> users = userRepository.findAll();
        for(User user : users){
            user.setPossibleSearch(10);
            user.setQuest(0);
        }
        userRepository.saveAll(users);
    }

}
