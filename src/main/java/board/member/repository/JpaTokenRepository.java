package board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import board.member.entity.TokenEntity;

public interface JpaTokenRepository extends JpaRepository<TokenEntity, String>  {
	TokenEntity findByUserId(String userId);
	
	boolean existsByUserId(String userId);

	TokenEntity findByRefreshToken(String refreshToken);
}
