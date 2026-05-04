package users;

import academic.Course;
import academic.Lesson;
import types.MarkType;
import storage.Database;
import types.TeacherType;

import java.util.List;

public class Teacher extends Employee {

    private TeacherType teacherType;


    public Teacher(String username, String password, double salary, TeacherType teacherType) {
        super(username, password, salary);
        setTeacherType(teacherType);
    }

    public String toString(){
        return "Teacher: " + super.toString() + "; teacher type: " + this.teacherType;
    }

    public TeacherType getTeacherType() {
        return teacherType;
    }
    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }


    public void viewCourses(){
        Database database = Database.getInstance();
        System.out.println("Your courses: ");
        for (Course course : database.getCourses()){
            if(course.getInstructor().equals(this)){
                System.out.println(course);
            }
        }
    }

    public void viewStudents(){
        Database database = Database.getInstance();
        Course course = database.getCourses().stream().filter(item -> item.getInstructor().equals(this)).findFirst().orElse(null);
        if(course == null){
            System.out.println("Teacher doesn't enrolled to any course");
            return;
        }
        System.out.println("Students: ");
        List<Student> students = database.getStudents().stream().filter(item -> item.getCourses().contains(course)).toList();
        for(Student s : students){
            System.out.println(s);
        }
    }

    public void viewLessons(){
        Database database = Database.getInstance();
        System.out.print("Lessons: ");
        for(Lesson lesson : database.getLessons()){
            if(lesson.getCourse().getInstructor().equals(this)){
                System.out.println(lesson);
            }
        }
    }

    public void putMark(Student student, MarkType markType, double points){
        Database database = Database.getInstance();
        Course course = database.getCourses().stream().filter(item -> item.getInstructor().equals(this)).findFirst().orElse(null);
        if(course != null){
            database.putMark(course, student, markType, points);
        }else{
            throw new RuntimeException("Teacher doesn't enrolled to any course");
        }
    }

    public void viewStudentInfo(int id){
        Database database = Database.getInstance();
        Student student = database.getStudentById(id);
        if(student != null){
            System.out.println(student);
            return;
        }
        throw new IllegalArgumentException("There is no student with this id");
    }

}
