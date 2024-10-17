import com.boyang.exam.question.FreeResponseQuestion;
import com.boyang.exam.question.MultipleChoicesQuestion;
import com.boyang.exam.question.Question;
import com.boyang.exam.question.QuestionFactory;
import com.boyang.exam.quiz.StudentQuiz;
import com.boyang.exam.student.Statistic;
import com.boyang.exam.student.Student;
import com.boyang.exam.utils.QuestionUtil;
import com.boyang.exam.utils.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExamTest {

    @BeforeAll
    public static void initQuestionPool() {
        for (int i = 0; i < 5; i++) {
            QuestionFactory.getInstance("Free response question " + (i + 1), "my answer");
            QuestionFactory.getInstance("Multiple choices question " + (i + 1), Arrays.asList("A", "B", "C", "D"));
        }
    }

    @Test
    public void questionInstanceTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> QuestionFactory.getInstance(null, ""));
        assertThrowsExactly(IllegalArgumentException.class, () -> QuestionFactory.getInstance("", List.of()));
        assertSame(FreeResponseQuestion.class, QuestionFactory.getInstance("Free response question", "my answer").getClass());
        assertSame(MultipleChoicesQuestion.class, QuestionFactory.getInstance("Multiple choices question", List.of("A", "B")).getClass());
    }

    @Test
    public void questionEqualsTest() {
        assertEquals(QuestionFactory.getInstance("question", "answer"),
                QuestionFactory.getInstance("question", "answer"));
        assertNotEquals(QuestionFactory.getInstance("question", "answer"),
                QuestionFactory.getInstance("#question", "answer"));
    }

    @Test
    public void checkFreeResponseQuestion() {
        Question question = QuestionFactory.getInstance("Free response question", "my answer");
        assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(null));
        assertTrue(question.checkAnswer(" MY   AnSWER  "));
        assertFalse(question.checkAnswer("AnSWER"));
    }

    @Test
    public void checkMultipleChoicesQuestion() {
        Question question = QuestionFactory.getInstance("Multiple choices question", List.of("A", "B", "C", "D"));
        assertThrowsExactly(IllegalArgumentException.class, () -> question.checkAnswer(null));
        assertTrue(question.checkAnswer("  B,  D,  c,a "));
        assertFalse(question.checkAnswer("A,A,b"));
    }

    @Test
    public void studentConstructorTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new Student(null, null, null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertEquals("Mary", student.getFirstName());
        assertEquals("Rose", student.getLastName());
        assertEquals(0, calendar.getTime().compareTo(student.getBirthday()));
    }

    @Test
    public void studentToStringTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertEquals("Mary Rose, 06/06/1996", student.toString());
    }

    @Test
    public void studentEqualsTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student1 = new Student("Mary", "Rose", calendar.getTime());
        Student student2 = new Student("Mary", "Rose", calendar.getTime());
        assertEquals(student1, student2);
    }

    @Test
    public void statisticSetVerdictTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setVerdict(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setVerdict("##"));
        statistic.setVerdict(Statistic.PASS);
        assertEquals(Statistic.PASS, statistic.getVerdict());
    }

    @Test
    public void statisticSetAttemptsTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setAttempts(100));
        statistic.setAttempts(2);
        assertEquals(2, statistic.getAttempts());
    }

    @Test
    public void statisticSetRevisionsTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setRevisions(100));
        statistic.setRevisions(2);
        assertEquals(2, statistic.getRevisions());
    }

    @Test
    public void statisticToStringTest() {
        Statistic statistic = new Statistic();
        assertEquals("""
                verdict: TBD
                attempts: 0, [null, null]
                revisions: 0, [null, null]""", statistic.toString());
    }

    @Test
    public void generateQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.generateQuiz(100));
        List<Question> questions = studentQuiz.generateQuiz(5);
        assertEquals(5, questions.size());
        assertEquals(questions.size(), new HashSet<>(questions).size());
    }

    @Test
    public void reviseTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.revise(null, 100));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        List<Question> questions = Arrays.asList(
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(0),
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(1),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(0),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(1));
        studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        List<Question> revise = studentQuiz.revise(student, 4);
        assertFalse(revise.containsAll(questions));
    }

    @Test
    public void takeQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.takeQuiz(null, null, null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        List<Question> questions = Arrays.asList(
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(0),
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(1),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(0),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(1));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeQuiz(student, new ArrayList<>(), new ArrayList<>()));
        studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        assertEquals(1, student.getStatistic().getAttempts());
        assertEquals(Statistic.PASS, student.getStatistic().getVerdict());
        assertEquals(1.0, student.getStatistic().getAttemptScores().getFirst());
        assertThrowsExactly(IllegalArgumentException.class, () ->
                studentQuiz.takeQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D")));
    }

    @Test
    public void takeRevisionQuizTest() {
        StudentQuiz studentQuiz = StudentQuiz.getInstance();
        assertThrowsExactly(NullPointerException.class, () -> studentQuiz.takeQuiz(null, null, null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        List<Question> questions = Arrays.asList(
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(0),
                QuestionFactory.FREE_RESPONSE_QUESTIONS.get(1),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(0),
                QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.get(1));
        assertThrowsExactly(IllegalArgumentException.class, () -> studentQuiz.takeRevisionQuiz(student, new ArrayList<>(), new ArrayList<>()));
        studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D"));
        assertEquals(1, student.getStatistic().getRevisions());
        assertEquals(1.0, student.getStatistic().getRevisionScores().getFirst());
        studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C", "A,B,C"));
        assertEquals(2, student.getStatistic().getRevisions());
        assertEquals(0.5, student.getStatistic().getRevisionScores().getLast());
        assertThrowsExactly(IllegalArgumentException.class, () ->
                studentQuiz.takeRevisionQuiz(student, questions, Arrays.asList("my answer", "my answer", "A,B,C,D", "A,B,C,D")));
    }

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

    @Test
    public void randomQuestionsTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> QuestionUtil.getRandomQuestions(0, null));
        List<Question> randomQuestions = QuestionUtil.getRandomQuestions(4, QuestionFactory.FREE_RESPONSE_QUESTIONS);
        assertEquals(4, randomQuestions.size());
        assertEquals(randomQuestions.size(), new HashSet<>(randomQuestions).size());
    }

    @Test
    public void isEmptyTest() {
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty("fsd"));
    }
}
