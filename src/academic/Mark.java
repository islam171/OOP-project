package academic;

import types.MarkType;
import users.Student;

public class Mark {

    private double points;
    private Course course;
    private Student student;
    private MarkType markType;

    public Mark(Student student, Course course, double points, MarkType markType) {
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

    public void setPoints(double points) {
        this.points = points;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public void setMarkType(MarkType markType) {
        this.markType = markType;
    }
}
