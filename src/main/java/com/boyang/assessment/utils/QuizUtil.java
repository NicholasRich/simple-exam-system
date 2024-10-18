package com.boyang.assessment.utils;

import com.boyang.assessment.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The tool class to handle some frequent operations related to quiz.
 *
 * @author Boyang Wang
 */
public class QuizUtil {
    /**
     * Randomly get the unduplicated questions from the question pool.
     *
     * @param number The number of the questions you want to get.
     * @param pool   The question pool which got questions from.
     * @return A {@link Question} list.
     * @throws IllegalArgumentException If the number is less than 1.
     * @throws IllegalArgumentException If the question pool is null or empty.
     * @throws IllegalArgumentException If the number is greater than the question pool size.
     */
    public static List<Question> getRandomQuestions(int number, List<Question> pool) {
        if (number < 1) {
            throw new IllegalArgumentException("The number of questions cannot be less than 1!");
        }
        if (pool == null || pool.isEmpty()) {
            throw new IllegalArgumentException("Question pool cannot be null or empty!");
        }
        if (number > pool.size()) {
            throw new IllegalArgumentException("The number of questions cannot be greater than the size of question pool!");
        }
        List<Question> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            Question question = pool.get(random.nextInt(pool.size()));
            result.add(question);
            pool = pool.stream().filter(item -> !item.equals(question)).toList();
        }
        return result;
    }
}
