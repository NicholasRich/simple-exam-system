package com.boyang.exam.utils;

import com.boyang.exam.interfaces.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionUtil {
    public static boolean isValidContent(String content) {
        return content != null && !content.isBlank();
    }

    public static boolean isValidAnswer(String answer) {
        if (!isValidContent(answer)) {
            throw new IllegalArgumentException("The answer cannot be empty!");
        }
        int size = Arrays.stream(answer.split(",")).toList().size();
        return size > 1 && size < 5;
    }

    public static List<String> sortedAnswer(String answer) {
        if (!isValidAnswer(answer)) {
            throw new IllegalArgumentException("The number of answers must between 2 and 4!");
        }
        return Arrays.stream(answer.split(","))
                .map(item -> item.strip().toUpperCase()).sorted().toList();
    }

    public static String formattedAnswer(String answer) {
        if (!isValidContent(answer)) {
            throw new IllegalArgumentException("The answer cannot be empty!");
        }
        List<String> result = Arrays.stream(answer.strip().split(" +"))
                .map(String::toLowerCase).toList();
        return String.join(" ", result);
    }

    public static List<Question> randomQuestions(int count, List<Question> list) {
        if (count < 1) {
            throw new IllegalArgumentException("The number of questions cannot be less than 1!");
        }
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Question list cannot be empty!");
        }
        List<Question> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int size = list.size();
            Question e = list.get(random.nextInt(size));
            result.add(e);
            list = list.stream().filter(item -> !item.equals(e))
                    .toList();
        }
        return result;
    }

}
