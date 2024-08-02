package com.travelgo.backend.domain.userItems.repository;

import com.travelgo.backend.domain.userItems.entity.UserItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserItemsRepository extends JpaRepository<UserItems, Long> {
    @Query("SELECT ui FROM UserItems ui WHERE ui.user.email= :email")
    List<UserItems> findAllByUserEmail(@Param("email") String email);
}
