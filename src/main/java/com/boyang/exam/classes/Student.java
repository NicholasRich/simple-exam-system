package com.boyang.exam.classes;

import com.boyang.exam.interfaces.Question;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private Date birthday;
    private String verdict = "TBD";
    private int attempts;
    private int revisions;
    private List<Double> attemptScores = new ArrayList<>();
    private List<Double> revisionScores = new ArrayList<>();
    private List<Question> answeredQuestions = new ArrayList<>();

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getBirthday() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .format(birthday.toInstant());
    }


}
