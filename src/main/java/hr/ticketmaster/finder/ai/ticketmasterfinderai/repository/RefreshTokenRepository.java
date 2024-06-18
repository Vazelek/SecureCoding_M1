package hr.ticketmaster.finder.ai.ticketmasterfinderai.repository;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.RefreshToken;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfo(UserInfo userInfo);
}