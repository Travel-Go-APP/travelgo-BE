package com.travelgo.backend.redis.repository;


import com.travelgo.backend.redis.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findById(String id);

    Boolean existsByAccessToken(String accessToken);
}
