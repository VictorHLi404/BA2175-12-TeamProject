package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private Quiz customQuiz;
    private Quiz publicQuiz;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password123");
        customQuiz = new Quiz(1, new ArrayList<>(), true, 5);
        publicQuiz = new Quiz(2, new ArrayList<>(), false, 10);
    }

    @Test
    public void testPlayAnyQuizAddsToPlayedQuizzes() {
        // The user did NOT create this quiz
        boolean played = user.playQuiz(publicQuiz);

        assertTrue(played);
        assertEquals(1, user.getPlayedQuizzes().size());
        assertTrue(user.getPlayedQuizzes().contains(publicQuiz));
    }

    @Test
    public void testPlaySameQuizTwiceDoesNotDuplicate() {
        user.playQuiz(publicQuiz);
        boolean playedAgain = user.playQuiz(publicQuiz);

        assertFalse(playedAgain); // Should not add twice
        assertEquals(1, user.getPlayedQuizzes().size());
    }

    @Test
    public void testPlayQuizNullReturnsFalse() {
        boolean played = user.playQuiz(null);

        assertFalse(played);
        assertTrue(user.getPlayedQuizzes().isEmpty());
    }

    @Test
    public void testCreateQuizAndPlayIt() {
        user.createQuiz(customQuiz);
        boolean played = user.playQuiz(customQuiz);

        assertTrue(played);
        assertTrue(user.getPlayedQuizzes().contains(customQuiz));
        assertTrue(user.getCreatedQuizzes().contains(customQuiz));
    }
}
