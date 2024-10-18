package com.boyang.assessment.quiz;

import com.boyang.assessment.question.Question;
import com.boyang.assessment.student.Student;

import java.util.List;

public interface Quiz {
    /**
     * Generate a question list for regular quiz.
     *
     * @param number The number of the generated questions.
     * @return A {@link Question} list.
     */
    List<Question> generateQuiz(int number);

    /**
     * Generate a question list for revision quiz.
     *
     * @param student The {@link Student} instance who takes the revision quiz.
     * @param number  The number of the generated questions.
     * @return A {@link Question} list.
     */
    List<Question> revise(Student student, int number);

    /**
     * Make the specified student take a regular quiz.
     *
     * @param student   The student who will take the regular quiz.
     * @param questions The question list of the regular quiz.
     * @param answers   The student's answer list of the regular quiz.
     * @return The score obtained by the student.
     */
    Double takeQuiz(Student student, List<Question> questions, List<String> answers);

    /**
     * Make the specified student take a revision quiz.
     *
     * @param student   The student who will take the revision quiz.
     * @param questions The question list of the revision quiz.
     * @param answers   The student's answer list of the revision quiz.
     * @return The score obtained by the student.
     */
    Double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers);

    /**
     * Generate the specified student's statistics of the quiz result.
     *
     * @param student The student whose quiz statistics will be generated.
     * @return The quiz statistics string.
     */
    String generateStatistics(Student student);
}
