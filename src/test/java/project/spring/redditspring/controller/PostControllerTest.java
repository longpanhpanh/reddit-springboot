package project.spring.redditspring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.spring.redditspring.security.JwtProvider;
import project.spring.redditspring.service.PostService;
import project.spring.redditspring.service.UserDetailsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Shoul get list all the posts when making a GET request to endpoint: /api/posts ")
    public void shouldGetAllPosts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}