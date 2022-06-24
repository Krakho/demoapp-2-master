package com.guarino.ingsw.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private VoteType voteType;

    @ManyToOne(fetch = LAZY)
    private Post post;
    @ManyToOne(fetch = LAZY)
    private User user;

    public Vote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long voteId) {
        this.id = voteId;
    }

    public VoteType getType() {
        return voteType;
    }

    public void setType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public static final class Builder {
        private Long id;
        private VoteType voteType;
        private Post post;
        private User user;

        private Builder() {
        }

        public static Builder aVote() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        public Builder withPost(Post post) {
            this.post = post;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Vote build() {
            Vote vote = new Vote();
            vote.setId(id);
            vote.setType(voteType);
            vote.setPost(post);
            vote.setUser(user);
            return vote;
        }
    }
}
