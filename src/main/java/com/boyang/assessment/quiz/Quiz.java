package com.boyang.assessment.quiz;

import com.boyang.assessment.student.Student;
import com.boyang.assessment.question.Question;

import java.util.List;

public interface Quiz {
    List<Question> generateQuiz(int number);

    List<Question> revise(Student student, int number);

    Double takeQuiz(Student student, List<Question> questions, List<String> answers);

    Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers);

    String generateStatistics(Student student);
}
