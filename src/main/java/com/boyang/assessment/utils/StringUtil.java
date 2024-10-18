package com.boyang.assessment.utils;

/**
 * The utility class to handle some frequent operations related to {@link String}.
 *
 * @author Boyang Wang
 */
public class StringUtil {
    /**
     * Check if the string is null or empty.
     *
     * @param content The string to be checked.
     * @return If the content is null or empty, return true. Otherwise, return false.
     */
    public static boolean isEmpty(String content) {
        return content == null || content.isBlank();
    }
}
