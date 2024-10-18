import com.boyang.assessment.question.FreeResponseQuestion;
import com.boyang.assessment.question.MultipleChoicesQuestion;
import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test question related methods.
 *
 * @author Boyang Wang
 */
public class QuestionTest {

    /**
     * Test {@link QuestionFactory#getInstance(String, List)} and {@link QuestionFactory#getInstance(String, List)}.
     */
    @Test
    public void questionInstanceTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> QuestionFactory.getInstance(null, "answer"));
        assertThrowsExactly(IllegalArgumentException.class, () -> QuestionFactory.getInstance("question", List.of()));
        assertSame(FreeResponseQuestion.class, QuestionFactory.getInstance("question", "answer").getClass());
        assertSame(MultipleChoicesQuestion.class, QuestionFactory.getInstance("question", List.of("A", "B")).getClass());
    }

    /**
     * Test {@link QuestionFactory#equals(Object)}.
     */
    @Test
    public void questionEqualsTest() {
        assertEquals(QuestionFactory.getInstance("question", "answer"),
                QuestionFactory.getInstance("question", "answer"));
        assertNotEquals(QuestionFactory.getInstance("question", "answer"),
                QuestionFactory.getInstance("#question", "answer"));
    }

    /**
     * Test{@link QuestionFactory#getFormulation()}
     */
    @Test
    public void getFormulationTest() {
        Question question = QuestionFactory.getInstance("question", "answer");
        assertEquals("question", question.getFormulation());
    }

    /**
     * Test {@link FreeResponseQuestion#checkAnswer(String)}
     */
    @Test
    public void checkFreeResponseQuestionTest() {
        Question question = QuestionFactory.getInstance("Free response question", "my answer");
        assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(null));
        assertTrue(question.checkAnswer(" MY   AnSWER  "));
        assertFalse(question.checkAnswer("AnSWER"));
    }

    /**
     * Test {@link MultipleChoicesQuestion#checkAnswer(String)}
     */
    @Test
    public void checkMultipleChoicesQuestionTest() {
        Question question = QuestionFactory.getInstance("Multiple choices question", List.of("A", "B", "C", "D"));
        assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(null));
        assertTrue(question.checkAnswer("  B,  D,  c,a "));
        assertFalse(question.checkAnswer("A,b"));
    }
}
