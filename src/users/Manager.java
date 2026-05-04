package users;

import academic.Course;
import academic.Mark;
import academic.News;
import academic.RegistrationRequest;
import storage.Database;
import types.ManagerType;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void addCourse(Course course) {
        Database database = new Database();
        database.addCourse(course);
    }

    public void removeCourse(Course course) {
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

    public void approveRegistration(RegistrationRequest request) {
        Student student = request.getStudent();
        boolean ok = student.getCourses().contains(request.getCourse());
        if (ok) {
            throw new RuntimeException("Student already have this course");
        }
        student.addCourse(request.getCourse());
        Database database = Database.getInstance();
        database.removeRequest(request);
    }

    public void rejectRegistration(RegistrationRequest request) {
        Database database = Database.getInstance();
        database.removeRequest(request);
    }


    public void assignTeacher(Teacher teacher, Course course) {
        if (course.getInstructor() != null) {
            course.setInstructor(teacher);
        }
        throw new RuntimeException("This course already has instructor");
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


    public void generateReport() {
        System.out.println("Average marks by course: ");
        Database database = Database.getInstance();
        Map<Course, List<Mark>> collect = database.getMarks().stream().collect(Collectors.groupingBy(Mark::getCourse));
        for(Map.Entry<Course, List<Mark>> c : collect.entrySet()){
            System.out.print("Course: " + c.getKey().getName());
            double marksByCourse = c.getValue().stream().collect(Collectors.groupingBy(Mark::getStudent, Collectors.summingDouble(Mark::getPoints))).values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            System.out.println("AVG:" + marksByCourse);
        }
    }

    public void viewTeachers() {
        System.out.println("Teachers: ");
        Database database = Database.getInstance();
        for(Teacher teacher : database.getTeachers()){
            System.out.println(teacher);
        }
    }

    public String toString() {
        return "Manager: " + super.toString() + "Manager type: " + getManagerType() + ";";
    }

}
