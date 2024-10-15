package com.boyang.exam.classes;

import com.boyang.exam.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;

public final class MultipleChoicesQuestion extends QuestionFactory {
    private final List<String> answers;

    public MultipleChoicesQuestion(String formulation, String answer) {
        super(formulation);
        this.answers = new ArrayList<>(QuestionUtil.sortedAnswer(answer));
    }

    @Override
    public boolean checkAnswer(String answer) {
        return this.answers.equals(QuestionUtil.sortedAnswer(answer));
    }

}
