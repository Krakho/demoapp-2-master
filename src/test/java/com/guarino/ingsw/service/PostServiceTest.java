package com.guarino.ingsw.service;

import com.guarino.ingsw.dto.PostDTO;
import com.guarino.ingsw.dto.PostRequest;
import com.guarino.ingsw.mapper.PostMapper;
import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.Subsection;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.repository.PostRepository;
import com.guarino.ingsw.repository.SubsectionRepository;
import com.guarino.ingsw.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SubsectionRepository subsectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PostMapper postMapper;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("Should Retrieve Post by Id")
    public void shouldFindPostById() {
        Post post = new Post();
        PostDTO expectedPostDTO = new PostDTO();

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.toDTO(any(Post.class))).thenReturn(expectedPostDTO);

        PostDTO actualPostDTO = postService.getPost(123L);

        Assertions.assertThat(actualPostDTO.id).isEqualTo(expectedPostDTO.id);
        Assertions.assertThat(actualPostDTO.postName).isEqualTo(expectedPostDTO.postName);
    }

    @Test
    @DisplayName("Should Save Posts")
    public void shouldSavePosts() {
        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        Subsection subsection = new Subsection(123L, "First Subsection", "Subsection Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null, emptyList());
        PostRequest postRequest = new PostRequest(subsection.getId(), "First Post", "http://url.site", "Test");

        Mockito.when(subsectionRepository.findById(subsection.getId()))
                .thenReturn(Optional.of(subsection));
        Mockito.when(authService.getCurrentUser())
                .thenReturn(currentUser);

        postService.create(postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getId()).isEqualTo(123L);
        Assertions.assertThat(postArgumentCaptor.getValue().getName()).isEqualTo("First Post");
    }

}