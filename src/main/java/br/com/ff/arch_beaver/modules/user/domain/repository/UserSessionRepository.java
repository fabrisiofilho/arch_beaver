package br.com.ff.arch_beaver.modules.user.domain.repository;

import br.com.ff.arch_beaver.modules.user.domain.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

    @Query("SELECT use FROM UserSessionEntity as use WHERE use.token = :token AND (use.term > :now OR use.term IS NULL)")
    Optional<UserSessionEntity> findByToken(String token, LocalDateTime now);

}
