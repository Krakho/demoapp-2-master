package com.guarino.ingsw.repository;

import com.guarino.ingsw.model.Comment;
import com.guarino.ingsw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    List<Comment> findAllByUser(User user);
}
