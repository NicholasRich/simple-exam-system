package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class FreeResponseQuestion extends QuestionFactory {
    private final String answer;

    /**
     * The constructor of {@link FreeResponseQuestion}.
     * It's used by {@link QuestionFactory} to create {@link FreeResponseQuestion} instance.
     * Recommend to use {@link QuestionFactory#getInstance(String, String)} to create the instance.
     *
     * @param formulation The question formulation.
     * @param answer      The correct answer of the question.
     * @throws IllegalArgumentException Check {@link QuestionFactory#QuestionFactory(String)}.
     * @throws IllegalArgumentException If the answer is null or empty.
     */
    FreeResponseQuestion(String formulation, String answer) {
        super(formulation);
        if (StringUtil.isEmpty(answer)) {
            throw new IllegalArgumentException("The answer cannot be null or empty!");
        }
        this.answer = answer;
    }

    /**
     * The implementation of {@link Question#checkAnswer(String)}
     * Firstly, use {@link FreeResponseQuestion#formattedAnswer(String)}
     * to make the correct answer and the answer param have the same format. Then compare them.
     *
     * @throws IllegalArgumentException If the answer is null or empty.
     * @see Question#checkAnswer(String)
     */
    @Override
    public boolean checkAnswer(String answer) {
        if (StringUtil.isEmpty(answer)) {
            throw new IllegalArgumentException("The answer cannot be null or empty!");
        }
        return formattedAnswer(this.answer).equals(formattedAnswer(answer));
    }

    /**
     * Format the answer into a specific format.
     * Remove the redundant spaces and change all the letters to lowercase.
     *
     * @param answer The answer to be formatted.
     * @return The formatted answer.
     */
    private String formattedAnswer(String answer) {
        return Arrays.stream(answer.strip().split(" +"))
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
