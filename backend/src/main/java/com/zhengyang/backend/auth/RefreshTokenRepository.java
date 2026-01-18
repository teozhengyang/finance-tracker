package com.zhengyang.backend.auth;

import com.zhengyang.backend.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    
    Optional<RefreshTokenEntity> findByToken(String token);
    
    @Modifying
    int deleteByUser(UserEntity user);
    
    @Modifying
    int deleteByExpiryDateBefore(Instant now);
}
