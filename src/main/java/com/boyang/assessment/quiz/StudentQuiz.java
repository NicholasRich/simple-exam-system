package com.boyang.assessment.quiz;

import com.boyang.assessment.question.FreeResponseQuestion;
import com.boyang.assessment.question.Question;
import com.boyang.assessment.question.QuestionFactory;
import com.boyang.assessment.student.Statistic;
import com.boyang.assessment.student.Student;
import com.boyang.assessment.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class StudentQuiz implements Quiz {
    private static final StudentQuiz INSTANCE = new StudentQuiz();

    private StudentQuiz() {
    }

    public static StudentQuiz getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Question> generateQuiz(int number) {
        if (number < 2 || number > QuestionFactory.FREE_RESPONSE_QUESTIONS.size() + QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.size()) {
            throw new IllegalArgumentException("The number of questions must between 2 and the total number of questions!");
        }
        List<Question> result = new ArrayList<>();
        Map<String, Integer> questionCountMap = randomQuestionCount(number, QuestionFactory.FREE_RESPONSE_QUESTIONS.size(), QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.size());
        result.addAll(QuestionUtil.getRandomQuestions(questionCountMap.get(QuestionFactory.FREE_RESPONSE), QuestionFactory.FREE_RESPONSE_QUESTIONS));
        result.addAll(QuestionUtil.getRandomQuestions(questionCountMap.get(QuestionFactory.MULTIPLE_CHOICES), QuestionFactory.MULTIPLE_CHOICES_QUESTIONS));
        return result;
    }

    @Override
    public List<Question> revise(Student student, int number) {
        if (student == null) {
            throw new NullPointerException("The student cannot be null!");
        }
        if (number < 2 || number > QuestionFactory.FREE_RESPONSE_QUESTIONS.size() + QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.size()) {
            throw new IllegalArgumentException("The number of questions must between 2 and the total number of questions!");
        }
        Map<String, Integer> questionCountMap = randomQuestionCount(number, QuestionFactory.FREE_RESPONSE_QUESTIONS.size(), QuestionFactory.MULTIPLE_CHOICES_QUESTIONS.size());
        int freeResponseQuestionCount = questionCountMap.get(QuestionFactory.FREE_RESPONSE);
        int multipleChoicesQuestionCount = questionCountMap.get(QuestionFactory.MULTIPLE_CHOICES);
        List<Question> answeredFreeResponseQuestions = student.getStatistic().getAnsweredFreeResponseQuestions().stream().toList();
        List<Question> answeredMultipleChoicesQuestions = student.getStatistic().getAnsweredMultipleChoicesQuestions().stream().toList();
        List<Question> result = new ArrayList<>();
        result.addAll(getUnansweredQuestions(freeResponseQuestionCount, answeredFreeResponseQuestions, QuestionFactory.FREE_RESPONSE_QUESTIONS));
        result.addAll(getUnansweredQuestions(multipleChoicesQuestionCount, answeredMultipleChoicesQuestions, QuestionFactory.MULTIPLE_CHOICES_QUESTIONS));
        return result;
    }

    @Override
    public Double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        validQuiz(student, questions, answers);
        int correct = logAnsweredQuestions(student, questions, answers);
        double result = correct / (double) questions.size();
        if (result >= 0.5) {
            student.getStatistic().setVerdict(Statistic.PASS);
        } else if (result < 0.5 && student.getStatistic().getAttempts() == 1) {
            student.getStatistic().setVerdict(Statistic.FAIL);
        }
        int attempts = student.getStatistic().getAttempts();
        student.getStatistic().getAttemptScores().set(attempts++, result);
        student.getStatistic().setAttempts(attempts);
        return result;
    }

    @Override
    public Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers) {
        validQuiz(student, questions, answers);
        if (student.getStatistic().getRevisions() == 2) {
            throw new IllegalArgumentException("The student has taken 2 revision quizzes!");
        }
        int correct = logAnsweredQuestions(student, questions, answers);
        double result = correct / (double) questions.size();
        int revisions = student.getStatistic().getRevisions();
        student.getStatistic().getRevisionScores().set(revisions++, result);
        student.getStatistic().setRevisions(revisions);
        return result;
    }

    @Override
    public String generateStatistics(Student student) {
        if (student == null) {
            throw new NullPointerException("The student cannot be null!");
        }
        return student + "\n" + student.getStatistic();
    }

    private Map<String, Integer> randomQuestionCount(int number, int freeResponseQuestionSize, int multipleChoicesQuestionSize) {
        Random random = new Random();
        int freeResponseQuestionCount = 1;
        int multipleChoicesQuestionCount = 1;
        for (int i = 0; i < number - 2; i++) {
            if (freeResponseQuestionCount == freeResponseQuestionSize) {
                multipleChoicesQuestionCount = number - freeResponseQuestionCount;
                break;
            }
            if (multipleChoicesQuestionCount == multipleChoicesQuestionSize) {
                freeResponseQuestionCount = number - multipleChoicesQuestionCount;
                break;
            }
            switch (random.nextInt(2)) {
                case 0 -> freeResponseQuestionCount++;
                case 1 -> multipleChoicesQuestionCount++;
            }
        }
        return Map.of(
                QuestionFactory.FREE_RESPONSE, freeResponseQuestionCount,
                QuestionFactory.MULTIPLE_CHOICES, multipleChoicesQuestionCount);
    }

    private List<Question> getUnansweredQuestions(int count, List<Question> answeredQuestions, List<Question> questions) {
        List<Question> unansweredQuestions = questions.stream()
                .filter(question -> !answeredQuestions.contains(question)).toList();
        List<Question> result = new ArrayList<>();
        if (count <= unansweredQuestions.size()) {
            result.addAll(QuestionUtil.getRandomQuestions(count, unansweredQuestions));
        } else {
            result.addAll(unansweredQuestions);
            result.addAll(QuestionUtil.getRandomQuestions(count - unansweredQuestions.size(), answeredQuestions));
        }
        return result;
    }

    private int logAnsweredQuestions(Student student, List<Question> questions, List<String> answers) {
        int correct = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.checkAnswer(answers.get(i))) {
                if (question instanceof FreeResponseQuestion) {
                    student.getStatistic().getAnsweredFreeResponseQuestions().add(question);
                } else {
                    student.getStatistic().getAnsweredMultipleChoicesQuestions().add(question);
                }
                correct++;
            }
        }
        return correct;
    }

    private void validQuiz(Student student, List<Question> questions, List<String> answers) {
        if (student == null) {
            throw new NullPointerException("The student cannot be null!");
        }
        if (!Statistic.TBD.equals(student.getStatistic().getVerdict())) {
            throw new IllegalArgumentException("The student who received the quiz result cannot take another quiz!");
        }
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("The question list cannot be null or empty!");
        }
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("The answer list cannot be null or empty!");
        }
        if (questions.size() < 2) {
            throw new IllegalArgumentException("The number of questions must be greater than 1!");
        }
        if (questions.size() != answers.size()) {
            throw new IllegalArgumentException("The number of questions does not match the number of answers!");
        }
    }
}
