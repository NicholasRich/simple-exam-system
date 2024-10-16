package com.boyang.exam.classes;

import com.boyang.exam.interfaces.Exam;
import com.boyang.exam.interfaces.Question;
import com.boyang.exam.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class StudentExam implements Exam {
    @Override
    public List<Question> generateQuiz(int number) {
        List<Question> freeResponseQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.FREE_RESPONSE);
        List<Question> multipleChoicesQuestions = QuestionFactory.QUESTION_MAP.get(QuestionFactory.MULTIPLE_CHOICES);
        if (number < 2) {
            throw new IllegalArgumentException("The number of question must be greater than 2!");
        }
        if (number > freeResponseQuestions.size() + multipleChoicesQuestions.size()) {
            throw new IllegalArgumentException("The number of question cannot be greater than the total number of questions!");
        }
        List<Question> result = new ArrayList<>();
        Random random = new Random();
        int freeResponseQuestionCount = 1;
        int multipleChoicesQuestionCount = 1;
        for (int i = 0; i < number - 2; i++) {
            if (freeResponseQuestionCount == freeResponseQuestions.size()) {
                multipleChoicesQuestionCount = number - freeResponseQuestionCount;
                break;
            }
            if (multipleChoicesQuestionCount == multipleChoicesQuestions.size()) {
                freeResponseQuestionCount = number - multipleChoicesQuestionCount;
                break;
            }
            switch (random.nextInt(2)) {
                case 0 -> freeResponseQuestionCount++;
                case 1 -> multipleChoicesQuestionCount++;
            }
        }
        result.addAll(QuestionUtil.randomQuestions(freeResponseQuestionCount, freeResponseQuestions));
        result.addAll(QuestionUtil.randomQuestions(multipleChoicesQuestionCount, multipleChoicesQuestions));
        return result;
    }

    @Override
    public List<Question> revise(Student student, int number) {
        return List.of();
    }

    @Override
    public Double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        return 0.0;
    }

    @Override
    public Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers) {
        return 0.0;
    }

    @Override
    public Student generateStatistics(Student student) {
        return null;
    }
}
