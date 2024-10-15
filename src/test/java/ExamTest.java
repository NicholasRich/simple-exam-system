import com.boyang.exam.interfaces.Question;
import com.boyang.exam.classes.QuestionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExamTest {

    @BeforeAll
    public static void initQuestionPool() {
        for (int i = 0; i < 5; i++) {
            Question freeResponseQuestion = QuestionFactory.getInstance(
                    QuestionFactory.FREE_RESPONSE,
                    "Free response question " + (i + 1),
                    "my answer");
            System.out.println(freeResponseQuestion.getFormulation());
            Question MultipleChoicesQuestion = QuestionFactory.getInstance(
                    QuestionFactory.MULTIPLE_CHOICES,
                    "Multiple choices question " + (i + 1),
                    "A,B,C,D");
            System.out.println(MultipleChoicesQuestion.getFormulation());
        }
    }

    @Test
    public void CheckFreeResponseQuestion() {
        List<Question> questionList = QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE);
        for (Question question : questionList) {
            assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(""));
            assertTrue(question.checkAnswer(" MY   AnSWER  "));
            assertFalse(question.checkAnswer("AnSWER"));
        }

    }

    @Test
    public void CheckMultipleChoicesQuestion() {
        List<Question> questionList = QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES);
        for (Question question : questionList) {
            assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(""));
            assertTrue(question.checkAnswer("  B,  D,  c,a "));
            assertFalse(question.checkAnswer("A,A,b"));
        }

    }

}
