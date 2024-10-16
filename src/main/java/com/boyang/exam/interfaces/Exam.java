package com.boyang.exam.interfaces;

import com.boyang.exam.classes.Student;

import java.util.List;

public interface Exam {
    List<Question> generateQuiz(int number);

    List<Question> revise(Student student, int number);

    Double takeQuiz(Student student, List<Question> questions, List<String> answers);

    Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers);

    Student generateStatistics(Student student);
}
