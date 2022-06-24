package com.guarino.ingsw.model;

import com.google.common.base.Objects;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Post Name cannot be empty or Null")
    private String name;

    @Nullable
    private String url;

    @Nullable
    @Lob
    private String description;

    private Integer voteCount = 0;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private User user;

    @CreationTimestamp
    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Subsection subsection;

    @OneToMany(fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
        //hibernate
    }

    public Post(Long id, String name, @Nullable String url, @Nullable String description, Integer voteCount, User user, Instant createdDate, Subsection subsection, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
        this.voteCount = voteCount;
        this.user = user;
        this.createdDate = createdDate;
        this.subsection = subsection;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Subsection getSubsection() {
        return subsection;
    }

    public void setSubsection(Subsection subsection) {
        this.subsection = subsection;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equal(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static final class Builder {
        private Long postId;
        private String postName;
        private String url;
        private String description;
        private Integer voteCount = 0;
        private User user;
        private Instant createdDate;
        private Subsection subsection;
        private List<Comment> comments = new ArrayList<>();

        private Builder() {
        }

        public static Builder aPost() {
            return new Builder();
        }

        public Builder withPostId(Long postId) {
            this.postId = this.postId;
            return this;
        }

        public Builder withName(String postName) {
            this.postName = postName;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withCreatedDate(Instant createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder withSubsection(Subsection subsection) {
            this.subsection = subsection;
            return this;
        }

        public Builder withComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Post build() {
            Post post = new Post();
            post.voteCount = this.voteCount;
            post.url = this.url;
            post.name = this.postName;
            post.user = this.user;
            post.description = this.description;
            post.subsection = this.subsection;
            post.id = this.postId;
            post.createdDate = this.createdDate;
            post.comments = this.comments;
            return post;
        }
    }
}