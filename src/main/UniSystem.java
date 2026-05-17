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
            database.addUser(admin);
            database.addUser(manager);
            database.addUser(new Student("islam", "islam", "", 2));
            database.addUser(new Student("islam", "islam", "", 2));
            database.addUser(new Student("islam", "islam", "", 2));
            database.addUser(new Student("islam", "islam", "", 2));
            database.addUser(new Student("islam", "islam", "", 2));
            MainMenu menu = new MainMenu();
            menu.start();
        }
        catch(UserExistsException e){
            System.out.println(e.getMessage());
        }
    }

}
