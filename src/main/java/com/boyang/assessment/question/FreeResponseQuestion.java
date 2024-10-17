package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class FreeResponseQuestion extends QuestionFactory {
    private final String answer;

    FreeResponseQuestion(String formulation, String answer) {
        super(formulation);
        if (StringUtil.isEmpty(answer)) {
            throw new IllegalArgumentException("The answer cannot be null or empty!");
        }
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (StringUtil.isEmpty(answer)) {
            throw new IllegalArgumentException("The answer cannot be null or empty!");
        }
        return formattedAnswer(this.answer).equals(formattedAnswer(answer));
    }

    private String formattedAnswer(String answer) {
        return Arrays.stream(answer.strip().split(" +"))
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
