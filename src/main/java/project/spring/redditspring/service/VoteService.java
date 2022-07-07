package project.spring.redditspring.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.spring.redditspring.dto.VoteDto;
import project.spring.redditspring.dto.VoteResponse;
import project.spring.redditspring.exception.PostNotFoundException;
import project.spring.redditspring.exception.SpringRedditException;
import project.spring.redditspring.model.Post;
import project.spring.redditspring.model.User;
import project.spring.redditspring.model.Vote;
import project.spring.redditspring.repository.PostRepository;
import project.spring.redditspring.repository.UserRepository;
import project.spring.redditspring.repository.VoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static project.spring.redditspring.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;


    @Transactional
    public VoteResponse vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        // Check if the current user has already upvoted/downvoted
        Optional<Vote> voteByPostAndUser =
                voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "D for this post");
        }
        if (!voteByPostAndUser.isPresent()) {
            post.setVoteCount(post.getVoteCount() + 1);
        }
        // Delete all voted in this post by current user
        voteRepository.deleteAllByUserAndPost(authService.getCurrentUser(), post);
        postRepository.save(post);

        // Save the vote
        Vote vote = voteRepository.save(mapToVote(voteDto, post));


        return maptoVoteResponse(vote);
    }

    public List<VoteResponse> getVotesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with prodivded username"));
         return voteRepository.findAllByUser(user)
                 .stream()
                 .map(this::maptoVoteResponse)
                 .collect(Collectors.toList());
    }

    private VoteResponse maptoVoteResponse(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getVoteId())
                .postId(vote.getPost().getPostId())
                .userId(vote.getUser().getUserId())
                .voteType(vote.getVoteType())
                .build();
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
