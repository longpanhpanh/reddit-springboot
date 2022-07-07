package project.spring.redditspring.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import project.spring.redditspring.dto.PostRequest;
import project.spring.redditspring.dto.PostResponse;
import project.spring.redditspring.mapper.PostMapper;
import project.spring.redditspring.model.Post;
import project.spring.redditspring.model.Subreddit;
import project.spring.redditspring.model.User;
import project.spring.redditspring.repository.PostRepository;
import project.spring.redditspring.repository.SubredditRepository;
import project.spring.redditspring.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private SubredditRepository subredditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private PostService postService;
    private PostResponse postResponse;
    private Post post;
    private PostRequest postRequest;

    @BeforeEach
    public void setup() {
        postService = new PostService(
                postRepository,
                subredditRepository,
                userRepository,
                authService,
                postMapper);

        postResponse = new PostResponse(123L,
                "first post",
                "http://url.site",
                "no description",
                "Test User",
                "SubredditName",
                0,
                0,
                "1 hour ago",
                "http://img.site",
                false,
                false);

        post = new Post(123L,
                "first post",
                "http://url.site",
                "no description",
                0,
                "http://img.site",
                null,
                Instant.now(),
                null);

        postRequest = new PostRequest(
                123L,
                "test subreddit",
                "first post",
                "http://url.site",
                "no description",
                "http://img.site"
        );
    }

    @Test
    @DisplayName("Should find a post by it's id")
    void shouldFindPostById() {

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(postResponse);

        PostResponse actualPostResponse = postService.getPost(123L);
        assertThat(actualPostResponse.getId()).isEqualTo(postResponse.getId());
        assertThat(actualPostResponse.getPostName()).isEqualTo(postResponse.getPostName());
    }

    @Test
    @DisplayName("Should save a post")
    void shouldSavePost() {

        User currentUser = new User(
                123L,
                "Test User",
                "Secret pasword",
                "user@gmail.com",
                Instant.now(),
                true
        );

        Subreddit subreddit = new Subreddit(
                123L,
                "test subreddit",
                "no description",
                Collections.emptyList(),
                Instant.now(),
                currentUser
        );


        Mockito.when(subredditRepository.findByName("test subreddit"))
                .thenReturn(Optional.of(subreddit));
        Mockito.when(postMapper.map(postRequest, subreddit, currentUser))
                .thenReturn(post);
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);


        postService.save(postRequest);

        Mockito.verify(postRepository,
                Mockito.times(1)).save(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
        assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("first post");
    }

    @Test
    @DisplayName("Should return a list of posts")
    void shouldGetAllPosts() {
        List<Post> expectedListPost = new ArrayList<>();
        expectedListPost.add(post);
        Mockito.when(postRepository.findAll()).thenReturn(expectedListPost);

       List<PostResponse> actualListPostResponse = postService.getAllPosts();
       assertThat(actualListPostResponse.size()).isEqualTo(expectedListPost.size());
    }

}