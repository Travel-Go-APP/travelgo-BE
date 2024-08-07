package com.travelgo.backend.auth.service;

import com.travelgo.backend.auth.exception.TokenException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import com.travelgo.backend.redis.entity.Token;
import com.travelgo.backend.redis.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void deleteRefreshToken(String email) {
        tokenRepository.deleteById(email);
    }

    @Transactional
    public void saveOrUpdate(String email, String refreshToken, String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken)
                .map(o -> o.updateRefreshToken(refreshToken))
                .orElseGet(() -> new Token(email, refreshToken, accessToken));

        tokenRepository.save(token);
    }

    public Token findByEmailOrThrow(String email){
        return tokenRepository.findById(email)
                .orElseThrow(() -> new TokenException(ErrorCode.NOT_FOUND_TOKEN));
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(ErrorCode.TOKEN_EXPIRED));
    }

    public Boolean checkToken(String accessToken) {
        return tokenRepository.existsByAccessToken(accessToken);
    }

    @Transactional
    public void updateToken(String accessToken, Token token) {
        token.updateAccessToken(accessToken);
        tokenRepository.save(token);
    }
}
