package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.List;

public final class MultipleChoicesQuestion extends QuestionFactory {
    private final List<String> answer;

    MultipleChoicesQuestion(String formulation, List<String> answer) {
        super(formulation);
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("The answer list cannot be null or empty!");
        }
        if (answer.size() < 2 || answer.size() > 4) {
            throw new IllegalArgumentException("The number of answers must between 2 and 4!");
        }
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (StringUtil.isEmpty(answer)) {
            throw new IllegalArgumentException("The answer cannot be null or empty!");
        }
        return sortedAnswer(this.answer).equals(sortedAnswer(answer));
    }

    private List<String> sortedAnswer(String answer) {
        return Arrays.stream(answer.split(","))
                .filter(item -> !item.isBlank())
                .map(item -> item.strip().toUpperCase()).sorted().toList();
    }

    private List<String> sortedAnswer(List<String> answer) {
        return answer.stream().filter(item -> !item.isBlank())
                .map(item -> item.strip().toUpperCase()).sorted().toList();
    }


}
