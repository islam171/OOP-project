package academic;

import users.Student;

import java.io.Serializable;

public class RegistrationRequest implements Serializable {
    private int id;
    private Student student;
    private Course course;
    private static int ID = 0;

    public RegistrationRequest(Student student, Course course){
        this.student = student;
        this.course = course;
        this.id = ID++;
    }

    public Student getStudent(){
        return this.student;
    }

    public Course getCourse() {
        return course;
    }

    public int getId() {
        return this.id;
    }
}
