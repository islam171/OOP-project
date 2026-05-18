package menus;

import exceptions.*;
import storage.Database;
import storage.Log;
import types.ManagerType;
import types.TeacherType;
import users.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();
        while (true) {
            String s = """
                    
                    ======= Admin Menu =======
                    1. Add student
                    2. Remove student
                    3. Add Teacher
                    4. Remove teacher
                    5. Add Manager
                    6. Remove Manager
                    7. See log
                    8. Save data
                    9. Load data
                    10. Show Users
                    11. Messages
                    0. Exit
                    ===========================
                    """;
            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    addStudent(input, database);
                    break;
                case "2":
                    deleteStudent(input, database);
                    break;
                case "3":
                    addTeacher(input, database);
                    break;
                case "4":
                    removeTeacher(input, database);
                    break;
                case "5":
                    addManager(input, database);
                    break;
                case "6":
                    removeManager(input, database);
                    break;
                case "7":
                    database.getLogs().forEach(item -> System.out.print(item + "\n"));
                    break;
                case "8":
                    saveDate(database);
                    break;
                case "9":
                    loadDate(database);
                    break;
                case "10":
                    showAllUsers(input, database);
                    break;
                case "11":
                    manageMessage(input, database);
                    break;
                case "0":
                    return;
                default:
                    System.out.print("Incorrect command. try again\n");
            }
        }
    }


    public void addStudent(Scanner input, Database db) {
        while (true) {
            System.out.print("Enter student name: ");
            String name = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            System.out.print("Enter student speciality: ");
            String speciality = input.nextLine();

            System.out.print("Enter year of study: ");
            int year = input.nextInt();
            input.nextLine();

            Student student = new Student(name, password, speciality, year);
            try {
                db.addUser(student);
                System.out.print("Student successfully added\n");
                Log log = new Log(db.getUser().getUsername(), "added student");
                db.addLogs(log);
                return;
            } catch (UserException e) {
                System.out.print(e.getMessage() + "\n");
            }
        }
    }

    public void deleteStudent(Scanner input, Database db) {
        while (true) {
            System.out.print("Enter student id: ");
            int id = input.nextInt();
            Student student1 = db.getStudents().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
            input.nextLine();
            try {
                db.deleteUser(student1);
                System.out.print("Student successfully deleted\n");
                Log log = new Log(db.getUser().getUsername(), "deleted student");
                db.addLogs(log);
                break;
            } catch (UserException e) {
                System.out.print(e.getMessage() + "\n");
            }
        }
    }

    public void addTeacher(Scanner input, Database db) {
        while (true) {
            System.out.print("Enter teacher name: ");
            String name = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            System.out.print("Enter salary: ");
            double salary = input.nextDouble();

            TeacherType type;

            while (true) {
                System.out.print("""
                        Choose teacher type: 
                            1. TUTOR
                            2. PROFESSOR
                            3. LECTOR
                            4. SENIOR LECTOR
                        """);
                int comm = input.nextInt();
                switch (comm) {
                    case 1:
                        type = TeacherType.TUTOR;
                        break;
                    case 2:
                        type = TeacherType.PROFESSOR;
                        break;
                    case 3:
                        type = TeacherType.LECTOR;
                        break;
                    case 4:
                        type = TeacherType.SENIOR_LECTOR;
                        break;
                    default:
                        System.out.print("Incorrect command. try again\n");
                        continue;
                }
                break;
            }
            input.nextLine();
            Teacher teacher = new Teacher(name, password, salary, type);
            try {
                db.addUser(teacher);
                System.out.print("Teacher successfully added\n");
                Log log = new Log(db.getUser().getUsername(), "added teacher");
                db.addLogs(log);

                break;
            } catch (UserException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    public void removeTeacher(Scanner input, Database db) {

        System.out.print("Enter teacher id: ");
        int id = input.nextInt();
        Teacher teacher = db.getTeachers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        input.nextLine();
        try {
            db.deleteUser(teacher);
            System.out.print("Teacher successfully deleted\n");
            Log log = new Log(db.getUser().getUsername(), "removed teacher");
            db.addLogs(log);

        } catch (UserException e) {
            System.out.print(e.getMessage() + "\n");
        }

    }

    public void addManager(Scanner input, Database db) {

        System.out.print("Enter manager name: ");
        String name = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter salary: ");
        double salary = input.nextDouble();

        ManagerType type;

        while (true) {
            System.out.print("""
                    Choose manager type: 
                        1. HR
                        2. OR
                        3. FINANCE
                    """);
            int comm = input.nextInt();
            switch (comm) {
                case 1:
                    type = ManagerType.HR;
                    break;
                case 2:
                    type = ManagerType.OR;
                    break;
                case 3:
                    type = ManagerType.FINANCE;
                    break;
                default:
                    System.out.print("Incorrect command");
                    continue;
            }
            break;
        }
        input.nextLine();
        Manager manager = new Manager(name, password, salary, type);
        try {
            db.addUser(manager);
            System.out.print("Manager successfully added\n");
            Log log = new Log(db.getUser().getUsername(), "added manger");
            db.addLogs(log);

        } catch (UserException e) {
            System.out.print(e.getMessage() + "\n");
        }

    }

    public void removeManager(Scanner input, Database db) {

            System.out.print("Enter manager id: ");
            int id = input.nextInt();
            Manager manager = db.getManagers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
            input.nextLine();

            try {
                db.deleteUser(manager);
                System.out.print("Manager successfully deleted\n");
                Log log = new Log(db.getUser().getUsername(), "removed manager");
                db.addLogs(log);
            } catch (UserException e) {
                System.out.print(e.getMessage()+"\n");
            }

    }

    public void showAllUsers(Scanner in, Database db) {
        List<User> users = db.getUsers();

        int username = 8;
        int id = 2;
        int type = 4;
        for (User user : users) {
            id = Math.max(String.valueOf(Math.abs(user.getId())).length(), id);
            username = Math.max(String.valueOf(user.getUsername()).length(), username);
            type = Math.max(user.getClass().getSimpleName().length(), type);
        }
        System.out.printf(
                "| %s | %s | %s |\n",
                center("ID", id), center("USERNAME", username), center("TYPE", type)
        );
        for (User user : users) {
            System.out.printf("| %s | %s | %s |\n", center(String.valueOf(user.getId()), id), center(user.getUsername(), username), center(user.getClass().getSimpleName(), type));
        }

    }

    public static String center(String text, int width) {
        int padding = width - text.length();
        if (padding < 0) padding = text.length();

        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }

    public void saveDate(Database db) {
        try {
            db.save();
            System.out.print("Data saved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDate(Database db) {
        try {
            db.loadData();
            System.out.print("Data loaded");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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
                        admin.viewMessages();
                        break;
                    case 0:
                        return;
                    default:
                        return;
                }

            } catch (PermissionException e) {
                System.out.print(e.getMessage() + "\n");
            }
        }
    }

    public void sendMessage(Scanner input, Database db) {
        System.out.println("===== Users =====");
        db.getUsers().stream().filter(user -> (user instanceof Employee && !user.equals(this.admin))).forEach(u -> System.out.println("| ID: " + u.getId() + " | " + u.getUsername() + "|"));

        System.out.print("Enter recipient ID: ");
        try {
            int userId = Integer.parseInt(input.nextLine().trim());
            var recipient = db.getUserById(userId);

            System.out.print("Enter message: ");
            String text = input.nextLine();

            admin.sendMessage(recipient, text);
            System.out.print("Message sent.\n");

        } catch (NumberFormatException | PermissionException | MessageException e) {
            System.out.print("Invalid input.\n");
        }
    }

}