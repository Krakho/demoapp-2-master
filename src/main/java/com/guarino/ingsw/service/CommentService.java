package com.guarino.ingsw.service;

import com.guarino.ingsw.dto.CommentCreationResponse;
import com.guarino.ingsw.dto.CommentDTO;
import com.guarino.ingsw.exceptions.DemoAppException;
import com.guarino.ingsw.exceptions.PostNotFoundException;
import com.guarino.ingsw.mapper.implementations.CommentMapper;
import com.guarino.ingsw.model.Comment;
import com.guarino.ingsw.model.NotificationEmail;
import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.repository.CommentRepository;
import com.guarino.ingsw.repository.PostRepository;
import com.guarino.ingsw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentService {
    private static final String POST_URL = "";
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MailContentBuilder mailContentBuilder;
    @Autowired
    MailService mailService;


    @Transactional
    public CommentCreationResponse save(CommentDTO commentDto) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentDto.postId).orElseThrow(() -> new PostNotFoundException(commentDto.postId.toString()));
        Comment comment = Comment.Builder.aComment()
                .withCreatedDate(Instant.now())
                .withPost(post)
                .withText(commentDto.text)
                .withUser(user)
                .build();

        comment = commentRepository.save(comment);
        post.addComment(comment);

        post = postRepository.save(post);


        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

        return new CommentCreationResponse(comment.getId(), comment.getText(), comment.getUser().getId());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

        return post.getComments().stream()
                .map(commentMapper::toDTO)
                .collect(toList());

    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::toDTO)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new DemoAppException("Comments contains unacceptable language");
        }
        // se ritorniamo false il CommentServiceTest passerà, altrimenti fallirà
        return false;
    }
}


