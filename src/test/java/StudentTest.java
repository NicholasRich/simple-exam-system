import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import com.boyang.assessment.student.Statistic;
import com.boyang.assessment.student.Student;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the methods in {@link Student} and {@link Statistic}.
 *
 * @author Boyang Wang
 */
public class StudentTest {
    /**
     * Test {@link Student#Student(String, String, Date)}, {@link Student#getFirstName()},
     * {@link Student#getLastName()} and {@link Student#getBirthday()}.
     */
    @Test
    public void studentConstructorTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new Student(null, null, null));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Student("name", null, null));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Student("name", "name", null));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertEquals("Mary", student.getFirstName());
        assertEquals("Rose", student.getLastName());
        assertEquals(0, calendar.getTime().compareTo(student.getBirthday()));
    }

    /**
     * Test {@link Student#toString()}.
     */
    @Test
    public void studentToStringTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        assertEquals("Mary Rose, 06/06/1996", student.toString());
    }

    /**
     * Test {@link Student#equals(Object)}.
     */
    @Test
    public void studentEqualsTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student1 = new Student("Mary", "Rose", calendar.getTime());
        Student student2 = new Student("Mary", "Rose", calendar.getTime());
        assertEquals(student1, student2);
    }

    /**
     * Test {@link Student#getStatistic()} and all get methods in {@link Statistic}
     */
    @Test
    public void getStudentStatisticsTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, Calendar.JUNE, 6);
        Student student = new Student("Mary", "Rose", calendar.getTime());
        Statistic statistic = student.getStatistic();
        assertEquals(0, statistic.getAttempts());
        assertEquals(0, statistic.getRevisions());
        assertEquals("TBD", statistic.getVerdict());

        Map<String, Set<Question>> answeredQuestionMap = statistic.getAnsweredQuestionMap();
        assertTrue(answeredQuestionMap.containsKey(QuestionFactory.FREE_RESPONSE));
        assertTrue(answeredQuestionMap.containsKey(QuestionFactory.MULTIPLE_CHOICES));
        assertEquals(0, answeredQuestionMap.get(QuestionFactory.FREE_RESPONSE).size());
        assertEquals(0, answeredQuestionMap.get(QuestionFactory.MULTIPLE_CHOICES).size());

        List<Double> attemptScores = statistic.getAttemptScores();
        assertEquals(2, attemptScores.size());
        assertNull(attemptScores.getFirst());
        assertNull(attemptScores.getLast());

        List<Double> revisionScores = statistic.getRevisionScores();
        assertEquals(2, revisionScores.size());
        assertNull(revisionScores.getFirst());
        assertNull(revisionScores.getLast());
    }

    /**
     * Test {@link Statistic#setVerdict(String)}.
     */
    @Test
    public void statisticSetVerdictTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setVerdict(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setVerdict("##"));
        statistic.setVerdict(Statistic.PASS);
        assertEquals(Statistic.PASS, statistic.getVerdict());
    }

    /**
     * Test {@link Statistic#setAttempts(int)}.
     */
    @Test
    public void statisticSetAttemptsTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setAttempts(100));
        statistic.setVerdict(Statistic.PASS);
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setAttempts(2));
        statistic.setVerdict(Statistic.TBD);
        statistic.setAttempts(2);
        assertEquals(2, statistic.getAttempts());
    }

    /**
     * Test {@link Statistic#setRevisions(int)}.
     */
    @Test
    public void statisticSetRevisionsTest() {
        Statistic statistic = new Statistic();
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setRevisions(100));
        statistic.setVerdict(Statistic.PASS);
        assertThrowsExactly(IllegalArgumentException.class, () -> statistic.setRevisions(2));
        statistic.setVerdict(Statistic.TBD);
        statistic.setRevisions(2);
        assertEquals(2, statistic.getRevisions());
    }

    /**
     * Test {@link Statistic#toString()}.
     */
    @Test
    public void statisticToStringTest() {
        Statistic statistic = new Statistic();
        assertEquals("""
                verdict: TBD
                attempts: 0, [null, null]
                revisions: 0, [null, null]""", statistic.toString());
    }
}
