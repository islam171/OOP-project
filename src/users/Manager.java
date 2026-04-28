package users;

import academic.Course;
import academic.News;
import main.UniSystem;
import storage.Database;

import java.util.List;
import java.util.Scanner;

public class Manager extends Employee {


    private ManagerType type;

    public Manager(String username, String password, double salary, ManagerType type) {
        super(username, password, salary);
        setType(type);
    }

    @Override
    public boolean showCommands() {

        System.out.println("Add course: 1");
        System.out.println("Assign teacher: 2");
        System.out.println("Approve Register: 3");
        System.out.println("Add news: 4");
        System.out.println("Remove news: 5");
        System.out.println("Update news: 6");
        System.out.println("View students by GPA: 7");
        System.out.println("View students by name: 8");
        System.out.println("Break: 0");

        Scanner input = new Scanner(System.in);
        int command = input.nextInt();
        switch (command) {
            case 0:
                return false;
            case 1:
                addCourse(input);
                break;
            case 2:
                assignTeacher(input);
                break;
            case 3:
                approveRegister();
                break;
            case 4:
                addNews(input);
                break;
            case 5:
                removeNews(input);
                break;
            case 6:
                updateNews(input);
                break;
            case 7:
                viewStudentsByGPA();
                break;
            case 8:
                viewStudentsByName();
                break;

            default:
                return false;
        }

        return true;
    }

    public void addCourse(Scanner input) {
        System.out.println("Enter name: ");
        String name = input.next();
        System.out.println("Enter number of credits: ");
        int credits = input.nextInt();
        Database database = Database.getInstance();
        database.addCourse(new Course(name, credits));
    }

    public void assignTeacher(Scanner input) {
        System.out.println("Enter name of the teacher: ");
        String name = input.next();
        System.out.println("Enter password: ");
        String password = input.next();
        Database database = Database.getInstance();
        database.addUser(new Teacher(name, password, 10.0, TeacherType.TUTOR));

    }

    public void approveRegister() {
        System.out.println("Registry complete!");
    }

    public void addNews(Scanner input) {
        System.out.println("Enter title: ");
        String title = input.next();
        System.out.println("Enter content: ");
        String content = input.next();
        Database database = Database.getInstance();
        database.addNews(new News(title, content));
    }

    public void removeNews(Scanner input) {
        System.out.println("Enter ID of the news post: ");
        int id = input.nextInt();
        Database database = Database.getInstance();
        database.deleteNewById(id);
    }

    public void updateNews(Scanner input) {
        Database database = Database.getInstance();
        System.out.println("Enter ID of the news post: ");
        int id = input.nextInt();

        System.out.println("Change title: 1");
        System.out.println("Change content: 2");
        System.out.println("Apply changes: 3");
        System.out.println("Break: 0");

        String content = "";
        String title = "";
        int command = input.nextInt();
        switch (command) {
            case 0:
                break;
            case 1:
                System.out.println("Enter new title: ");
                title = input.next();
            case 2:
                System.out.println("Enter new content: ");
                content = input.next();
            case 3:
                if (content.isEmpty() && title.isEmpty()) {
                    System.out.println("No new changes applied");
                } else {

                    database.updateNews(id, new News(title, content));
                }

            default:
                break;
        }
    }

    public void viewStudentsByGPA() {
        Database database = Database.getInstance();
        List<Student> students = database.getStudents();

        students.sort(new ComparatorGPA());


        for (Student student : students) {
            System.out.println("Student's name: " + student.getUsername() +
                    "; Student's GPA: " + student.getGPA());

        }

    }

    public void viewStudentsByName() {
        Database database = Database.getInstance();
        List<Student> students = database.getStudents();

        students.sort(new ComparatorName());


        for (Student student : students) {
            System.out.println("Student's name: " + student.getUsername() +
                    " Student's GPA: " + student.getGPA());

        }
    }


    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

}
