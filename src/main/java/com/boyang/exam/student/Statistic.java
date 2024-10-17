package com.boyang.exam.student;

import com.boyang.exam.question.Question;
import com.boyang.exam.utils.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Statistic {
    public static String PASS = "PASS";
    public static String FAIL = "FAIL";
    public static String TBD = "TBD";
    private String verdict = TBD;
    private int attempts;
    private int revisions;
    private final List<Double> attemptScores = Arrays.asList(null, null);
    private final List<Double> revisionScores = Arrays.asList(null, null);
    private final Set<Question> answeredFreeResponseQuestions = new HashSet<>();
    private final Set<Question> answeredMultipleChoicesQuestions = new HashSet<>();

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        if (StringUtil.isEmpty(verdict)) {
            throw new IllegalArgumentException("The verdict cannot be empty!");
        }
        if (!Arrays.asList(PASS, FAIL, TBD).contains(verdict)) {
            throw new IllegalArgumentException("Unknown verdict: " + verdict + "!");
        }
        this.verdict = verdict;
    }

    public List<Double> getAttemptScores() {
        return attemptScores;
    }

    public Set<Question> getAnsweredFreeResponseQuestions() {
        return answeredFreeResponseQuestions;
    }

    public Set<Question> getAnsweredMultipleChoicesQuestions() {
        return answeredMultipleChoicesQuestions;
    }


    public List<Double> getRevisionScores() {
        return revisionScores;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        if (attempts < 0 || attempts > 2) {
            throw new IllegalArgumentException("Attempt count must be between 0 and 2!");
        }
        this.attempts = attempts;
    }

    public int getRevisions() {
        return revisions;
    }

    public void setRevisions(int revisions) {
        if (revisions < 0 || revisions > 2) {
            throw new IllegalArgumentException("Revision count must be between 0 and 2!");
        }
        this.revisions = revisions;
    }

    @Override
    public String toString() {
        return "verdict: " + verdict + "\n" +
                "attempts: " + attempts + ", " + attemptScores.toString() + "\n" +
                "revisions: " + revisions + ", " + revisionScores.toString();
    }
}
