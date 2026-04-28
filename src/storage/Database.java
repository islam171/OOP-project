package storage;

import academic.Course;
import academic.Lesson;
import academic.News;
import research.ResearchProject;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private static Database instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<Log> logs = new ArrayList<>();

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Users

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(int id) {
        this.users.removeIf(user -> user.getId() == id);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public User getUserById(int id) {
        return this.getUsers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public void updateUser(User user, Request req) {
        if (req.username != null) user.setUsername(req.username);
        if (req.password != null) user.setUsername(req.password);

        if (user instanceof Student) {
            if (req.major != null) ((Student) user).setMajor(req.major);
        } else if (user instanceof Employee) {
            if (req.salary != null) ((Employee) user).setSalary(req.salary);
            if (user instanceof Teacher) {
                if (req.teacherType != null) ((Teacher) user).setTeacherType(req.teacherType);
            } else if (user instanceof Manager)
                if (req.managerType != null) ((Manager) user).setType(req.managerType);
        }
    }

    // Students
    public List<Student> getStudents() {
        return this.users.stream().filter(user -> user instanceof Student).map(user -> (Student) user).collect(Collectors.toList());
    }

    // Courses

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    // Lessons

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    // Projects

    public void addProject(ResearchProject project) {
        this.projects.add(project);
    }

    public List<ResearchProject> getProjects() {
        return this.projects;
    }


    // NEWS
    public void addNews(News news) {
        this.news.add(news);
    }

    public List<News> getNews() {
        return this.news;
    }

    public void deleteNewById(int id) {
        this.news.removeIf(news -> news.id == id);
    }

    public void updateNews(int id, News newPost) {
        this.news = this.news.stream().map(item -> item.getId() == id ? newPost : item).collect(Collectors.toList());
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void addLogs(Log log){
        this.logs.add(log);
    }
}
