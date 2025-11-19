import java.util.Scanner;

// Custom Exception
class MarksException extends Exception {
    public MarksException(String msg) {
        super(msg);
    }
}

class Student {
    int roll;
    String name;
    int[] marks = new int[3];

    public Student(int roll, String name, int[] marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
    }

    public void checkMarks() throws MarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new MarksException(
                        "Invalid marks for subject " + (i + 1) + ": " + marks[i]
                );
            }
        }
    }

    public double avg() {
        int sum = 0;
        for (int m : marks) sum += m;
        return sum / 3.0;
    }

    public void show() {
        System.out.println("Roll: " + roll);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks[0] + " " + marks[1] + " " + marks[2]);
        double a = avg();
        System.out.println("Average: " + a);
        System.out.println(a >= 40 ? "Result: Pass" : "Result: Fail");
    }
}

class ResultApp {

    Student[] list = new Student[50];
    int count = 0;
    Scanner sc = new Scanner(System.in);

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            int[] m = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                m[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, m);
            s.checkMarks();

            list[count++] = s;

            System.out.println("Student added successfully.");

        } catch (MarksException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Invalid input!");
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    public void showStudent() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();

            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (list[i].roll == roll) {
                    list[i].show();
                    found = true;
                    break;
                }
            }

            if (!found)
                System.out.println("Student not found.");

        } catch (Exception e) {
            System.out.println("Error while searching.");
        } finally {
            System.out.println("Search completed.");
        }
    }

    public void menu() {
        int ch;

        try {
            while (true) {
                System.out.println("\n===== Student Result System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");

                System.out.print("Enter your choice: ");
                ch = sc.nextInt();

                switch (ch) {
                    case 1: addStudent(); break;
                    case 2: showStudent(); break;
                    case 3:
                        System.out.println("Exiting... Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } finally {
            sc.close();
        }
    }

    public static void main(String[] args) {
        ResultApp app = new ResultApp();
        app.menu();
    }
}

