package com.guarino.ingsw.repository;

import com.guarino.ingsw.model.Post;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
