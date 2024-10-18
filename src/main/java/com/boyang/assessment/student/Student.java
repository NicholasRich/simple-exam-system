package com.boyang.assessment.student;

import com.boyang.assessment.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * This class contains some basic information of the student and the student's quiz statistics.
 *
 * @author Boyang Wang
 */
public class Student {
    /**
     * The student's first name.
     */
    private final String firstName;

    /**
     * The student's last name.
     */
    private final String lastName;

    /**
     * The student's birthday.
     */
    private final Date birthday;

    /**
     * The student's quiz statistics.
     */
    private final Statistic statistic = new Statistic();

    /**
     * The constructor of {@link Student}
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student.
     * @param birthday  The birthday of the student.
     * @throws IllegalArgumentException If the first name is null or empty.
     * @throws IllegalArgumentException If the last name is null or empty.
     * @throws NullPointerException     If the birthday is null.
     */
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

    /**
     * Get the first name of the student.
     *
     * @return The {@link Student#firstName} field.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of the student.
     *
     * @return The {@link Student#lastName} field.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the birthday of the student.
     *
     * @return The {@link Student#birthday} field.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Get the quiz statistics of the student.
     *
     * @return The {@link Student#statistic} field.
     */
    public Statistic getStatistic() {
        return statistic;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return firstName + " " + lastName + ", " + format.format(birthday);
    }

    /**
     * Override the {@link Object#equals(Object)} method.
     * If 2 students have the same name and birthday, they are the same student.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                birthday.compareTo(student.birthday) == 0;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthday);
    }
}
