package com.boyang.exam.classes;

import com.boyang.exam.interfaces.Exam;
import com.boyang.exam.interfaces.Question;

import java.util.List;

public class StudentExam implements Exam {
    @Override
    public List<Question> generateQuiz(int number) {
        return List.of();
    }

    @Override
    public List<Question> revise(int number) {
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
