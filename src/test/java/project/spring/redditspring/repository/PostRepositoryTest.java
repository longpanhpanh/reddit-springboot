package project.spring.redditspring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.spring.redditspring.BaseTest;
import project.spring.redditspring.model.Post;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends BaseTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost() {
        Post expectedPostObject = new Post(123L,
                "first post",
                "http://url.site",
                "no description",
                0,
                "http://img.site",
                null,
                Instant.now(),
                null);
        Post actualPostObject = postRepository.save(expectedPostObject);
        assertThat(actualPostObject).usingRecursiveComparison()
                .ignoringFields("postId").isEqualTo(expectedPostObject);
    }

}

