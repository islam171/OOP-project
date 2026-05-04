package users;

import java.io.Serializable;

public class RatingTeacher implements Serializable {

    private Teacher teacher;
    private Student student;
    private int rating;

    public RatingTeacher(Teacher teacher, Student student, int rating){
        this.rating =rating;
        this.teacher = teacher;
        this.student = student;
    }


    public Teacher getTeacher() {
        return teacher;
    }

    public Student getStudent() {
        return student;
    }

    public int getRating() {
        return rating;
    }
}
