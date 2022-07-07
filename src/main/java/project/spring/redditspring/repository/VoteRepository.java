package project.spring.redditspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.redditspring.model.Post;
import project.spring.redditspring.model.User;
import project.spring.redditspring.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
    List<Vote> findAllByUser(User user);
    void deleteAllByUserAndPost(User user, Post post);
}
