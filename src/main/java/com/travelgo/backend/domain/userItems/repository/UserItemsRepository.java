package com.travelgo.backend.domain.userItems.repository;

import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserItemsRepository extends JpaRepository<UserItems, Long> {
    Optional<UserItems> findById(Long id);
    List<UserItems> findAllByUser_UserId(Long userId);
}
