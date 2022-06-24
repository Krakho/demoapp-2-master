package com.guarino.ingsw.service;

import com.guarino.ingsw.exceptions.DemoAppException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class CommentServiceTest {

    CommentService commentService = new CommentService();

    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear Words")
    public void shouldNotContainSwearWordsInsideComment() {

        assertThat(commentService.containsSwearWords("This is a comment")).isFalse();
    }

    @Test
    @DisplayName("Should Throw Exception when Exception Contains Swear Words")
    public void shouldFailWhenCommentContainsSwearWords() {

        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(DemoAppException.class)
                .hasMessage("Comments contains unacceptable language");
    }


}