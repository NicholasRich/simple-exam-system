package com.boyang.exam.classes;

import com.boyang.exam.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;

public final class MultipleChoicesQuestion extends QuestionFactory {
    private final List<String> answers;

    public MultipleChoicesQuestion(String formulation, List<String> answers) {
        super(formulation);
        this.answers = answers;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return this.answers.equals(QuestionUtil.sortedAnswer(answer));
    }

}
