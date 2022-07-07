package project.spring.redditspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.spring.redditspring.model.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private VoteType voteType;
}
