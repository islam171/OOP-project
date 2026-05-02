package users;

import academic.Course;
import academic.News;
import main.UniSystem;
import storage.Database;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Scanner;

public class Manager extends Employee {


    private ManagerType type;

    public Manager(String username, String password, double salary, ManagerType type) {
        super(username, password, salary);
        setType(type);
    }


    public void addCourse() {

    }

    public void assignTeacher(Scanner input) {

    }

    public void approveRegister() {
        System.out.println("Registry complete!");
    }

    public void addNews(Scanner input) {

    }

    public void removeNews(Scanner input) {

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
