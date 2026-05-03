package academic;

import users.Student;

public class RegistrationRequest {
    private Student student;
    private Course course;

    public RegistrationRequest(Student student, Course course){
        this.student = student;
        this.course = course;
    }

    public Student getStudent(){
        return this.student;
    }

    public Course getCourse() {
        return course;
    }


}
