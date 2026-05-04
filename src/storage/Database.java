package storage;

import academic.*;
import exceptions.*;
import research.ResearchProject;
import types.MarkType;
import users.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database implements Serializable {

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
    private List<Message> messages = new ArrayList<>();

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

    public void addUser(User user) throws UserExistsException {
        if (this.users.contains(user)) {
            throw new UserExistsException(user.getUsername());
        } else
            this.users.add(user);
    }

    public void deleteUser(User user) throws UserNotFoundException {
        if(this.users.contains(user))
        this.users.remove(user);
        else
            throw new UserNotFoundException(user, "");
    }

    public List<User> getUsers() {
        return this.users;
    }

    public User getUserById(int id) {
        return this.getUsers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }


    // Courses

    public void addCourse(Course course) throws CourseExistsException {
        if (this.courses.contains(course))
            throw new CourseExistsException("There course already exists");
        this.courses.add(course);
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void removeCourse(Course course) throws CourseNotFoundException {
        if(this.courses.contains(course))
        this.courses.remove(course);
        else throw new CourseNotFoundException("Course " + course.getName() + " is not found");
    }

    // Lessons

    public void addLesson(Lesson lesson) throws LessonExistsException {
        if(this.lessons.contains(lesson)){
            throw new LessonExistsException("You can not lesson two times");
        }
        this.lessons.add(lesson);

    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    public Lesson getLessonById(int id) {
        return this.lessons.stream().filter(item -> item.getLessonsId() == id).findFirst().orElse(null);
    }

    // mark

    public List<Mark> getMarks() {
        return this.marks;
    }

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
    public void addTeacherRating(Teacher teacher, Student student, int value) throws RatingTeacherException {
        if (teacher == null || student == null) {
            throw new IllegalArgumentException("Teacher or student cannot be null");
        }


        RatingTeacher ratingTeacher = this.ratingTeachers.stream().filter(item -> item.getStudent().equals(student) && item.getTeacher().equals(teacher)).findFirst().orElse(null);
        if(ratingTeacher != null){
            throw new RatingTeacherException(student.getUsername(), teacher.getUsername(), "");
        }
        this.ratingTeachers.add(new RatingTeacher(teacher, student, value));
    }

    public List<Teacher> getTeachers() {
        return this.users.stream().filter(item -> item instanceof Teacher).map(item -> (Teacher) item).toList();
    }


    // Projects

    public void addProject(ResearchProject project) throws ProjectExistsException {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if(this.projects.contains(project))
            throw new ProjectExistsException("Project" + project.getTopic() + " already exists");
        this.projects.add(project);
    }

    public List<ResearchProject> getProjects() {
        return this.projects;
    }


    // NEWS
    public void addNews(News news) throws NewsExistsException {
        if (news == null) {
            throw new IllegalArgumentException("News cannot be null");
        }

        if(this.news.contains(news)){
            throw new NewsExistsException("News " + news.getTitle() + " with id: " + news.getId() + " already exists");
        }
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
        getStudents().stream().map(item -> (Student)item).forEach(Student::increaseYearOfStudy);
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

    public void removeRequest(RegistrationRequest request) {
        this.requests.remove(request);
    }


    // message
    public void sendMessage(User sender, User recipient, String text) throws PermissionException {
        if (sender instanceof Employee || recipient instanceof Employee) {
            throw new PermissionException("Only employee can send Message");
        }
        this.messages.add(new Message(sender, recipient, text));
    }

    public List<Message> getSentMessages(User user) throws PermissionException {
        if (user instanceof Employee) {
            throw new PermissionException("Only employee can get Message");
        }
        return this.messages.stream().filter(item -> item.getSender().equals(user)).toList();
    }

    public List<Message> getReceivedMessage(User user) throws PermissionException {
        if (user instanceof Employee) {
            throw new PermissionException("Only employee can get Message");
        }
        return this.messages.stream().filter(item -> item.getRecipient().equals(user)).toList();
    }


    // SAVING DATA
    public void loadData() throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream("C:\\\\Users\\\\Islam\\\\Documents\\\\KBTU\\\\OOP-project\\\\data.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        instance = (Database) objectInputStream.readObject();
    }

    public void save() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Islam\\Documents\\KBTU\\OOP-project\\data.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        Database database = Database.getInstance();

        objectOutputStream.writeObject(database);
        objectOutputStream.close();
    }
}

