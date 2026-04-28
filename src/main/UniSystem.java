package main;

import academic.Course;
import academic.Lesson;
import academic.ManagerType;
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

        Database database = new Database();

        Admin admin = new Admin("admin", "admin", getInstance());
        Teacher teacher = new Teacher("islam", "islam", getInstance());
        Student student = new Student("islam1", "islam1", getInstance(), 21);
        Manager manager = new Manager("manager", "qwerty", getInstance(), ManagerType.OR);
        Course course = new Course("Calculus", 6, teacher);
        Course course1 = new Course("PP1", 6, teacher);

        Researcher researcher = new ResearcherDecorator(teacher); // пример как учитель может стать иследователем


        database.addUser(admin);
        database.addUser(admin);
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

        if(logged){
            boolean status = true;

            while(status){
                status = getUser().showCommands();
            }
        }

    }

    public boolean login(String username, String password){
        Database database = new Database();
        for(User user : database.getUsers()){
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    setUser(user);
                    return true;
                }
            }
        }
        return false;
    }


    // auth
    public void logout(){
        setUser(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // users

    public void addUser(User user){
        Database database = new Database();
        database.addUser(user);
    }

    public void removeUser(int id){
        this.getUsers().removeIf(user -> user.getId() == id);
    }

    public List<User> getUsers(){
        Database database = new Database();
        return database.getUsers();
    }


    // courses
    public List<Course> getCourses(){
        Database database = new Database();
        return database.getCourses();
    }

    public void addCourse(Course course){
        Database database = new Database();
        database.addCourse(course);
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
        Database database = new Database();
        return database.getLessons();
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
        Database database = new Database();
        return database.getProjects();
    }

    public void addProject(ResearchProject project){
        Database database = new Database();
        database.addProject(project);
    }


    // news
    public void addNews(News newsItem) {
        Database database = new Database();
        database.addNews(newsItem);
    }

    public void deleteNews(int id) {
        Database database = new Database();
        database.deleteNewById(id);
    }


    public void updateNews(int id, News newPost) {
        Database database = new Database();
        database.updateNews(id, newPost);
    }

}
