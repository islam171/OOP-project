package storage;

import academic.*;
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
    private List<Mark> marks = new ArrayList<>();
    private List<RatingTeacher> ratingTeachers = new ArrayList<>();
    private List<RegistrationRequest> requests = new ArrayList<>();

    private User user = null;


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // AUTH

    public boolean login(User user, String password) {
        if (user.getPassword().equals(password)) {
            this.user = user;
            return true;
        }
        return false;
    }

    public boolean logout() {
        if (this.user == null) return false;
        this.user = null;
        return true;
    }


    // Users

    public void addUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public User getUserById(int id) {
        return this.getUsers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }


    // Courses

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
    }

    // Lessons

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    public Lesson getLessonById(int id) {
        return this.lessons.stream().filter(item -> item.getLessonsId() == id).findFirst().orElse(null);
    }

    // mark
    public double getMarkOfStudentByCourse(Student student, Course course) {
        return this.marks.stream().filter(item -> item.getStudent().equals(student) && item.getCourse().equals(course)).mapToDouble(Mark::getPoints).sum();
    }

    public List<Mark> getMarksOfStudent(Student student) {
        return this.marks.stream().filter(item -> item.getStudent().equals(student)).collect(Collectors.toList());
    }

    public void putMark(Course course, Student student, MarkType markType, double points) {
        Mark mark = this.marks.stream().filter(item -> item.getStudent().equals(student) && item.getCourse().equals(course) && item.getMarkType() == markType).findFirst().orElse(null);
        if (mark != null) {
            mark.setPoints(points);
            return;
        }
        mark = new Mark(student, course, points, markType);
        this.marks.add(mark);
    }

    // Teachers
    public void addTeacherRating(Teacher teacher, Student student, int value) {
        this.ratingTeachers.add(new RatingTeacher(teacher, student, value));
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

    public void removeNews(News news) {
        this.news.remove(news);
    }

    public News getNewById(int id) {
        return this.news.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public void updateNews(int id, News newPost) {
        this.news = this.news.stream().map(item -> item.getId() == id ? newPost : item).collect(Collectors.toList());
    }

    // logs
    public List<Log> getLogs() {
        return logs;
    }

    public void addLogs(Log log) {
        this.logs.add(log);
    }


    // STUDENTS
    // increease Year of students
    public void increaseYearOfStudents() {
        // increasing YEAR
    }

    public List<Student> getStudents() {
        return this.users.stream().filter(item -> item instanceof Student).map(item -> (Student) item).toList();
    }

    public Student getStudentById(int id) {
        return this.getStudents().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }


    // request
    public void addRequestForRegistration(Student student, Course course) {
        this.requests.add(new RegistrationRequest(student, course));
    }

    public void removeRequest(RegistrationRequest request){
        this.requests.remove(request);
    }
}

