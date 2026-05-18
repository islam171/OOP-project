package users;

import academic.Course;
import academic.Lesson;
import exceptions.CourseException;
import exceptions.MarkWrongException;
import exceptions.TeacherException;
import types.MarkType;
import storage.Database;
import types.TeacherType;

import java.rmi.StubNotFoundException;
import java.util.List;

public class Teacher extends Employee {

    private TeacherType teacherType;


    public Teacher(String username, String password, double salary, TeacherType teacherType) {
        super(username, password, salary);
        setTeacherType(teacherType);
    }

    public String toString() {
        return "Teacher: " + super.toString() + " teacher type: " + this.teacherType;
    }

    public TeacherType getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }


    public void viewCourses() {
        Database database = Database.getInstance();
        System.out.println("Your courses: ");
        for (Course course : database.getCourses()) {
            if (course.getInstructor().equals(this)) {
                System.out.println(course);
            }
        }
    }

    public void viewStudents() {
        Database database = Database.getInstance();
        Course course = database.getCourses().stream().filter(item -> item.getInstructor().equals(this)).findFirst().orElse(null);
        if (course == null) {
            System.out.println("Teacher doesn't enrolled to any course");
            return;
        }
        System.out.println("Students: ");
        List<Student> students = database.getStudents().stream().filter(item -> item.getCourses().contains(course)).toList();
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void viewLessons() {
        Database database = Database.getInstance();
        List<Lesson> lessons = database.getLessons();
        if(lessons.isEmpty()){
            System.out.print("No lessons\n");
            return;
        }
        System.out.print("Lessons: \n");
        for (Lesson lesson : database.getLessons()) {
            if (lesson.getCourse().getInstructor().equals(this)) {
                System.out.println(lesson);
            }
        }
    }

    public void putMark(Student student, MarkType markType, double points) throws TeacherException, MarkWrongException, StubNotFoundException, CourseException {
        Database database = Database.getInstance();
        Course course = database.getCourses().stream().filter(item -> {
            if (item.getInstructor() != null)
                return item.getInstructor().equals(this);
            else return false;
        }).findFirst().orElse(null);
        if (course != null) {
            database.putMark(course, student, markType, points);
        } else {
           throw new TeacherException(this.getUsername(), " is not enrolled to any courses");
        }
    }

    public void viewStudentInfo(int id) throws StubNotFoundException {
        Database database = Database.getInstance();
        Student student = database.getStudentById(id);
        if (student != null) {
            System.out.println(student);
            return;
        }
        throw new StubNotFoundException("");
    }

}
