package com.guarino.ingsw.controller;

import com.guarino.ingsw.config.JwtTokenUtil;
import com.guarino.ingsw.dto.PostDTO;
import com.guarino.ingsw.service.PostService;
import com.guarino.ingsw.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {


    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should List All Posts When making GET request to endpoint - /api/posts/")
    void shouldCreatePost() throws Exception {

        PostDTO postRequest1 = new PostDTO();
        PostDTO postRequest2 = new PostDTO();


        // Mockito.when(postService.getAllPosts()).thenReturn(asList(postRequest1, postRequest2));
        Mockito.when(postService.getAllPosts()).thenReturn(List.of(postRequest1, postRequest2));

        mockMvc.perform(get("/api/posts/"))
                .andExpect(status().is(200))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect((ResultMatcher) jsonPath("$.size()", Matchers.is(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", Matchers.is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].postName", Matchers.is("Post Name")))
                .andExpect((ResultMatcher) jsonPath("$[0].url", Matchers.is("http://url.site")))
                .andExpect((ResultMatcher) jsonPath("$[1].url", Matchers.is("http://url2.site2")))
                .andExpect((ResultMatcher) jsonPath("$[1].postName", Matchers.is("Post Name 2")))
                .andExpect((ResultMatcher) jsonPath("$[1].id", Matchers.is(2)));
    }


}