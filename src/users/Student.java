package users;

import academic.Attendance;
import main.UniSystem;
import academic.Course;
import academic.Lesson;
import academic.Mark;
import storage.Database;

import javax.xml.crypto.Data;
import java.util.Scanner;
import java.util.Vector;

interface retakes{
     void RetakeCourse();
 }

public class Student extends User implements retakes {
    private int course;
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
    
    public void RetakeCourse(){
        System.out.print("gg guys");
    }
    
    @Override
    public boolean showCommands() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nMenu");
        System.out.println("1 View + register for courses");
        System.out.println("2 View transcript & marks");
        System.out.println("3 View teacher info");
        System.out.println("0 Logout");

        int command = input.nextInt();
        switch (command) {
            case 1 -> manageRegistration(input);
            case 2 -> viewTranscript();
            case 0 -> { return false; }
            default -> System.out.println("Invalid command");
        }
        return true;
    }

    public void registerForCourse(Course c) {
        if (this.totalCredits + c.getCredit() <= 21) {
            this.courses.add(c);
            this.totalCredits += c.getCredit();
            System.out.println("Registered for " + c.getName());
        } else {
            System.out.println("Credit limit 21 exceeded");
        }
    }

    public void setAttendance(int lessonId, boolean isPresent) {
        Database database = Database.getInstance();
        Lesson lesson = database.getLessonById(lessonId);
        if (lesson != null && lesson.getStudent().equals(this)) {
            if (isPresent) {
                lesson.setAttendance(Attendance.ATTENDED);
            } else {
                lesson.setAttendance(Attendance.ABSENT);
            }
        }
    }
    public void addLessonMark(Lesson lesson, int markValue) {
        lesson.setMark(new Mark(markValue));
    }

    private void manageRegistration(Scanner input) {
        Database database = Database.getInstance();
        System.out.println("Available courses: ");
        database.getCourses().forEach(System.out::println);
        System.out.print("Enter course name to register: ");
        String name = input.next();


        for (Course c : database.getCourses()) {
            if (c.getName().equalsIgnoreCase(name)) {
                if (this.totalCredits + c.getCredit() <= 21) {
                    this.courses.add(c);
                    this.totalCredits += c.getCredit();
                    System.out.println("Registered");
                } else {
                    System.out.println("Max 21 credits");
                }
                break;
            }
        }
    }

    public void viewTranscript() {
        System.out.println("Transcript for " + getUsername());
        System.out.println("GPA: " + GPA + " | Fails: " + failsCount);
    }

    public void addFail() {
        this.failsCount++;
        if (this.failsCount > 3) {
            System.out.println("More than 3 fails! High risk of expulsion");
        }
    }


    public int getCourse() {
        return course;
    }
    public void setCourse(int course) {
        this.course = course;
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





}
