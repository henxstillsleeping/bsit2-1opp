import java.util.Scanner;

public class Main {

    // ----- Data storage (parallel arrays), capacity of 10 students -----
    static final int MAX_STUDENTS = 10;

    static int[] ids = new int[MAX_STUDENTS];
    static String[] names = new String[MAX_STUDENTS];
    static int[] ages = new int[MAX_STUDENTS];
    static String[] courses = new String[MAX_STUDENTS];
    static double[] grades = new double[MAX_STUDENTS];
    static boolean[] enrolled = new boolean[MAX_STUDENTS];

    static int studentCount = 0; // how many slots are currently used

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        // Loop keeps showing the menu until the user chooses to exit
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchById();
                    break;
                case 4:
                    viewStatistics();
                    break;
                case 5:
                    System.out.println("\nGoodbye! Thank you for using the Student Information System.");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select a number from 1 to 5.");
            }
        }

        scanner.close();
    }

    // ----------------------------------------------------------------
    // Menu display
    // ----------------------------------------------------------------
    static void printMenu() {
        System.out.println("\n===== STUDENT INFORMATION SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search by ID");
        System.out.println("4. View Statistics");
        System.out.println("5. Exit");
        System.out.println("=======================================");
    }

    // ----------------------------------------------------------------
    // Option 1: Add Student
    // ----------------------------------------------------------------
    static void addStudent() {
        // Reject input when the list is already full
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("\nCannot add more students. The system is full (max " + MAX_STUDENTS + ").");
            return;
        }

        System.out.println("\n--- Add New Student ---");

        int id = readInt("Enter Student ID: ");

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        // Validate age (must be positive)
        int age;
        while (true) {
            age = readInt("Enter Age: ");
            if (age > 0) {
                break;
            }
            System.out.println("Age must be a positive number. Try again.");
        }

        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        // Validate grade (must be between 0 and 100)
        double grade;
        while (true) {
            grade = readDouble("Enter Grade/Score (0-100): ");
            if (grade >= 0 && grade <= 100) {
                break;
            }
            System.out.println("Grade must be between 0 and 100. Try again.");
        }

        boolean isEnrolled = readBoolean("Is the student currently enrolled? (yes/no): ");

        // Store the data in the parallel arrays
        ids[studentCount] = id;
        names[studentCount] = name;
        ages[studentCount] = age;
        courses[studentCount] = course;
        grades[studentCount] = grade;
        enrolled[studentCount] = isEnrolled;

        studentCount++;

        System.out.println("\nStudent added successfully! (" + studentCount + "/" + MAX_STUDENTS + " slots used)");
    }

    // ----------------------------------------------------------------
    // Option 2: View All Students
    // ----------------------------------------------------------------
    static void viewAllStudents() {
        System.out.println("\n--- All Students ---");

        if (studentCount == 0) {
            System.out.println("No students recorded yet.");
            return;
        }

        // Header for aligned output
        System.out.printf("%-5s %-20s %-5s %-15s %-8s %-10s %-12s%n",
                "ID", "Name", "Age", "Course", "Grade", "Enrolled", "Standing");
        System.out.println("---------------------------------------------------------------------------");

        for (int i = 0; i < studentCount; i++) {
            String standing;

            // if/else to determine standing based on grade
            if (grades[i] >= 90) {
                standing = "Dean's Lister";
            } else if (grades[i] >= 75) {
                standing = "Passed";
            } else {
                standing = "Failed";
            }

            String enrolledText = enrolled[i] ? "Yes" : "No";

            System.out.printf("%-5d %-20s %-5d %-15s %-8.2f %-10s %-12s%n",
                    ids[i], names[i], ages[i], courses[i], grades[i], enrolledText, standing);
        }
    }

    // ----------------------------------------------------------------
    // Option 3: Search by ID
    // ----------------------------------------------------------------
    static void searchById() {
        if (studentCount == 0) {
            System.out.println("\nNo students recorded yet.");
            return;
        }

        int searchId = readInt("\nEnter Student ID to search: ");
        boolean found = false;

        for (int i = 0; i < studentCount; i++) {
            if (ids[i] == searchId) { // relational operator ==
                found = true;

                String standing;
                if (grades[i] >= 90) {
                    standing = "Dean's Lister";
                } else if (grades[i] >= 75) {
                    standing = "Passed";
                } else {
                    standing = "Failed";
                }

                System.out.println("\n--- Student Found ---");
                System.out.println("ID       : " + ids[i]);
                System.out.println("Name     : " + names[i]);
                System.out.println("Age      : " + ages[i]);
                System.out.println("Course   : " + courses[i]);
                System.out.println("Grade    : " + grades[i]);
                System.out.println("Enrolled : " + (enrolled[i] ? "Yes" : "No"));
                System.out.println("Standing : " + standing);
                break; // no need to keep looping once found
            }
        }

        if (!found) {
            System.out.println("\nNo student found with ID " + searchId + ".");
        }
    }

    // ----------------------------------------------------------------
    // Option 4: View Statistics
    // ----------------------------------------------------------------
    static void viewStatistics() {
        System.out.println("\n--- Class Statistics ---");

        if (studentCount == 0) {
            System.out.println("No students recorded yet.");
            return;
        }

        double total = 0;
        double topGrade = grades[0];
        String topName = names[0];

        for (int i = 0; i < studentCount; i++) {
            total += grades[i]; // accumulate total using + operator

            // Comparison inside loop to track the top student
            if (grades[i] > topGrade) {
                topGrade = grades[i];
                topName = names[i];
            }
        }

        double average = total / studentCount; // division operator

        System.out.println("Total Number of Students : " + studentCount);
        System.out.printf("Average Grade             : %.2f%n", average);
        System.out.println("Top Student               : " + topName + " (" + topGrade + ")");
    }

    // ----------------------------------------------------------------
    // Input helper methods (basic validation so the program never crashes
    // on non-numeric input)
    // ----------------------------------------------------------------
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("yes") || line.equals("y")) {
                return true;
            } else if (line.equals("no") || line.equals("n")) {
                return false;
            } else {
                System.out.println("Please answer with yes or no.");
            }
        }
    }
}