package com.travelgo.backend.domain.userItems.repository;

import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserItemsRepository extends JpaRepository<UserItems, Long> {
//    @Query("SELECT ui FROM UserItems ui WHERE ui.user.email= :email")
//    List<UserItems> findAllByUser_Email(@Param("email") String email);

    List<UserItems> findAllByUser_Email(String email);

    boolean existsByUserAndItem(User user, Item selectedItem);
}
