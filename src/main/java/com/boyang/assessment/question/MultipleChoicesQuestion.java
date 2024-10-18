package com.boyang.assessment.question;

import com.boyang.assessment.utils.StringUtil;

import java.util.Arrays;
import java.util.List;

public final class MultipleChoicesQuestion extends QuestionFactory {
    private final List<String> answer;

    /**
     * The constructor of {@link MultipleChoicesQuestion}.
     * It's used by {@link QuestionFactory} to create {@link MultipleChoicesQuestion} instance.
     * Recommend to use {@link QuestionFactory#getInstance(String, List)} to create the instance.
     *
     * @param formulation The question formulation.
     * @param answer      The correct answer of the question.
     * @throws IllegalArgumentException Check {@link QuestionFactory#QuestionFactory(String)}.
     * @throws IllegalArgumentException If the answer is null or empty
     * @throws IllegalArgumentException If the size of the answer list is less than 2 or greater than 4.
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
     * The implementation of {@link Question#checkAnswer(String)}
     * Firstly, use {@link MultipleChoicesQuestion#sortedAnswer(List)} and {@link MultipleChoicesQuestion#sortedAnswer(String)}
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
        return sortedAnswer(this.answer).equals(sortedAnswer(answer));
    }

    /**
     * Format the answer into a specific format.
     * Split the answer string by ",". Then remove blank answers, change all the letters to uppercase and sort the
     * answer in alphabetical order.
     *
     * @param answer The answer string to be handled.
     * @return The sorted answer list.
     */
    private List<String> sortedAnswer(String answer) {
        return Arrays.stream(answer.split(","))
                .filter(item -> !item.isBlank())
                .map(item -> item.strip().toUpperCase()).sorted().toList();
    }

    /**
     * Format the answer into a specific format.
     * Remove blank answers, change all the letters to uppercase and sort the answer in alphabetical order.
     *
     * @param answer The answer list to be handled.
     * @return The sorted answer list.
     */
    private List<String> sortedAnswer(List<String> answer) {
        return answer.stream().filter(item -> !item.isBlank())
                .map(item -> item.strip().toUpperCase()).sorted().toList();
    }


}
