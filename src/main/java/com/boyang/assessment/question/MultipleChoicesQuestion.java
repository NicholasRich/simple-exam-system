package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.List;

public final class MultipleChoicesQuestion extends QuestionFactory {
    private final List<String> answer;

    /**
     * The constructor of MultipleChoicesQuestion
     * It's used by {@link QuestionFactory} to create MultipleChoicesQuestion instance
     * Recommend to use {@link QuestionFactory#getInstance(String, List)} to create instance
     *
     * @param formulation The question formulation
     * @param answer      The correct answer of the question
     */
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

    /**
     * Check if the answer is right or not for this question
     *
     * @param answer The answer to be checked
     * @return If the answer is correct, return true, otherwise return false
     * @throws IllegalArgumentException If the answer is null or empty
     */
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
