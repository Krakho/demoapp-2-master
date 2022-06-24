package com.guarino.ingsw.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Subsection {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;

    @CreationTimestamp
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    private User user;

    public Subsection() {
    }

    public Subsection(Long id, String name, String description, List<Post> posts, Instant createdDate, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posts = posts;
        this.createdDate = createdDate;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumberOfPosts() {
        return this.posts.size();
    }

    public void addPost(Post post) {
        post.setSubsection(this);
        this.posts.add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subsection that = (Subsection) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static final class Builder {
        private String name;
        private String description;
        private List<Post> posts;
        private Instant createdDate;
        private User user;

        private Builder() {
        }

        public static Builder aSubsection() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPosts(List<Post> posts) {
            this.posts = posts;
            return this;
        }

        public Builder withCreatedDate(Instant createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Subsection build() {
            Subsection subsection = new Subsection();
            subsection.setName(name);
            subsection.setDescription(description);
            subsection.setPosts(posts);
            subsection.setCreatedDate(createdDate);
            subsection.setUser(user);
            return subsection;
        }
    }
}
