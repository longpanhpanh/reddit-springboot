package project.spring.redditspring.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import project.spring.redditspring.dto.VoteDto;
import project.spring.redditspring.dto.VoteResponse;
import project.spring.redditspring.model.User;
import project.spring.redditspring.model.Vote;
import project.spring.redditspring.repository.UserRepository;
import project.spring.redditspring.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponse> vote(@RequestBody VoteDto voteDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(voteService.vote(voteDto));

    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<VoteResponse>> getVoteByUser(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(voteService.getVotesByUser(username));
    }
}
