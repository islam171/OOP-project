package main;

import academic.Course;
import exceptions.CourseExistsException;
import exceptions.TeacherException;
import exceptions.UserExistsException;
import storage.Database;
import types.ManagerType;
import types.MarkType;
import types.TeacherType;
import users.Manager;
import users.Student;
import users.Teacher;

import java.io.*;

public class UniSystem {

    static void main() throws IOException, ClassNotFoundException {

        Teacher teacher = new Teacher("islam", "ds", 200000.0, TeacherType.LECTOR);
        Student student = new Student("Hello", "hello", "IS", 2);
        Student student1 = new Student("Student1", "student1", "IS", 2);
        Student student2 = new Student("Student2", "student2", "IS", 2);
        Course course1 = new Course("PP2", 5);
        Manager manager = new Manager("idfsf", "fdsfds", 221423.0, ManagerType.HR);
        Database database = Database.getInstance();

        try {
            database.addUser(teacher);
            database.addUser(student);
            database.addUser(student1);
            database.addUser(student2);

            database.addCourse(course1);
            student.addCourse(course1);
            student1.addCourse(course1);
            student2.addCourse(course1);

            teacher.putMark(student, MarkType.FINAL, 35);
            teacher.putMark(student, MarkType.FIRST__ATTESTATION, 27);
            teacher.putMark(student, MarkType.SECOND__ATTESTATION, 28);
            teacher.putMark(student1, MarkType.FINAL, 25);
            teacher.putMark(student1, MarkType.FIRST__ATTESTATION, 30);
            teacher.putMark(student1, MarkType.SECOND__ATTESTATION, 25);
            teacher.putMark(student2, MarkType.FINAL, 31);
            teacher.putMark(student2, MarkType.FIRST__ATTESTATION, 26);
            teacher.putMark(student2, MarkType.SECOND__ATTESTATION, 28);

            manager.generateReport();

        } catch (CourseExistsException e) {
            System.out.println("That course already exists");
        } catch (UserExistsException | TeacherException e) {
            System.out.println(e.getMessage());
        }
    }
}
