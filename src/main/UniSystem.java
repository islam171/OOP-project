package main;

import academic.Course;
import academic.Lesson;
import research.ResearchProject;
import research.Researcher;
import research.ResearcherDecorator;
import users.Admin;
import users.Student;
import users.Teacher;
import users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UniSystem {

    private static UniSystem instance; // singleton deign pattern

    private static final List<User> users = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Lesson> lessons = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    private User user;

    public UniSystem(){

    }

    public static UniSystem getInstance(){
        if(UniSystem.instance == null){
            UniSystem.instance = new UniSystem();
        }
        return UniSystem.instance;
    }


    static void main(){
        UniSystem university = new UniSystem();
        university.run();
    }

    public void run(){

        Admin admin = new Admin("admin", "admin", getInstance());
        addUser(admin);

        Teacher teacher = new Teacher("islam", "islam", getInstance());
        addUser(teacher);
        Student student = new Student("islam1", "islam1", getInstance(), 21);
        addUser(student);

        Course course = new Course("Calculus", 6, teacher);
        addCourse(course);
        Course course1 = new Course("PP1", 6, teacher);
        addCourse(course1);

        Researcher researcher = new ResearcherDecorator(teacher); // пример как учитель может стать иследователем



        // авторизуем пользователя
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        boolean logged = login(username, password);

        if(logged){
            boolean status = true;

            while(status){
                status = getUser().showCommands();
            }
        }

    }

    public boolean login(String username, String password){
        for(User user : users){
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    setUser(user);
                    return true;
                }
            }
        }
        return false;
    }

    public void logout(){
        setUser(null);
    }

    // auth
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // users

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(int id){
        this.getUsers().removeIf(user -> user.getId() == id);
    }

    public List<User> getUsers(){
        return users;
    }


    // courses
    public List<Course> getCourses(){
        return courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }


    // students
    public List<Student> getStudents(){
        return this.getUsers().stream().filter(user -> user instanceof Student).map(user -> (Student)user).collect(Collectors.toList());
    }

    public Student getStudentById(int id){
        for(Student student : this.getStudents()){
            if(id == student.getId()){
                return student;
            }
        }
        return null;
    }

    // lessons
    public List<Lesson> getLessons(){
        return lessons;
    }

    public Lesson getLessonsById(int id){
        for(Lesson lesson : this.getLessons()){
            if(id == lesson.getLessonsId()){
                return lesson;
            }
        }
        return null;
    }

    // projects
    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void addProject(ResearchProject project){
        this.projects.add(project);
    }
}
