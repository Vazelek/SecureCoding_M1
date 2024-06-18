package hr.ticketmaster.finder.ai.ticketmasterfinderai.service;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.RefreshToken;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.UserInfo;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.repository.RefreshTokenRepository;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        UserInfo userInfo = userRepository.findByUsername(username);

        // Check if a refresh token already exists for the user
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserInfo(userInfo);
        if (existingToken.isPresent()) {
            // Handle the case where a refresh token already exists
            // For example, update the existing token
            RefreshToken refreshToken = existingToken.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(600000)); // Update expiry date
            return refreshTokenRepository.save(refreshToken);
        }

        // Create a new token if one does not exist
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // Set expiry of refresh token to 10 minutes
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}