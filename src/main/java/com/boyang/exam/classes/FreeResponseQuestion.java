package com.boyang.exam.classes;

import com.boyang.exam.utils.QuestionUtil;

public final class FreeResponseQuestion extends QuestionFactory {
    private final String answer;

    public FreeResponseQuestion(String formulation, String answer) {
        super(formulation);
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return this.answer.equals(QuestionUtil.formattedAnswer(answer));
    }

}
