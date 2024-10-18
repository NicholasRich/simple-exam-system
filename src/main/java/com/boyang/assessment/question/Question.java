package com.boyang.assessment.question;

/**
 * Every specific question class must realize its own business logic to check the correctness of the answer.
 * And each question type must contain a method to get its formulation.
 *
 * @author Boyang Wang
 */
public interface Question {

    /**
     * Check if the answer is right or not for this question.
     *
     * @param answer The answer to be checked.
     * @return If the answer is correct, return true, otherwise return false.
     */
    boolean checkAnswer(String answer);

    /**
     * Get the formulation of the question.
     *
     * @return The formulation field.
     */
    String getFormulation();
}
