import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import com.boyang.assessment.quiz.StudentQuiz;
import com.boyang.assessment.student.Statistic;
import com.boyang.assessment.student.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * Test the methods in {@link StudentQuiz}
 *
 * @author Boyang Wang
 */
public class StudentQuizTest {
    /**
     * Init the question pool contains 5 free response questions and 5 multiple choices questions.
     */
    @BeforeAll
    public static void initQuestionPool() {
        for (int i = 0; i < 5; i++) {
            QuestionFactory.getInstance("Free response question " + (i + 1), "my answer");
            QuestionFactory.getInstance("Multiple choices question " + (i + 1), Arrays.asList("A", "B", "C", "D"));
        }
    }

    /**
     * Test {@link StudentQuiz#getInstance()}.
     */
    @Test
    public void getInstanceTest() {
        StudentQuiz instance = StudentQuiz.getInstance();
        assertEquals(StudentQuiz.class, instance.getClass());
    }

    /**
     * Test {@link StudentQuiz#generateQuiz(int)}.
     */
    @Test
    public void generateQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.generateQuiz(100));
        List<Question> questions = studentQuiz.generateQuiz(5);
        assertEquals(5, questions.size());
        assertEquals(questions.size(), new HashSet<>(questions).size());
    }

    /**
     * Test {@link StudentQuiz#takeQuiz(Student, List, List)}.
     */
    @Test
    public void takeQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.takeQuiz(null, null, null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeQuiz(student, null, null));
        List<Question> questions = Arrays.asList(
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(1),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(1));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeQuiz(student, questions, null));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D")));

        studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        assertEquals(1, student.getStatistic().getAttempts());
        assertEquals(Statistic.PASS, student.getStatistic().getVerdict());
        assertEquals(1.0, student.getStatistic().getAttemptScores().getFirst());
        assertThrowsExactly(IllegalArgumentException.class, () ->
                studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D")));
    }

    /**
     * Test {@link StudentQuiz#revise(Student, int)}.
     */
    @Test
    public void reviseTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.revise(null, 5));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.revise(student, 100));
        List<Question> questions = Arrays.asList(
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(1),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(1));
        studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        List<Question> revise = studentQuiz.revise(student, 6);
        List<Question> duplicatedRevise = revise.stream().filter(questions::contains).toList();
        assertEquals(0, duplicatedRevise.size());
    }

    /**
     * Test {@link StudentQuiz#takeRevisionQuiz(Student, List, List)}.
     */
    @Test
    public void takeRevisionQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.takeRevisionQuiz(null, null, null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeRevisionQuiz(student, null, null));
        List<Question> questions = Arrays.asList(
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE).get(1),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(0),
                QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES).get(1));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeRevisionQuiz(student, questions, null));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D")));

        studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        assertEquals(1, student.getStatistic().getRevisions());
        assertEquals(1.0, student.getStatistic().getRevisionScores().getFirst());
        studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C", "A,B,C"));
        assertEquals(2, student.getStatistic().getRevisions());
        assertEquals(0.5, student.getStatistic().getRevisionScores().getLast());
        assertThrowsExactly(IllegalArgumentException.class, () ->
                studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D")));
    }

    /**
     * Test {@link StudentQuiz#generateStatistics(Student)}.
     */
    @Test
    public void generateStatisticsTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.generateStatistics(null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertEquals("""
                Mary Rose, 06/06/1996
                verdict: TBD
                attempts: 0, [null, null]
                revisions: 0, [null, null]""", studentQuiz.generateStatistics(student));
    }
}
