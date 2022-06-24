package com.guarino.ingsw.service;

import com.guarino.ingsw.dto.VoteDTO;
import com.guarino.ingsw.exceptions.DemoAppException;
import com.guarino.ingsw.exceptions.PostNotFoundException;
import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.Vote;
import com.guarino.ingsw.repository.PostRepository;
import com.guarino.ingsw.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.guarino.ingsw.model.VoteType.DOWNVOTE;
import static com.guarino.ingsw.model.VoteType.UPVOTE;


@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AuthService authService;

    @Transactional
    public void vote(VoteDTO voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getType()
                        .equals(voteDto.getVoteType())) {
            throw new DemoAppException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
            if ((voteByPostAndUser.isPresent()) && ((UPVOTE.equals(voteDto.getVoteType())))) {
                post.setVoteCount(post.getVoteCount() - 2);
            }
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
            if ((voteByPostAndUser.isPresent()) && ((DOWNVOTE.equals(voteDto.getVoteType())))) {
                post.setVoteCount(post.getVoteCount() + 2);
            }
        }


        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDTO voteDto, Post post) {
        return Vote.Builder.aVote()
                .withType(voteDto.getVoteType())
                .withPost(post)
                .withUser(authService.getCurrentUser())
                .build();
    }
}