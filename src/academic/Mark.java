package academic;

import exceptions.MarkWrongException;
import types.MarkType;
import users.Student;

import java.io.Serializable;

public class Mark implements Serializable {

    private double points;
    private Course course;
    private Student student;
    private MarkType markType;

    public Mark(Student student, Course course, double points, MarkType markType) throws MarkWrongException {
        setCourse(course);
        setStudent(student);
        setPoints(points);
        setMarkType(markType);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getPoints() {
        return points;
    }

    private void setPoints(double points) throws MarkWrongException {
        if(points < 0){
            throw new MarkWrongException("");
        }
        this.points = points;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public void setMarkType(MarkType markType) {
        this.markType = markType;
    }

    public String toString(){
        return "Course: " + this.getCourse().getName() + " " + getPoints() + " " + getMarkType();
    }

}
