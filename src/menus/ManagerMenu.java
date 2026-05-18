package menus;

import academic.Course;
import academic.News;
import academic.RegistrationRequest;
import exceptions.*;
import storage.Database;
import storage.Log;
import users.Manager;
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
                    
                    ====== Manager Menu ======
                    1. manage course
                    2. manage news
                    3. manage registration
                    4. Assign teacher
                    5. View students by GPA
                    6. View students by name
                    7. Generate report
                    8. View all teachers
                    9. Message
                    0. Exit
                    ===========================
                    """;
            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    manageCourse(input, database);
                    break;
                case "2":
                    manageNews(input, database);
                    break;
                case "3":
                    manageRegistration(input, database);
                    break;
                case "4":
                    assignTeacher(input, database);
                    break;
                case "5":
                    manager.viewStudentsByGPA();
                    break;
                case "6":
                    manager.viewStudentsByName();
                    break;
                case "7":
                    manager.generateReport();
                    break;
                case "8":
                    manager.viewTeachers();
                    break;
                case "9":
                    manageMessage(input, database);
                case "0":
                    database.logout();
                    return;
                default:
                    System.out.println("incorrect comm");
            }
        }
    }

    public void manageCourse(Scanner input, Database db) {
        while (true) {
            System.out.print("""
                    1. Add course
                    2. Delete course
                    3. Show all courses
                    0. Cancel
                    """);
            int command = input.nextInt();
            input.nextLine();
            switch (command) {
                case 1:
                    addCourse(input, db);
                    break;
                case 2:
                    deleteCourse(input, db);
                    break;
                case 3:
                    showCourses(input, db);
                    break;
                case 0:
                    return;
                default:
                    return;
            }

        }
    }

    public void addCourse(Scanner input, Database db) {
        System.out.print("Enter course name: ");
        String name = input.nextLine();

        System.out.print("Enter credits: ");
        int credit = Integer.parseInt(input.nextLine());

        Course course = new Course(name, credit);
        manager.addCourse(course);
        System.out.println(db.getCourses().getFirst());
    }

    public void deleteCourse(Scanner input, Database db) {
        System.out.print("Enter course id: ");

        int courseID = input.nextInt();
        input.nextLine();

        Course c = db.getCourses().stream().filter(item -> item.getId() == courseID).findFirst().orElse(null);
        if (c == null) {
            System.out.println("No such course in the Database.");
            return;
        }
        try {
            manager.removeCourse(c);
        } catch (CourseException e) {
            System.out.println("No such course");
        }
    }

    public void showCourses(Scanner input, Database db) {
        List<Course> courses = db.getCourses();
        System.out.print("All courses: \n");
        for (Course course : courses) {
            System.out.print(course + "\n");
        }
    }

    public void manageNews(Scanner input, Database db) {
        while (true) {
            System.out.print("""
                    1. Add news
                    2. Delete news
                    3. Show all news
                    0. Cancel
                    """);
            int command = input.nextInt();
            input.nextLine();
            switch (command) {
                case 1:
                    addNews(input, db);
                    break;
                case 2:
                    removeNews(input, db);
                    break;
                case 3:
                    showNews(db);
                    break;
                case 0:
                    return;
                default:
                    System.out.print("Invalid command. try again");
            }

        }
    }

    public void addNews(Scanner input, Database db) {
        System.out.print("Enter title: ");
        String title = input.nextLine();
        System.out.print("Enter content: ");
        String content = input.nextLine();
        News news = new News(title, content);
        try {
            manager.addNews(news);
        } catch (NewsException e) {
            System.out.print("News already exists \n");
        }
    }

    public void removeNews(Scanner input, Database database) {
        System.out.print("Enter id of the news: ");
        int id = input.nextInt();
        input.nextLine();
        News toDeleteNews = database.getNewById(id);
        manager.removeNews(toDeleteNews);
    }

    public void showNews(Database db) {
        List<News> news = db.getNews();
        if (!news.isEmpty()) {
            System.out.print("All news: \n");
        } else {
            System.out.print("No news \n");
            return;
        }
        for (News n : news) {
            System.out.print(n + "\n");
        }
    }


    public void manageRegistration(Scanner input, Database db) {
        while (true) {
            System.out.print("""
                    1. approve registration
                    2. reject registration
                    3. Show request
                    0. Cancel
                    """);
            int command = input.nextInt();
            input.nextLine();
            switch (command) {
                case 1:
                    approveRegistration(input, db);
                    break;
                case 2:
                    rejectRegistration(input, db);
                    break;
                case 3:
                    showRequest(input, db);
                    break;
                case 0:
                    return;
                default:
                    return;
            }

        }
    }

    public void approveRegistration(Scanner input, Database db) {
        System.out.print("Enter id of the request: ");
        int requestId = input.nextInt();
        input.nextLine();
        RegistrationRequest request = db.getRequestById(requestId);
        if (request == null) {
            System.out.print("Request not found, try again\n");
            return;
        }
        try {
            manager.approveRegistration(request);
        } catch (CourseException e) {
            manager.rejectRegistration(request);
            System.out.print("Student already took this course, Registration was rejected\n");
        }
    }

    public void rejectRegistration(Scanner input, Database db) {
        System.out.print("Enter id of request: ");
        int requestId = input.nextInt();
        input.nextLine();
        RegistrationRequest request = db.getRequestById(requestId);
        if (request == null) {
            System.out.print("Request not found, try again\n");
            return;
        }
        manager.rejectRegistration(request);
        input.nextLine();
    }

    public void showRequest(Scanner input, Database db) {
        List<RegistrationRequest> requests = db.getRequests();
        if (requests.isEmpty()) {
            System.out.print("No request\n");
            return;
        }
        System.out.print("All requests\n");
        for (RegistrationRequest request : requests) {
            System.out.println(request);
        }
    }

    public void assignTeacher(Scanner input, Database db) {

        try {

            System.out.print("Enter id of the teacher: ");
            int idTeacher = input.nextInt();
            Teacher teacher = db.getTeachers().stream().filter(item -> item.getId() == idTeacher).findFirst().orElse(null);
            System.out.print("Enter id of the course: ");
            int idCourse = input.nextInt();
            input.nextLine();
            Course course = db.getCourses().stream().filter(item -> item.getId() == idCourse).findFirst().orElse(null);
            manager.assignTeacher(teacher, course);
            Log log = new Log("Teacher " + teacher.getUsername(), " is assigned to the course " + course.getName());
            db.addLogs(log);
        } catch (TeacherNotFoundException e) {
            System.out.print("Teacher not found. try again\n");

        } catch (CourseException e) {
            System.out.print("Course not found, try again\n");
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
                        manager.viewMessages();
                        break;
                    case 0:
                        return;
                    default:
                        return;
                }

            } catch (PermissionException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    public void sendMessage(Scanner input, Database db) {
        System.out.println("===== Users =====");
        db.getUsers().forEach(u -> System.out.println("| ID: " + u.getId() + " | " + u.getUsername() + "|"));

        System.out.print("Enter recipient ID: ");
        try {
            int userId = Integer.parseInt(input.nextLine().trim());
            var recipient = db.getUserById(userId);

            System.out.print("Enter message: ");
            String text = input.nextLine();

            manager.sendMessage(recipient, text);
            System.out.println("Message sent.");
            Log log = new Log(db.getUser().getUsername(), "sent message");
            db.addLogs(log);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
