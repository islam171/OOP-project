package menus;

import academic.Course;
import academic.Mark;
import academic.News;
import exceptions.CourseNotFoundException;
import exceptions.NewsExistsException;
import storage.Database;
import users.Manager;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private Manager manager;

    public ManagerMenu(Manager manager) {
        this.manager = manager;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = new Database();
        while(true) {
            String s = """
                    StudentMenu
                    -->
                    1. Add course
                    2. Remove course
                    3. Add news
                    4. Remove news
                    5. Approve registration
                    6. Reject registration
                    7. Assign teacher
                    8. View students by GPA
                    9. View students by name
                    10. Generate report
                    11. View all teachers
                    0. Exit
                    <--
                    """;
            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    System.out.print("enter course name: ");
                    String name = input.nextLine();

                    System.out.print("enter credits: ");
                    int credit = Integer.parseInt(input.nextLine());

                    Course course = new Course(name, credit);
                    manager.addCourse(course);
                    break;
                case "2":
                    System.out.print("enter course name: ");
                    String courseName = input.nextLine();
                    database = new Database();
                    Course c = database.getCourses().stream().filter(item -> item.getName().equals(courseName)).findFirst().orElse(null);
                    try {
                        manager.removeCourse(c);
                    } catch (CourseNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "3":
                    System.out.println("enter title: ");
                    String title = input.nextLine();
                    System.out.println("enter content");
                    String content = input.nextLine();
                    News news = new News(title, content);
                    try {
                        manager.addNews(news);
                    } catch (NewsExistsException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "4":
                    System.out.print("enter id of the news: ");
                    int id = input.nextInt();
                    News toDeleteNews = database.getNewById(id);
                    manager.removeNews(toDeleteNews);
                    break;

            }
    }
}
}
