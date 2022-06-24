package com.guarino.ingsw.repository;

import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.Subsection;
import com.guarino.ingsw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p from Post p join fetch p.comments WHERE p.id = :postId")
    Optional<Post> findById(Long postId);

    List<Post> findAllBySubsection(Subsection subsection);

    List<Post> findByUser(User user);
}
