package project.spring.redditspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditSpringApplication.class, args);
    }
        
}
