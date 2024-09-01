package com.travelgo.backend.domain.userItems.repository;

import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserItemsRepository extends JpaRepository<UserItems, Long> {
//    @Query("SELECT ui FROM UserItems ui WHERE ui.user.email= :email")
//    List<UserItems> findAllByUser_Email(@Param("email") String email);

    List<UserItems> findAllByUser_Email(String email);

    boolean existsByUserAndItem(User user, Item selectedItem);

    Optional<UserItems> findByUserAndItem(User user, Item item);
}
