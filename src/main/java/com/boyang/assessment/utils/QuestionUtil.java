package com.boyang.assessment.utils;

import com.boyang.assessment.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionUtil {
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
            Question e = pool.get(random.nextInt(pool.size()));
            result.add(e);
            pool = pool.stream().filter(item -> !item.equals(e)).toList();
        }
        return result;
    }
}
