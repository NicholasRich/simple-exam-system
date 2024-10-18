package com.boyang.assessment.student;

import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains some statistics of the quiz.
 *
 * @author Boyang Wang
 */
public class Statistic {
    /**
     * Denotes the quiz verdict - pass
     */
    public static String PASS = "PASS";

    /**
     * Denotes the quiz verdict - fail
     */
    public static String FAIL = "FAIL";

    /**
     * Denotes the quiz verdict - to be decided
     */
    public static String TBD = "TBD";

    /**
     * The verdict of the quiz. Default value: "TBD".
     */
    private String verdict = TBD;

    /**
     * The number of the attempt times for the regular quiz. Default value: 0.
     */
    private int attempts;

    /**
     * The number of times to take the revision quiz. Default value: 0.
     */
    private int revisions;

    /**
     * The scores of each regular quiz.
     */
    private final List<Double> attemptScores = Arrays.asList(null, null);

    /**
     * The scores of each revision quiz.
     */
    private final List<Double> revisionScores = Arrays.asList(null, null);

    /**
     * Log the answered question.
     */
    private final Map<String, Set<Question>> answeredQuestionMap = Map.of(
            QuestionFactory.FREE_RESPONSE, new HashSet<>(),
            QuestionFactory.MULTIPLE_CHOICES, new HashSet<>()
    );

    /**
     * Get the quiz verdict.
     *
     * @return The {@link Statistic#verdict} field.
     */
    public String getVerdict() {
        return verdict;
    }

    /**
     * Set the quiz verdict.
     *
     * @param verdict The verdict to be set.
     * @throws IllegalArgumentException If the verdict is null or empty.
     * @throws IllegalArgumentException If the verdict value is not {@link Statistic#PASS}, {@link Statistic#FAIL} or {@link Statistic#TBD}.
     */
    public void setVerdict(String verdict) {
        if (StringUtil.isEmpty(verdict)) {
            throw new IllegalArgumentException("The verdict cannot be null or empty!");
        }
        if (!Arrays.asList(PASS, FAIL, TBD).contains(verdict)) {
            throw new IllegalArgumentException("Unknown verdict: " + verdict + "!");
        }
        this.verdict = verdict;
    }

    /**
     * Get each regular quiz store.
     *
     * @return The {@link Statistic#attemptScores} field.
     */
    public List<Double> getAttemptScores() {
        return attemptScores;
    }

    /**
     * Get each revision quiz store.
     *
     * @return The quiz store list.
     */
    public List<Double> getRevisionScores() {
        return revisionScores;
    }

    /**
     * Get the number of time to take the regular quiz.
     *
     * @return The {@link Statistic#attempts} field.
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Set the number of time to take the regular quiz.
     *
     * @param attempts The number of time to take the regular quiz to be set.
     * @throws IllegalArgumentException If the {@link Statistic#verdict} is not {@link Statistic#TBD}
     * @throws IllegalArgumentException If the attempt count is less than 0 or greater than 2.
     */
    public void setAttempts(int attempts) {
        if (!verdict.equals(TBD)) {
            throw new IllegalArgumentException("The student who gets the verdict cannot take another quiz!");
        }
        if (attempts != 1 && attempts != 2) {
            throw new IllegalArgumentException("The attempt count can only be 1 or 2!");
        }
        this.attempts = attempts;
    }

    /**
     * Get the number of time to take the revision quiz.
     *
     * @return The {@link Statistic#revisions} field.
     */
    public int getRevisions() {
        return revisions;
    }

    /**
     * Set the number of time to take the revision quiz.
     *
     * @param revisions The number of time to take the revision quiz to be set.
     * @throws IllegalArgumentException If the {@link Statistic#verdict} is not {@link Statistic#TBD}
     * @throws IllegalArgumentException If the revision count is less than 0 or greater than 2.
     */
    public void setRevisions(int revisions) {
        if (!verdict.equals(TBD)) {
            throw new IllegalArgumentException("The student who gets the verdict cannot take another quiz!");
        }
        if (revisions != 1 && revisions != 2) {
            throw new IllegalArgumentException("The revision count cannot be 1 or 2!");
        }
        this.revisions = revisions;
    }

    /**
     * Get the answered question map.
     *
     * @return The {@link Statistic#answeredQuestionMap} field.
     */
    public Map<String, Set<Question>> getAnsweredQuestionMap() {
        return answeredQuestionMap;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "verdict: " + verdict + "\n" +
                "attempts: " + attempts + ", " + attemptScores + "\n" +
                "revisions: " + revisions + ", " + revisionScores;
    }
}
