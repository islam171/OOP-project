package users;

import exceptions.*;
import types.Attendance;
import academic.Course;
import academic.Lesson;
import academic.Mark;
import storage.Database;

import java.util.List;
import java.util.Vector;


public class Student extends User {

    private int yearOfStudy;
    private String major;
    private double GPA;
    private int totalCredits = 0;
    private int failsCount = 0;
    private String specialty;
    private Vector<Course> courses = new Vector<>();

    public Student(String username, String password, String specialty, int yearOfStudy) {
        super(username, password);
        this.yearOfStudy = yearOfStudy;
        this.specialty = specialty;
    }


    public void registerForCourse(Course c) {
        Database database = Database.getInstance();
        if (this.totalCredits + c.getCredit() <= 21) {
            database.addRequestForRegistration(this, c);
            System.out.println("Created register request for " + c.getName());
        } else {
            System.out.println("Credit limit 21 exceeded");
        }
    }

    public void setAttendance(int lessonId) {
        Database database = Database.getInstance();
        Lesson lesson = database.getLessonById(lessonId);
        if (lesson != null && lesson.getStudent().equals(this)) {
            lesson.setAttendance(Attendance.ATTENDED);
        }
    }

    public void viewTranscript() {
        System.out.println("Transcript for " + getUsername());
        System.out.println("GPA: " + getGPA() + " | Fails: " + failsCount);
    }

    public void rateTeacher(Teacher teacher, int rating) throws RatingTeacherException, StudentCourseException {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Database database = Database.getInstance();
        if (this.getCourses().stream().noneMatch(item -> item.getInstructor().equals(teacher))) {
            throw new StudentCourseException("");
        }
        database.addTeacherRating(teacher, this, rating);
    }

    public void viewTeacher(Course course) {
        if (this.courses.contains(course)) {
            System.out.print(course.getInstructor());
            return;
        }
        throw new RuntimeException("Student is not enrolled to this course");
    }

    public void addFail() {
        this.failsCount++;

    }

    public int getFailsCount(){
        return this.failsCount;
    }

    public void viewCourse() {
        for (Course c : this.courses) {
            System.out.println(c);
        }
    }

    public List<Mark> viewMarks() {
        Database database = Database.getInstance();
        return database.getMarksOfStudent(this);
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }


    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public void increaseYearOfStudy() {
        this.setYearOfStudy(this.getYearOfStudy() + 1);
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Vector<Course> getCourses() {
        return this.courses;
    }

    public void addCourse(Course course) throws CourseException {
        if (!this.courses.contains(course))
            this.courses.add(course);
        else
            throw new CourseException("Student already has this course");
    }

    public void becomeResearcher() throws ResearcherException, UserException {
        Database database = Database.getInstance();
        if (database.getResearcherByUser(this) != null) {
            throw new ResearcherException("User is already researcher");
        }
        database.makeResearcher(this.getId());
    }

    public String toString(){
        return "Student; " + super.toString();
    }
}
