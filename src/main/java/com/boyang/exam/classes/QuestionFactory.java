package com.boyang.exam.classes;

import com.boyang.exam.interfaces.Question;
import com.boyang.exam.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class QuestionFactory implements Question {
    public static final String FREE_RESPONSE = "free response";
    public static final String MULTIPLE_CHOICES = "multiple choices";
    public static final Map<String, List<Question>> QUESTION_MAP = Map.of(
            FREE_RESPONSE, new ArrayList<>(),
            MULTIPLE_CHOICES, new ArrayList<>());
    private final String formulation;

    public QuestionFactory(String formulation) {
        this.formulation = formulation;
    }

    @Override
    public String getFormulation() {
        return formulation;
    }

    public static Question getInstance(String type, String formulation, String answer) {
        if (!QuestionUtil.isValidContent(formulation)) {
            throw new IllegalArgumentException("Invalid formulation!");
        }
        Question question;
        switch (type) {
            case FREE_RESPONSE -> {
                question = new FreeResponseQuestion(formulation, QuestionUtil.formattedAnswer(answer));
                QUESTION_MAP.get(FREE_RESPONSE).add(question);
            }
            case MULTIPLE_CHOICES -> {
                question = new MultipleChoicesQuestion(formulation, answer);
                QUESTION_MAP.get(MULTIPLE_CHOICES).add(question);
            }
            default -> throw new IllegalArgumentException("Unknown question type: " + type);
        }
        return question;
    }

}
