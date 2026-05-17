package menus;

import academic.Course;
import academic.Mark;
import academic.News;
import academic.RegistrationRequest;
import exceptions.CourseExistsException;
import exceptions.CourseNotFoundException;
import exceptions.NewsExistsException;
import storage.Database;
import storage.Table;
import users.Manager;
import users.Student;
import users.Teacher;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private Manager manager;

    public ManagerMenu(Manager manager) {
        this.manager = manager;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();
        while (true) {
            String s = """
                    Manager Menu
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
                    12. View all registration request
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
                    System.out.println(database.getCourses().getFirst());
                    break;
                case "2":
                    System.out.print("enter course name: ");

                    String courseName = input.nextLine();

                    Course c = database.getCourses().stream().filter(item -> item.getName().equals(courseName)).findFirst().orElse(null);
                    if (c == null) {
                        System.out.println("No such course in the Database.");
                        break;
                    }
                    try {
                        manager.removeCourse(c);
                    } catch (CourseNotFoundException e) {
                        System.out.println("No such course");
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
                    System.out.print("Enter id of the news: ");
                    int id = input.nextInt();
                    News toDeleteNews = database.getNewById(id);
                    manager.removeNews(toDeleteNews);
                    input.nextLine();
                    break;
                case "5":
                    while (true) {
                        System.out.print("Enter id of the request: ");
                        int requestId = input.nextInt();
                        RegistrationRequest request = database.getRequestById(requestId);
                        if(request == null){
                            System.out.print("Request not found, try again");
                            continue;
                        }

                        try {
                            manager.approveRegistration(request);
                        } catch (CourseExistsException e) {
                            manager.rejectRegistration(request);
                            System.out.print("Student already took this course, Registration rejected");
                            break;
                        }
                        break;
                    }
                    input.nextLine();
                    break;
                case "6":
                    System.out.print("enter id of the Student: ");
                    int studIdToDelete = input.nextInt();
                    Student studToDelete = database.getStudentById(studIdToDelete);
                    System.out.print("enter id of the course: ");
                    int courseIdToDelete = input.nextInt();
                    Course courseRegistryToDelete = database.getCourses().stream().filter(item -> item.getId() == courseIdToDelete).findFirst().orElse(null);
                    manager.rejectRegistration(new RegistrationRequest(studToDelete, courseRegistryToDelete));
                    input.nextLine();
                    break;
                case "7":
                    System.out.print("enter id of the teacher: ");
                    int idTeacher = input.nextInt();
                    Teacher teacher = database.getTeachers().stream().filter(item -> item.getId() == idTeacher).findFirst().orElse(null);
                    System.out.print("enter id of the course");
                    int idCourse = input.nextInt();
                    Course assignCourse = database.getCourses().stream().filter(item -> item.getId() == idCourse).findFirst().orElse(null);
                    assert assignCourse != null;
                    manager.assignTeacher(teacher, assignCourse);
                    input.nextLine();
                    break;
                case "8":
                    manager.viewStudentsByGPA();
                    break;
                case "9":
                    manager.viewStudentsByName();
                    break;
                case "10":
                    manager.generateReport();
                case "11":
                    manager.viewTeachers();
                case "12":
                    List<RegistrationRequest> requests = database.getRequests();
                    try {
                        Table.printTable(requests);
                    } catch (IllegalAccessException e) {
                        System.out.print(e);
                    }
                case "0":
                        database.logout();
                    return;
                default:
                    System.out.println("incorrect comm");
            }
        }
    }
}
