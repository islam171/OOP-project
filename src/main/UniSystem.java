package main;

import academic.Course;
import academic.Lesson;
import storage.Database;
import storage.Log;
import users.ManagerType;
import academic.News;
import research.ResearchProject;
import research.Researcher;
import research.ResearcherDecorator;
import users.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UniSystem {

    private static UniSystem instance; // singleton deign pattern
    private static User user;

    public UniSystem() {

    }

    public static UniSystem getInstance() {
        if (UniSystem.instance == null) {
            UniSystem.instance = new UniSystem();
        }
        return UniSystem.instance;
    }


    static void main() {
        UniSystem university = new UniSystem();
        university.run();
    }

    public void run() {

        Database database = Database.getInstance();

        Admin admin = new Admin("admin", "admin", getInstance());
        Teacher teacher = new Teacher("islam", "islam", getInstance(),10.0, TeacherType.TUTOR);
        Student student = new Student("islam1", "islam1", getInstance(),"IS", 2);
        Manager manager = new Manager("manager", "qwerty", getInstance(),10.0, ManagerType.OR);
        Course course = new Course("Calculus", 6, teacher);
        Course course1 = new Course("PP1", 6, teacher);

        Researcher researcher = new ResearcherDecorator(teacher); // пример как учитель может стать иследователем


        database.addUser(admin);
        database.addUser(teacher);
        database.addUser(student);
        database.addUser(manager);
        database.addCourse(course);
        database.addCourse(course1);


        // авторизуем пользователя
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        boolean logged = login(username, password);


        if (logged) {
            boolean status = true;

            while (status) {
                System.out.print("\n");
                System.out.print("MENU, Hello " + this.getUser().getUsername() + "!\n");
                System.out.print("Choose commands: \n");
                status = getUser().showCommands();
            }
        } else {
            System.out.println("Invalid username or password");
        }

    }

    public boolean login(String username, String password) {
        Database database = Database.getInstance();
        for (User user : database.getUsers()) {
            if(user == null) return false;
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    setUser(user);
                    Log log = new Log(getUser().getUsername(), "User logged in");
                    database.addLogs(log);
                    return true;
                }
            }
        }
        return false;
    }


    // auth
    public void logout() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "User logged out");
        database.addLogs(log);
        setUser(null);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // users

    public void addUser(User user) {
        Database database = Database.getInstance();
        database.addUser(user);
        Log log = new Log(getUser().getUsername(), "Added user");
        database.addLogs(log);
    }

    public void removeUser(int id) {
        this.getUsers().removeIf(user -> user.getId() == id);
    }

    public List<User> getUsers() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got user list");
        database.addLogs(log);
        return database.getUsers();
    }

    public User getUserById(int userId){
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got user by ID");
        database.addLogs(log);
        return database.getUserById(userId);
    }

    public void updateUser(User user, Request req) {
        Database database = Database.getInstance();
        database.updateUser(user, req);
        Log log = new Log(getUser().getUsername(), "Updated user");
        database.addLogs(log);
    }


    // courses
    public List<Course> getCourses() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got list of Courses");
        database.addLogs(log);
        return database.getCourses();
    }

    public void addCourse(Course course) {
        Database database = Database.getInstance();
        database.addCourse(course);
        Log log = new Log(getUser().getUsername(), "Added course");
        database.addLogs(log);
    }


    // students
    public List<Student> getStudents() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got list of users");
        database.addLogs(log);
        return this.getUsers().stream().filter(user -> user instanceof Student).map(user -> (Student) user).collect(Collectors.toList());
    }

    public Student getStudentById(int id) {
        for (Student student : this.getStudents()) {
            if (id == student.getId()) {
                Database database = Database.getInstance();
                Log log = new Log(getUser().getUsername(), "Got user by id");
                database.addLogs(log);
                return student;
            }
        }
        return null;
    }

    // lessons
    public List<Lesson> getLessons() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got list of lessons");
        database.addLogs(log);
        return database.getLessons();
    }

    public Lesson getLessonsById(int id) {
        for (Lesson lesson : this.getLessons()) {
            if (id == lesson.getLessonsId()) {
                Database database = Database.getInstance();
                Log log = new Log(getUser().getUsername(), "Got lesson by Id");
                database.addLogs(log);
                return lesson;
            }
        }
        return null;
    }

    // projects
    public List<ResearchProject> getProjects() {
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got list of projects");
        database.addLogs(log);
        return database.getProjects();
    }

    public void addProject(ResearchProject project) {
        Database database = Database.getInstance();
        database.addProject(project);
        Log log = new Log(getUser().getUsername(), "Added project");
        database.addLogs(log);
    }


    // news
    public void addNews(News newsItem) {
        Database database = Database.getInstance();
        database.addNews(newsItem);
        Log log = new Log(getUser().getUsername(), "Added news");
        database.addLogs(log);
    }

    public void deleteNewsById(int id) {
        Database database = Database.getInstance();
        database.deleteNewById(id);
        Log log = new Log(getUser().getUsername(), "Deleted news");
        database.addLogs(log);
    }


    public void updateNews(int id, News newPost) {
        Database database = Database.getInstance();
        database.updateNews(id, newPost);
        Log log = new Log(getUser().getUsername(), "Updated news");
        database.addLogs(log);
    }

    // logs
    public List<Log> getLogs(){
        Database database = Database.getInstance();
        Log log = new Log(getUser().getUsername(), "Got logs");
        database.addLogs(log);
        return database.getLogs();
    }

}
