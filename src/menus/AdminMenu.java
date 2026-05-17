package menus;
import exceptions.*;
import storage.Database;
import storage.Log;
import types.ManagerType;
import types.TeacherType;
import users.*;
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
                    Admin Menu
                    -->
                    1. Add student
                    2. Remove student
                    3. Add Teacher
                    4. Remove teacher
                    5. Add Manager
                    6. Remove Manager
                    7. See log
                    0. Exit
                    <--
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
                    addManager(input,database);
                    break;
                case "6":
                    removeManager(input, database);
                    break;
                case "7":
                    showAllUsers(input, database);
                    break;
                case "8":
                    break;
                case "9":
                    database.getLogs().forEach(item -> System.out.print(item + "\n"));
                    break;
                case "0":
                    return;
                default:
                    System.out.print("Incorrect command. try again");
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
            } catch (UserExistsException e) {
                System.out.print("That user already exists\n");
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
            } catch (UserNotFoundException e) {
                System.out.print("Student not found, try again");
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
                        type = TeacherType.TUTOR;
                        break;
                    case 3:
                        type = TeacherType.TUTOR;
                        break;
                    case 4:
                        type = TeacherType.TUTOR;
                        break;
                    default:
                        System.out.print("Incorrect command. try again");
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
            } catch (UserExistsException e) {
                System.out.print("Teacher already exists");
            }
        }
    }

    public void removeTeacher(Scanner input, Database db) {
        while (true) {
            System.out.print("Enter teacher id: ");
            int id = input.nextInt();
            Teacher teacher = db.getTeachers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
            input.nextLine();
            try {
                db.deleteUser(teacher);
                System.out.print("Teacher successfully deleted\n");
                Log log = new Log(db.getUser().getUsername(), "removed teacher");
                db.addLogs(log);
                break;
            } catch (UserNotFoundException e) {
                System.out.print("Teacher not found, try again");
            }
        }
    }

    public void addManager(Scanner input, Database db){
        while(true){
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
                break;
            } catch (UserExistsException e) {
                System.out.print("Manager already exists");
            }
        }
    }

    public void removeManager(Scanner input, Database db){
        while (true) {
            System.out.print("Enter manager id: ");
            int id = input.nextInt();
            Manager manager = db.getManagers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
            input.nextLine();

            try {
                db.deleteUser(manager);
                System.out.print("Manager successfully deleted\n");
                Log log = new Log(db.getUser().getUsername(), "removed manager");
                db.addLogs(log);
                break;
            } catch (UserNotFoundException e) {
                System.out.print("Manager not found, try again");
            }
        }
    }

    public void showAllUsers(Scanner in, Database db){
        List<User> users = db.getUsers();
        StringBuilder sb = new StringBuilder();

        int username = 8;
        int id = 2;
        int type = 4;
        for(User user : users){
            id = Math.max(String.valueOf(Math.abs(user.getId())).length(), id);
            username = Math.max(String.valueOf(user.getUsername()).length(), username);
            type = Math.max(user.getClass().getSimpleName().length(), type);
        }
        System.out.printf(
                "| %s | %s | %s |\n",
                center("ID", id), center("USERNAME", username), center("TYPE", type)
        );
        for(User user : users){
            System.out.printf("| %s | %s | %s |\n", center(String.valueOf(user.getId()), id), center(user.getUsername(), username), center(user.getClass().getSimpleName(), type));
        }

    }

    public static String center(String text, int width) {
        int padding = width - text.length();
        if(padding < 0) padding = text.length();

        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }
}