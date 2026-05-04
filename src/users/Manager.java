package users;

import academic.Course;
import academic.News;
import academic.RegistrationRequest;
import storage.Database;
import types.ManagerType;

import java.util.List;

public class Manager extends Employee {


    private ManagerType type;

    public Manager(String username, String password, double salary, ManagerType type) {
        super(username, password, salary);
        setManagerType(type);
    }

    public ManagerType getManagerType() {
        return type;
    }

    public void setManagerType(ManagerType type) {
        this.type = type;
    }

    public void approveRegister(RegistrationRequest request) {
        Student student = request.getStudent();
        boolean ok = student.getCourses().contains(request.getCourse());
        if(ok) {
            throw new RuntimeException("Student already have this course");
        }
        student.addCourse(request.getCourse());
        Database database = Database.getInstance();
        database.removeRequest(request);
    }

    public void rejectRequest(RegistrationRequest request){
        Database database = Database.getInstance();
        database.removeRequest(request);
    }


    public void assignTeacher(Teacher teacher, Course course) {
        if(course.getInstructor() != null){
            course.setInstructor(teacher);
        }
        throw new RuntimeException("This course already has instructor");
    }

    public void addCourse(Course course) {
        Database database = new Database();
        database.addCourse(course);
    }

    public void removeCourse(Course course){
        Database database = new Database();
        database.addCourse(course);
    }

    public void addNews(News news) {
        Database database = new Database();
        database.addNews(news);
    }

    public void removeNews(News news) {
        Database database = new Database();
        database.removeNews(news);
    }


    public void viewStudentsByGPA() {
        Database database = Database.getInstance();
        List<Student> students = database.getStudents();

        students.sort(new ComparatorGPA());


        for (Student student : students) {
            System.out.println("Student's name: " + student.getUsername() +
                    "; Student's GPA: " + student.getGPA());

        }

    }

    public void viewStudentsByName() {
        Database database = Database.getInstance();
        List<Student> students = database.getStudents();

        students.sort(new ComparatorName());


        for (Student student : students) {
            System.out.println("Student's name: " + student.getUsername() +
                    " Student's GPA: " + student.getGPA());

        }
    }

    public void generateReport(){
        // todo
    }

    public void viewTeachers(){
        // todo
    }

}
