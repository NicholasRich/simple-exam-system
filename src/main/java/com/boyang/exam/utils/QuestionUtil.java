package com.boyang.exam.utils;

import java.util.Arrays;
import java.util.List;

public class QuestionUtil {
    public static boolean isValidContent(String content) {
        return content != null && !content.isBlank();
    }

    public static boolean isValidAnswer(String question) {
        int size = Arrays.stream(question.split(",")).
                filter(item -> !item.isBlank()).toList().size();
        return size > 1 && size < 5;
    }

    public static List<String> sortedAnswer(String answer) {
        if (!isValidContent(answer)) {
            throw new IllegalArgumentException("The answer cannot be empty!");
        }
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

}
