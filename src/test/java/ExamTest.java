import com.boyang.exam.classes.QuestionFactory;
import com.boyang.exam.classes.StudentExam;
import com.boyang.exam.interfaces.Exam;
import com.boyang.exam.interfaces.Question;
import com.boyang.exam.utils.QuestionUtil;
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
//            System.out.println(freeResponseQuestion.getFormulation());
            Question MultipleChoicesQuestion = QuestionFactory.getInstance(
                    QuestionFactory.MULTIPLE_CHOICES,
                    "Multiple choices question " + (i + 1),
                    "A,B,C,D");
//            System.out.println(MultipleChoicesQuestion.getFormulation());
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

    @Test
    public void randomQuestionsTest() {
        QuestionUtil.randomQuestions(4, QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE))
                .forEach(item -> System.out.println(item.getFormulation()));
        System.out.println(QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).size());
    }

    @Test
    public void generateQuizTest() {
        Exam exam = new StudentExam();
        List<Question> questions = exam.generateQuiz(8);
        questions.forEach(item -> System.out.println(item.getFormulation()));
    }

}
