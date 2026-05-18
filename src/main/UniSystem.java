package main;

import academic.Course;
import exceptions.*;
import menus.MainMenu;
import storage.Database;
import types.ManagerType;
import types.MarkType;
import types.TeacherType;
import users.*;

import java.io.*;
import java.rmi.StubNotFoundException;

public class UniSystem {

    static void main() {
        try {
            Database database = Database.getInstance();
            Admin admin = new Admin("Admin", "Admin", 20000);
            Manager manager = new Manager("1", "1", 20000, ManagerType.OR);
            Teacher teacher = new Teacher("2", "2", 100000, TeacherType.LECTOR);
            Student student = new Student("3", "3", "", 2);
            Employee employee = new Employee("4", "4", 100000);
            Course course = new Course("Math", 5, teacher);
            try {
                database.addUser(student);
                database.addUser(teacher);
                database.addCourse(course);
                database.addUser(admin);
                database.addUser(manager);
                database.addUser(employee);
                student.addCourse(course);

                database.putMark(course, student, MarkType.FIRST__ATTESTATION, 25.0);
                database.putMark(course, student, MarkType.SECOND__ATTESTATION, 25.0);
                database.putMark(course, student, MarkType.FINAL, 25.0);
            } catch (MarkWrongException e) {
                throw new RuntimeException(e);
            } catch (StubNotFoundException e) {
                System.out.print("Student not found");
            } catch (StudentCourseException e) {
                System.out.print(e.getMessage() + "\n");
                return;
            }

            MainMenu menu = new MainMenu();
            menu.start();
        } catch (UserExistsException | CourseExistsException e) {
            System.out.println(e.getMessage());
        } catch (UserNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

}
