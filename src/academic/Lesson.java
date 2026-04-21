package academic;


import users.Student;

import java.time.LocalDate;

public class Lesson {
    private Attendance attendance;
    private Mark mark;
    private LocalDate date;
    private Course course;
    private Student student;
    private static int ID = 0;
    private int lessonsId;

    public Lesson(LocalDate date, Course course, Student student){
        setCourse(course);
        setDate(date);
        setStudent(student);
        setLessonsId(ID++);
    }

    public String toString(){
        return "Lessons, ";
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getLessonsId() {
        return lessonsId;
    }

    public void setLessonsId(int lessonsId) {
        this.lessonsId = lessonsId;
    }
}
