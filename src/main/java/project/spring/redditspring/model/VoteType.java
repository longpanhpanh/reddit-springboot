package project.spring.redditspring.model;

import org.springframework.expression.ExpressionException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1), ;

    private int direction;

    VoteType(int direction) {
    }


}
