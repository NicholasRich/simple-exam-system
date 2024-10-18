package com.boyang.assessment.quiz;

import com.boyang.assessment.question.FreeResponseQuestion;
import com.boyang.assessment.question.MultipleChoicesQuestion;
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

    /**
     * The implementation of {@link Quiz#generateQuiz(int)}.
     * It randomly chooses the unduplicated questions in {@link QuestionFactory#QUESTION_MAP}.
     * The generated question list contains at least 1 question for each question type.
     *
     * @throws IllegalArgumentException If the number is less than 2 or greater than the number of questions in {@link QuestionFactory#QUESTION_MAP}.
     * @throws IllegalArgumentException Check {@link QuestionUtil#getRandomQuestions(int, List)}.
     * @see Quiz#generateQuiz(int)
     */
    @Override
    public List<Question> generateQuiz(int number) {
        List<Question> freeResponseQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE);
        List<Question> multipleChoicesQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES);
        if (number < 2 || number > freeResponseQuestions.size() + multipleChoicesQuestions.size()) {
            throw new IllegalArgumentException("The number of questions must between 2 and the total number of questions!");
        }
        List<Question> result = new ArrayList<>();
        Map<String, Integer> questionCountMap = randomQuestionCount(number, freeResponseQuestions.size(), multipleChoicesQuestions.size());
        result.addAll(QuestionUtil.getRandomQuestions(questionCountMap.get(QuestionFactory.FREE_RESPONSE), freeResponseQuestions));
        result.addAll(QuestionUtil.getRandomQuestions(questionCountMap.get(QuestionFactory.MULTIPLE_CHOICES), multipleChoicesQuestions));
        return result;
    }

    /**
     * The implementation of {@link Quiz#revise(Student, int)}.
     * It randomly chooses the unduplicated questions in {@link QuestionFactory#QUESTION_MAP}.
     * The generated question list contains at least 1 question for each question type.
     * And It will not contain the questions that the student did correctly before,
     * unless there are not enough new questions in {@link QuestionFactory#QUESTION_MAP}.
     *
     * @throws NullPointerException     If the student is null.
     * @throws IllegalArgumentException If the number is less than 2 or greater than the number of questions in {@link QuestionFactory#QUESTION_MAP}.
     * @throws IllegalArgumentException Check {@link QuestionUtil#getRandomQuestions(int, List)}.
     * @see Quiz#generateQuiz(int)
     */
    @Override
    public List<Question> revise(Student student, int number) {
        if (student == null) {
            throw new NullPointerException("The student cannot be null!");
        }
        List<Question> freeResponseQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE);
        List<Question> multipleChoicesQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES);
        if (number < 2 || number > freeResponseQuestions.size() + multipleChoicesQuestions.size()) {
            throw new IllegalArgumentException("The number of questions must between 2 and the total number of questions!");
        }
        Map<String, Integer> questionCountMap = randomQuestionCount(number, freeResponseQuestions.size(), multipleChoicesQuestions.size());
        int freeResponseQuestionCount = questionCountMap.get(QuestionFactory.FREE_RESPONSE);
        int multipleChoicesQuestionCount = questionCountMap.get(QuestionFactory.MULTIPLE_CHOICES);
        List<Question> answeredFreeResponseQuestions = student.getStatistic().getAnsweredFreeResponseQuestions().stream().toList();
        List<Question> answeredMultipleChoicesQuestions = student.getStatistic().getAnsweredMultipleChoicesQuestions().stream().toList();
        List<Question> result = new ArrayList<>();
        result.addAll(getUnansweredQuestions(freeResponseQuestionCount, answeredFreeResponseQuestions, freeResponseQuestions));
        result.addAll(getUnansweredQuestions(multipleChoicesQuestionCount, answeredMultipleChoicesQuestions, multipleChoicesQuestions));
        return result;
    }

    /**
     * The implementation of {@link Quiz#takeQuiz(Student, List, List)}.
     * Only if the student's {@link Statistic#getVerdict()} is {@link Statistic#TBD}, he can take the regular quiz.
     * The score is calculated by the correctly answered question count / the question list size.
     * If the score is less than 0.5, the student will fail the exam. Otherwise, he can get a pass.
     * After taking the quiz, the statistic of the quiz will be logged.
     *
     * @throws NullPointerException     Check {@link StudentQuiz#validQuiz(Student, List, List)}.
     * @throws IllegalArgumentException Check {@link StudentQuiz#validQuiz(Student, List, List)}.
     * @see Quiz#takeQuiz(Student, List, List)
     */
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

    /**
     * The implementation of {@link Quiz#takeRevisionQuiz(Student, List, List)}.
     * Only if the student's {@link Statistic#getVerdict()} is {@link Statistic#TBD}
     * and the number of times to take the revision quiz is less than 2, he can take the revision quiz.
     * The score is calculated by the correctly answered question count / the question list size.
     * After taking the quiz, the statistic of the quiz will be logged.
     *
     * @throws IllegalArgumentException If the student's {@link Statistic#getRevisions()} is greater than 1.
     * @throws NullPointerException     Check {@link StudentQuiz#validQuiz(Student, List, List)}.
     * @throws IllegalArgumentException Check {@link StudentQuiz#validQuiz(Student, List, List)}.
     * @see Quiz#takeRevisionQuiz(Student, List, List)
     */
    @Override
    public Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers) {
        validQuiz(student, questions, answers);
        if (student.getStatistic().getRevisions() > 1) {
            throw new IllegalArgumentException("The student can only take at most 2 revision quizzes!");
        }
        int correct = logAnsweredQuestions(student, questions, answers);
        double result = correct / (double) questions.size();
        int revisions = student.getStatistic().getRevisions();
        student.getStatistic().getRevisionScores().set(revisions++, result);
        student.getStatistic().setRevisions(revisions);
        return result;
    }

    /**
     * The implementation of {@link Quiz#generateStatistics(Student)}.
     * Invoke {@link Student#toString()} and {@link Statistic#toString()} to generate the result.
     *
     * @throws NullPointerException If the student is null.
     * @see Quiz#generateStatistics(Student)
     */
    @Override
    public String generateStatistics(Student student) {
        if (student == null) {
            throw new NullPointerException("The student cannot be null!");
        }
        return student + "\n" + student.getStatistic();
    }

    /**
     * Get the random number of question for {@link FreeResponseQuestion} and {@link MultipleChoicesQuestion}.
     * Each random number cannot be greater than the size of the corresponding question pool.
     *
     * @param number                      The total number of question you want to get from the question pool.
     * @param freeResponseQuestionSize    The size of the {@link FreeResponseQuestion} pool.
     * @param multipleChoicesQuestionSize The size of the {@link MultipleChoicesQuestion} pool.
     * @return A map whose key is the name of the question type and the value is the corresponding random
     * number.
     */
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

    /**
     * Get questions that are different from the answered questions in the question pool.
     * If there are not enough unanswered questions in the question pool.
     * The result will contain some answered questions to ensure total number of questions meets the requirement.
     *
     * @param number            The unanswered number of question you want to get.
     * @param answeredQuestions The answered question list.
     * @param questions         The question pool.
     * @return The list of the unanswered question.
     * @throws IllegalArgumentException Check {@link QuestionUtil#getRandomQuestions(int, List)}.
     */
    private List<Question> getUnansweredQuestions(int number, List<Question> answeredQuestions, List<Question> questions) {
        List<Question> unansweredQuestions = questions.stream()
                .filter(question -> !answeredQuestions.contains(question)).toList();
        List<Question> result = new ArrayList<>();
        if (number <= unansweredQuestions.size()) {
            result.addAll(QuestionUtil.getRandomQuestions(number, unansweredQuestions));
        } else {
            result.addAll(unansweredQuestions);
            result.addAll(QuestionUtil.getRandomQuestions(number - unansweredQuestions.size(), answeredQuestions));
        }
        return result;
    }

    /**
     * Check the specified student's answer and log the correctly answered questions.
     *
     * @param student   The student whose answer will be checked and logged.
     * @param questions The questions that the student will answer.
     * @param answers   The student's answer to be checked.
     * @return The count of the correctly answered questions.
     */
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

    /**
     * To check if the quiz is valid.
     * If the quiz is not valid, an exception will be thrown.
     *
     * @param student   The student who takes the quiz.
     * @param questions The questions to be answered.
     * @param answers   The answer of the student.
     * @throws NullPointerException     If the student is null.
     * @throws IllegalArgumentException If the student's {@link Statistic#getVerdict()} is not {@link Statistic#TBD}.
     * @throws IllegalArgumentException If the question list is null or empty.
     * @throws IllegalArgumentException If the answer list is null or empty.
     * @throws IllegalArgumentException If the question list size is less than 2.
     * @throws IllegalArgumentException If the question list size does not equal the answer list size.
     */
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
