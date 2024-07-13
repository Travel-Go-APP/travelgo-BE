package com.travelgo.backend.domain.item.repository;

import com.travelgo.backend.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemId(Long itemId);
}
