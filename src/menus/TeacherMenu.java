package menus;

import exceptions.ResearcherException;
import exceptions.PermissionException;
import exceptions.UserException;
import research.ResearcherDecorator;
import storage.Database;
import storage.Log;
import types.MarkType;
import users.Employee;
import users.Student;
import users.Teacher;

import java.rmi.StubNotFoundException;
import java.util.Scanner;

public class TeacherMenu {

    private Teacher teacher;

    public TeacherMenu(Teacher teacher) {
        this.teacher = teacher;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();
        while (true) {
            boolean isResearcher = database.getResearcherByUser(this.teacher) != null;
            String s = """
                    
                    ====== TeacherMenu ======
                    1. View my courses
                    2. View students
                    3. View lessons
                    4. Put mark
                    5. View student info
                    """ + (isResearcher ? "6. Researcher menu\n" : "6. Become Researcher\n") + """
                    7. Message
                    0. Exit
                    ===========================
                    """;

            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    teacher.viewCourses();
                    break;
                case "2":
                    teacher.viewStudents();
                    break;
                case "3":
                    teacher.viewLessons();
                    break;
                case "4":
                    putMark(input, database);
                    break;
                case "5":
                    viewStudentInfo(input, database);
                    break;
                case "6":
                    if (isResearcher) {
                        ResearcherDecorator researcher = database.getResearcherByUser(this.teacher);
                        new ResearcherMenu(researcher).menu();
                    } else becomeResearcher(input, database);
                    break;
                case "7":
                    manageMessage(input, database);
                    break;
                case "0":
                    return;

                default:
                    System.out.println("Wrong comm");
            }
        }
    }

    public void putMark(Scanner input, Database database) {
        while (true) {
            try {
                Database db = Database.getInstance();

                System.out.print("Student id: ");
                int studentId = Integer.parseInt(input.nextLine());

                Student student = db.getStudentById(studentId);

                if (student == null) {
                    System.out.println("Student not found");
                    return;
                }

                System.out.print("""
                        Select mark type:
                        1. First attestation
                        2. Second attestation
                        3. Final
                        0.Cancel
                        """);

                MarkType type = null;
                int comm = input.nextInt();
                input.nextLine();
                switch (comm) {
                    case 1:
                        type = MarkType.FIRST__ATTESTATION;
                        break;
                    case 2:
                        type = MarkType.SECOND__ATTESTATION;
                        break;
                    case 3:
                        type = MarkType.FINAL;
                        break;
                    case 0:
                        return;
                    default:
                        System.out.print("Invalid command\n");
                        continue;
                }

                System.out.print("Points: ");
                double points = Double.parseDouble(input.nextLine());

                teacher.putMark(student, type, points);
                System.out.println("mark added");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void viewStudentInfo(Scanner input, Database db) {

        System.out.print("Enter student id: ");
        int id = Integer.parseInt(input.nextLine());
        try {
            teacher.viewStudentInfo(id);
        } catch (StubNotFoundException e) {
            System.out.print("Student not found try again\n");
        }

    }

    public void becomeResearcher(Scanner input, Database database) {
        try{
            this.teacher.becomeResearcher();
            System.out.println("You are now registered as a Researcher!");

            Log log = new Log(database.getUser().getUsername(), "become researcher");
            database.addLogs(log);
        } catch (ResearcherException | UserException e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    public void manageMessage(Scanner input, Database db) {
        while (true) {
            try {

                System.out.print("""
                        1. Send message
                        2. Read message
                        0. Cancel
                        """);
                int command = input.nextInt();
                input.nextLine();
                switch (command) {
                    case 1:
                        sendMessage(input, db);
                        break;
                    case 2:
                        teacher.viewMessages();
                        break;
                    case 0:
                        return;
                    default:
                        return;
                }

            } catch (PermissionException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    public void sendMessage(Scanner input, Database db) {
        System.out.println("===== Users =====");
        db.getUsers().stream().filter(user -> (user instanceof Employee && !user.equals(this.teacher))).forEach(u -> System.out.println("| ID: " + u.getId() + " | " + u.getUsername() + "|"));

        System.out.print("Enter recipient ID: ");
        try {
            int userId = Integer.parseInt(input.nextLine().trim());
            var recipient = db.getUserById(userId);

            System.out.print("Enter message: ");
            String text = input.nextLine();

            teacher.sendMessage(recipient, text);
            System.out.println("Message sent.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
