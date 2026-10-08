import java.util.Scanner;

/**
 * Main.java
 * Console-based user interface for CodeAlpha Student Grade Tracker.
 * 
 * Features:
 * - Interactive text-based menu
 * - Input validation for student names and marks (0 - 100)
 * - Safe numeric parsing to prevent scanner crashes
 * - Clear and beginner-friendly program structure
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GradeTracker tracker = new GradeTracker();

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readIntInput("Enter your choice (1-5): ", 1, 5);

            switch (choice) {
                case 1:
                    handleAddStudents();
                    break;
                case 2:
                    tracker.displayAllStudents();
                    break;
                case 3:
                    tracker.displaySummary();
                    break;
                case 4:
                    handleClearRecords();
                    break;
                case 5:
                    System.out.println("\nThank you for using CodeAlpha Student Grade Tracker. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please select between 1 and 5.");
            }
            if (running) {
                System.out.println("\nPress Enter to return to menu...");
                scanner.nextLine();
            }
        }
    }

    /**
     * Prints application welcome banner.
     */
    private static void printBanner() {
        System.out.println("========================================================");
        System.out.println("         CodeAlpha - Student Grade Tracker              ");
        System.out.println("            Java Programming Internship                 ");
        System.out.println("========================================================");
    }

    /**
     * Displays main menu options.
     */
    private static void displayMenu() {
        System.out.println("\n----------------- MAIN MENU -----------------");
        System.out.println(" 1. Add Student Grade(s)");
        System.out.println(" 2. View All Students and Marks");
        System.out.println(" 3. View Summary Report (Average, High, Low)");
        System.out.println(" 4. Clear All Student Records");
        System.out.println(" 5. Exit Application");
        System.out.println("---------------------------------------------");
    }

    /**
     * Handles student data entry flow.
     */
    private static void handleAddStudents() {
        System.out.println("\n--- Add Student Grade ---");
        int count = readIntInput("How many students would you like to add? ", 1, 1000);

        for (int i = 1; i <= count; i++) {
            System.out.println("\nStudent #" + i + ":");
            String name = readNonEmptyString("  Enter Student Name: ");
            double marks = readMarksInput("  Enter Marks (0 - 100): ");

            Student student = new Student(name, marks);
            tracker.addStudent(student);
            System.out.printf("  [✓] Successfully added %s with %.2f marks (Grade %s).%n", 
                student.getName(), student.getMarks(), student.getLetterGrade());
        }

        System.out.println("\n[✓] All " + count + " student record(s) added successfully!");
    }

    /**
     * Confirms and clears all student records.
     */
    private static void handleClearRecords() {
        if (!tracker.hasStudents()) {
            System.out.println("\n[!] No records to clear.");
            return;
        }

        System.out.print("\nAre you sure you want to delete all student records? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            tracker.clearAll();
        } else {
            System.out.println("[i] Operation cancelled.");
        }
    }

    /**
     * Reads a non-empty string from console.
     */
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("    [!] Error: Name cannot be blank. Please try again.");
        }
    }

    /**
     * Validates and reads double marks between 0 and 100.
     */
    private static double readMarksInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (marks >= 0.0 && marks <= 100.0) {
                    return marks;
                } else {
                    System.out.println("    [!] Error: Marks must be between 0.0 and 100.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("    [!] Error: Invalid numeric input. Please enter a valid number (e.g., 85 or 92.5).");
            }
        }
    }

    /**
     * Validates and reads integer choice within range [min, max].
     */
    private static int readIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("    [!] Please enter a number between %d and %d.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("    [!] Invalid input. Please enter a whole number.");
            }
        }
    }
}
