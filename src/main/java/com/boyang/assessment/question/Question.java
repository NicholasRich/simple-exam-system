package com.boyang.assessment.question;

public interface Question {
    boolean checkAnswer(String answer);

    String getFormulation();
}
