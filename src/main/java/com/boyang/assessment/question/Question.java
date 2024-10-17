package com.boyang.assessment.question;

public interface Question {

    /**
     * Check if the answer is right or not for this question
     *
     * @param answer The answer to be checked
     * @return If the answer is correct, return true, otherwise return false
     */
    boolean checkAnswer(String answer);

    /**
     * Get the formulation of the question
     * @return The formulation field
     */
    String getFormulation();
}
