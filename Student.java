/**
 * Student.java
 * Model class representing an individual student and their academic marks.
 * 
 * Demonstrates OOP concepts:
 * - Encapsulation: Private member variables with public getter/setter methods.
 */
public class Student {
    private String name;
    private double marks;

    /**
     * Constructor to initialize a student with name and marks.
     * @param name The student's name
     * @param marks The marks obtained by the student (0 - 100)
     */
    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    // Getter for student name
    public String getName() {
        return name;
    }

    // Setter for student name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for student marks
    public double getMarks() {
        return marks;
    }

    // Setter for student marks
    public void setMarks(double marks) {
        this.marks = marks;
    }

    /**
     * Calculates the letter grade based on marks obtained.
     * @return Letter grade (A+, A, B, C, D, or F)
     */
    public String getLetterGrade() {
        if (marks >= 90.0) {
            return "A+";
        } else if (marks >= 80.0) {
            return "A";
        } else if (marks >= 70.0) {
            return "B";
        } else if (marks >= 60.0) {
            return "C";
        } else if (marks >= 50.0) {
            return "D";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s | Marks: %6.2f | Grade: %-2s", name, marks, getLetterGrade());
    }
}
