package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class QuestionFactory implements Question {
    public static final String FREE_RESPONSE = "FREE_RESPONSE";
    public static final String MULTIPLE_CHOICES = "MULTIPLE_CHOICES";
    public static final List<Question> FREE_RESPONSE_QUESTIONS = new ArrayList<>();
    public static final List<Question> MULTIPLE_CHOICES_QUESTIONS = new ArrayList<>();
    private final String formulation;

    protected QuestionFactory(String formulation) {
        if (StringUtil.isEmpty(formulation)) {
            throw new IllegalArgumentException("The formulation cannot be null or empty!");
        }
        this.formulation = formulation;
    }

    @Override
    public String getFormulation() {
        return formulation;
    }

    public static Question getInstance(String formulation, String answer) {
        FreeResponseQuestion freeResponseQuestion = new FreeResponseQuestion(formulation, answer);
        FREE_RESPONSE_QUESTIONS.add(freeResponseQuestion);
        return freeResponseQuestion;
    }

    public static Question getInstance(String formulation, List<String> answer) {
        MultipleChoicesQuestion multipleChoicesQuestion = new MultipleChoicesQuestion(formulation, answer);
        MULTIPLE_CHOICES_QUESTIONS.add(multipleChoicesQuestion);
        return multipleChoicesQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionFactory questionFactory = (QuestionFactory) o;
        return formulation.equals(questionFactory.formulation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulation);
    }
}
