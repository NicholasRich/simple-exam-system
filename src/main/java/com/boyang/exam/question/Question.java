package com.boyang.exam.question;

public interface Question {
    boolean checkAnswer(String answer);

    String getFormulation();
}
