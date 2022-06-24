package com.guarino.ingsw.service;

import com.guarino.ingsw.dto.PostCreationResponse;
import com.guarino.ingsw.dto.PostDTO;
import com.guarino.ingsw.dto.PostRequest;
import com.guarino.ingsw.exceptions.PostNotFoundException;
import com.guarino.ingsw.exceptions.SubsectionNotFoundException;
import com.guarino.ingsw.mapper.PostMapper;
import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.Subsection;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.repository.PostRepository;
import com.guarino.ingsw.repository.SubsectionRepository;
import com.guarino.ingsw.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional
public class PostService {

    @Autowired
    SubsectionRepository subsectionRepository;
    @Autowired
    AuthService authService;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public PostCreationResponse create(PostRequest postRequest) {
        Subsection subsection = subsectionRepository.findById(postRequest.getSubsectionId())
                .orElseThrow(() -> new SubsectionNotFoundException("Subsection not found: " + postRequest.getSubsectionId()));
        User user = authService.getCurrentUser();
        Post post = Post.Builder.aPost()
                .withName(postRequest.getPostName())
                .withDescription(postRequest.getDescription())
                .withSubsection(subsection)
                .withUser(user)
                .withUrl(postRequest.getUrl())
                .build();
        post.setSubsection(subsection);
        post = postRepository.save(post);

        subsection.addPost(post);
        subsection = subsectionRepository.save(subsection);

        return new PostCreationResponse(post.getId(), subsection.getId(), post.getName(), post.getUser().getId(), post.getUrl(), post.getDescription());
    }


    @Transactional(readOnly = true)
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.toDTO(post);
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostsBySubsection(Long subsectionId) {
        Subsection subsection = subsectionRepository.findById(subsectionId)
                .orElseThrow(() -> new SubsectionNotFoundException(subsectionId.toString()));
        List<Post> posts = postRepository.findAllBySubsection(subsection);
        return posts.stream().map(postMapper::toDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::toDTO)
                .collect(toList());
    }
}
