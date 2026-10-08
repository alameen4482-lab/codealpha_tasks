import java.util.ArrayList;
import java.util.List;

/**
 * GradeTracker.java
 * Manages the collection of students and performs statistical operations.
 * 
 * Uses ArrayList to dynamically store and manage Student objects.
 */
public class GradeTracker {
    // Dynamic list to store student records
    private final List<Student> students;

    /**
     * Constructor initializes an empty list of students.
     */
    public GradeTracker() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student to the tracker.
     * @param student The student object to add
     */
    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    /**
     * Checks if any students have been recorded.
     * @return true if students list is not empty, false otherwise
     */
    public boolean hasStudents() {
        return !students.isEmpty();
    }

    /**
     * Returns total number of students.
     * @return Number of students recorded
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Returns the list of all students.
     * @return List of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Calculates the average marks of all students.
     * @return Average score, or 0.0 if no students exist
     */
    public double calculateAverage() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Student student : students) {
            total += student.getMarks();
        }
        return total / students.size();
    }

    /**
     * Finds the student with the highest marks.
     * @return Student object with highest score, or null if list is empty
     */
    public Student getHighestScorer() {
        if (students.isEmpty()) {
            return null;
        }
        Student highest = students.get(0);
        for (Student student : students) {
            if (student.getMarks() > highest.getMarks()) {
                highest = student;
            }
        }
        return highest;
    }

    /**
     * Finds the student with the lowest marks.
     * @return Student object with lowest score, or null if list is empty
     */
    public Student getLowestScorer() {
        if (students.isEmpty()) {
            return null;
        }
        Student lowest = students.get(0);
        for (Student student : students) {
            if (student.getMarks() < lowest.getMarks()) {
                lowest = student;
            }
        }
        return lowest;
    }

    /**
     * Displays all student records in a formatted table.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n[!] No student records found.");
            return;
        }

        System.out.println("\n========================================================");
        System.out.println("                   STUDENT RECORDS LIST                 ");
        System.out.println("========================================================");
        System.out.printf("%-5s | %-20s | %-10s | %-6s%n", "No.", "Student Name", "Marks", "Grade");
        System.out.println("--------------------------------------------------------");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%-5d | %-20s | %10.2f | %-6s%n", (i + 1), s.getName(), s.getMarks(), s.getLetterGrade());
        }
        System.out.println("========================================================");
        System.out.println("Total Students: " + students.size());
    }

    /**
     * Displays comprehensive statistical summary report.
     */
    public void displaySummary() {
        if (students.isEmpty()) {
            System.out.println("\n[!] No data available to generate summary report.");
            return;
        }

        Student topStudent = getHighestScorer();
        Student lowStudent = getLowestScorer();
        double avgMarks = calculateAverage();

        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        int passCount = 0;

        for (Student s : students) {
            if (s.getMarks() >= 50.0) {
                passCount++;
            }
            switch (s.getLetterGrade()) {
                case "A+":
                case "A":
                    countA++;
                    break;
                case "B":
                    countB++;
                    break;
                case "C":
                    countC++;
                    break;
                case "D":
                    countD++;
                    break;
                default:
                    countF++;
                    break;
            }
        }

        double passRate = ((double) passCount / students.size()) * 100.0;

        System.out.println("\n========================================================");
        System.out.println("               PERFORMANCE SUMMARY REPORT               ");
        System.out.println("========================================================");
        System.out.printf(" Total Students Recorded : %d%n", students.size());
        System.out.printf(" Class Average Marks     : %.2f / 100%n", avgMarks);
        System.out.printf(" Highest Marks Scored    : %.2f (By %s)%n", topStudent.getMarks(), topStudent.getName());
        System.out.printf(" Lowest Marks Scored     : %.2f (By %s)%n", lowStudent.getMarks(), lowStudent.getName());
        System.out.printf(" Overall Pass Rate       : %.2f%% (%d passed, %d failed)%n", passRate, passCount, (students.size() - passCount));
        System.out.println("--------------------------------------------------------");
        System.out.println(" Grade Distribution Breakdown:");
        System.out.printf("   - Grade A+/A (80-100) : %d student(s)%n", countA);
        System.out.printf("   - Grade B    (70-79)  : %d student(s)%n", countB);
        System.out.printf("   - Grade C    (60-69)  : %d student(s)%n", countC);
        System.out.printf("   - Grade D    (50-59)  : %d student(s)%n", countD);
        System.out.printf("   - Grade F    (< 50)   : %d student(s)%n", countF);
        System.out.println("========================================================");
    }

    /**
     * Clears all student records.
     */
    public void clearAll() {
        students.clear();
        System.out.println("\n[✓] All student records have been cleared.");
    }
}
