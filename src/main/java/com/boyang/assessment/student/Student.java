package com.boyang.assessment.student;

import com.boyang.assessment.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Student {
    private final String firstName;
    private final String lastName;
    private final Date birthday;
    private final Statistic statistic = new Statistic();

    public Student(String firstName, String lastName, Date birthday) {
        if (StringUtil.isEmpty(firstName)) {
            throw new IllegalArgumentException("The first name cannot be null or empty!");
        }
        if (StringUtil.isEmpty(lastName)) {
            throw new IllegalArgumentException("The last name cannot be null or empty!");
        }
        if (birthday == null) {
            throw new NullPointerException("The birthday cannot be null!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return firstName + " " + lastName + ", " + format.format(birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                birthday.compareTo(student.birthday) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthday);
    }
}
