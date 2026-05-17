package main;

import academic.Course;
import exceptions.CourseExistsException;
import exceptions.MarkWrongException;
import exceptions.TeacherException;
import exceptions.UserExistsException;
import menus.MainMenu;
import storage.Database;
import types.ManagerType;
import types.MarkType;
import types.TeacherType;
import users.*;

import java.io.*;

public class UniSystem {

    static void main() {
        try {
            Database database = Database.getInstance();
            Admin admin = new Admin("Admin", "Admin", 20000);
            Manager manager = new Manager("1", "1", 20000, ManagerType.OR);
            Teacher teacher = new Teacher("techer1", "teacheer1", 100000, TeacherType.LECTOR);
            database.addUser(new Student("islam", "islam", "", 2));
            database.addUser(teacher);
            database.addCourse(new Course("Math", 5, teacher));

            database.addUser(admin);
            database.addUser(manager);
            MainMenu menu = new MainMenu();
            menu.start();
        }
        catch(UserExistsException | CourseExistsException e){
            System.out.println(e.getMessage());
        }
    }

}
