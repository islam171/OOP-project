package storage;

import academic.*;
import exceptions.*;
import research.ResearchProject;
import research.ResearcherDecorator;
import types.MarkType;
import types.TeacherType;
import users.*;

import java.io.*;
import java.rmi.StubNotFoundException;
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
    private List<ResearcherDecorator> researchers = new ArrayList<>();

    private static final String FILE_PATH = "data.ser";

    private User user = null;


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // AUTH

    public void login(User user, String password) throws AuthWrongPassword {

        if (!user.getPassword().equals(password)) {
            throw new AuthWrongPassword();
        }
        this.user = user;
    }

    public boolean logout() {
        if (this.user == null) return false;
        this.user = null;
        return true;
    }

    public User getUser() {
        return this.user;
    }

    public User getUserByUsername(String username) {
        return this.users.stream().filter(item -> item.getUsername().equals(username)).findFirst().orElse(null);
    }


    // Users

    public void addUser(User user) throws UserExistsException, UserNotFoundException {
        if (this.users.contains(user)) {
            throw new UserExistsException(user.getUsername());
        } else {
            this.users.add(user);
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                if (teacher.getTeacherType() == TeacherType.PROFESSOR) {
                    try {
                        makeResearcher(teacher.getId());
                    } catch (CantBeResearcherException e) {

                    }
                }
            }
        }
    }

    public void deleteUser(User user) throws UserNotFoundException {
        if (user != null && this.users.contains(user)) this.users.remove(user);
        else throw new UserNotFoundException("");
    }

    public List<User> getUsers() {
        return this.users;
    }

    public User getUserById(int id) {
        return this.getUsers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }


    // Courses

    public void addCourse(Course course) throws CourseExistsException {
        if (this.courses.contains(course)) throw new CourseExistsException("There course already exists");
        this.courses.add(course);
    }

    public List<Course> getCourses() {
        return this.courses;
    }


    public void removeCourse(Course course) throws CourseNotFoundException {
        if (this.courses.contains(course)) this.courses.remove(course);
        else throw new CourseNotFoundException("Course " + course.getName() + " is not found");
    }

    // Lessons

    public void addLesson(Lesson lesson) throws LessonExistsException {
        if (this.lessons.contains(lesson)) {
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


    public void putMark(Course course, Student student, MarkType markType, double points) throws MarkWrongException, StubNotFoundException, StudentCourseException {
        if (student == null) {
            throw new StubNotFoundException("Student not found");
        }
        Mark mark = this.marks.stream().filter(item -> item.getStudent().equals(student) && item.getCourse().equals(course) && item.getMarkType() == markType).findFirst().orElse(null);
        if (mark != null) {
            throw new MarkWrongException("Impossible to change the mark");
        }

        if (student.getCourses().contains(course)) {
            mark = new Mark(student, course, points, markType);
        } else {
            throw new StudentCourseException("Student " + student.getUsername() + " is not assigned " + course.getName());
        }
        this.marks.add(mark);
        student.setGPA(calculateGPA(student));
    }

    public double calculateGPA(Student student) {
        List<Course> courses = student.getCourses();
        double credist = 0.0;
        double grades = 0.0;

        for (Course course : courses) {
            double point = getMarkOfStudentByCourse(student, course);

            double grade = 0.0;
            if (point >= 94.5 && point <= 100) {
                grade = 4.0;
            } else if (point >= 89.5 && point < 94.5) {
                grade = 3.67;
            } else if (point >= 84.5 && point < 89.5) {
                grade = 3.33;
            } else if (point >= 79.5 && point < 84.5) {
                grade = 3.0;
            } else if (point >= 74.5 && point < 79.5) {
                grade = 2.67;
            } else if (point >= 69.5 && point < 74.5) {
                grade = 2.33;
            } else if (point >= 64.5 && point < 69.5) {
                grade = 2.0;
            } else if (point >= 59.5 && point < 64.5) {
                grade = 1.67;
            } else if (point >= 54.5 && point < 59.5) {
                grade = 1.33;
            } else if (point >= 49.5 && point < 54.5) {
                grade = 1.00;
            } else if (point >= 0 && point < 49.5) {
                grade = 0.0;
            } else {
                System.out.print("Value of mark wrong");
                return 0;
            }
            grades += grade * course.getCredit();
            credist += course.getCredit();
        }
        if (credist <= 0) return 0;
        return grades / credist;

    }


    // Teachers
    public void addTeacherRating(Teacher teacher, Student student, int value) throws RatingTeacherException {
        if (teacher == null || student == null) {
            throw new IllegalArgumentException("Teacher or student cannot be null");
        } else if (student.getCourses().stream().noneMatch(c -> c.getInstructor().equals(teacher))) {
            throw new RatingTeacherException();
        }

        RatingTeacher ratingTeacher = this.ratingTeachers.stream().filter(item -> item.getStudent().equals(student) && item.getTeacher().equals(teacher)).findFirst().orElse(null);
        if (ratingTeacher != null) {
            throw new RatingTeacherException(student.getUsername(), teacher.getUsername(), "");
        }

        this.ratingTeachers.add(new RatingTeacher(teacher, student, value));
    }

    public List<Teacher> getTeachers() {
        return this.users.stream().filter(item -> item instanceof Teacher).map(item -> (Teacher) item).toList();
    }

    // manager
    public List<Manager> getManagers() {
        return this.users.stream().filter(item -> item instanceof Manager).map(item -> (Manager) item).toList();
    }


    // Projects

    public void addProject(ResearchProject project) throws ProjectExistsException {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (this.projects.contains(project))
            throw new ProjectExistsException("Project" + project.getTopic() + " already exists");
        this.projects.add(project);
    }

    public List<ResearchProject> getProjects() {
        return this.projects;
    }

    public ResearcherDecorator getResearcherByUser(User user) {
        return this.researchers.stream().filter(item -> item.getUser().getId() == user.getId()).findFirst().orElse(null);
    }

    public void makeResearcher(int userId) throws UserNotFoundException, CantBeResearcherException {
        User user = this.users.stream().filter(item -> item.getId() == userId).findFirst().orElse(null);
        if (user == null) {
            throw new UserNotFoundException("");
        }
        ResearcherDecorator alreadyResearcher = getResearcherByUser(user);
        if (alreadyResearcher != null) {
            System.out.print("User is already researcher\n");
            return;
        }
        ResearcherDecorator researcherDecorator = new ResearcherDecorator(user);
        this.researchers.add(researcherDecorator);
    }


    // NEWS
    public void addNews(News news) throws NewsExistsException {
        if (news == null) {
            throw new IllegalArgumentException("News cannot be null");
        }

        if (this.news.contains(news)) {
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
        getStudents().stream().map(item -> (Student) item).forEach(Student::increaseYearOfStudy);
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

    public List<RegistrationRequest> getRequests() {
        return this.requests;
    }

    public RegistrationRequest getRequestById(int requestId) {
        return this.requests.stream().filter(item -> item.getId() == requestId).findFirst().orElse(null);
    }

    // message
    public void sendMessage(User sender, User recipient, String text) throws PermissionException {
        if (!(sender instanceof Employee && recipient instanceof Employee)) {
            throw new PermissionException("Only employee can send or get the message");
        }
        if (sender.equals(recipient)) {
            throw new PermissionException("User can't send message to other itself");
        }

        this.messages.add(new Message(sender, recipient, text.trim()));
    }

    public List<Message> getReceivedMessage(User user) throws PermissionException {
        if (!(user instanceof Employee)) {
            throw new PermissionException("Only employee can get Message");
        }
        return this.messages.stream().filter(item -> item.getRecipient().equals(user)).toList();
    }


    // SAVING DATA
    public void loadData() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.print("No data saved found");
            return;
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Database loaded = (Database) objectInputStream.readObject();
            this.users = loaded.users;
            this.courses = loaded.courses;
            this.lessons = loaded.lessons;
            this.projects = loaded.projects;
            this.news = loaded.news;
            this.logs = loaded.logs;
            this.marks = loaded.marks;
            this.ratingTeachers = loaded.ratingTeachers;
            this.requests = loaded.requests;
            this.messages = loaded.messages;
            this.researchers = loaded.researchers;
            System.out.println("Data loaded successfully.");
        }

    }

    public void save() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(this);
            System.out.print("Data saved");
        }
    }


}

