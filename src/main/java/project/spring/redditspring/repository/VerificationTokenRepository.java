package project.spring.redditspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.spring.redditspring.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
