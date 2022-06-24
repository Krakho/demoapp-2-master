package com.guarino.ingsw.mapper;

import com.guarino.ingsw.dto.PostDTO;
import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.Vote;
import com.guarino.ingsw.model.VoteType;
import com.guarino.ingsw.repository.CommentRepository;
import com.guarino.ingsw.repository.VoteRepository;
import com.guarino.ingsw.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static com.guarino.ingsw.model.VoteType.DOWNVOTE;
import static com.guarino.ingsw.model.VoteType.UPVOTE;

@Component
public class PostMapper implements Mapper<Post, PostDTO> {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    AuthService authService;

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

    @Override
    public PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.id = post.getId();
        dto.commentCount = post.getComments().size();
        dto.url = post.getUrl();
        dto.voteCount = post.getVoteCount();
        dto.description = post.getDescription();
        dto.subsectionName = post.getSubsection().getName();
        dto.postName = post.getName();
        dto.userName = post.getUser().getUsername();
        dto.duration = Instant.now().getEpochSecond() - post.getCreatedDate().getEpochSecond();

        return dto;
    }
}