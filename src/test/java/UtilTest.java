import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import com.boyang.assessment.utils.QuizUtil;
import com.boyang.assessment.utils.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test all the methods in <code>com.boyang.assessment.utils</code> package.
 *
 * @author Boyang Wang
 */
public class UtilTest {
    /**
     * Test {@link StringUtil#isEmpty(String)}.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty("null"));
    }

    /**
     * Test {@link QuizUtil#getRandomQuestions(int, List)}.
     */
    @Test
    public void testGetRandomQuestions() {
        assertThrowsExactly(IllegalArgumentException.class, () -> QuizUtil.getRandomQuestions(0, null));
        assertThrowsExactly(IllegalArgumentException.class, () -> QuizUtil.getRandomQuestions(4, null));
        for (int i = 0; i < 5; i++) {
            QuestionFactory.getInstance("Free response question " + (i + 1), "my answer");
        }
        List<Question> pool = QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE);
        assertThrowsExactly(IllegalArgumentException.class, () -> QuizUtil.getRandomQuestions(6, pool));
        List<Question> randomQuestions = QuizUtil.getRandomQuestions(3, pool);
        assertTrue(pool.containsAll(randomQuestions));
        assertEquals(randomQuestions.size(), new HashSet<>(randomQuestions).size());
    }
}
