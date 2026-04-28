package users;

import academic.Attendance;
import main.UniSystem;
import academic.Course;
import academic.Lesson;
import academic.Mark;
import java.util.Scanner;
import java.util.Vector;

public class Student extends User {

    private int yearOfStudy;
    private String major;
    private double GPA;
    private int totalCredits = 0;
    private int failsCount = 0;
    private Vector<Course> courses = new Vector<>();

    public Student(String username, String password, UniSystem system, String major, int yearOfStudy) {
        super(username, password, system);
        this.yearOfStudy = yearOfStudy;
        setMajor(major);
    }

    @Override
    public boolean showCommands() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nMenu");
        System.out.println("1 View + register for courses");
        System.out.println("2 View transcript & marks");
        System.out.println("3 View teacher info");
        System.out.println("4 Settings");
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
        Lesson lesson = getSystem().getLessonsById(lessonId);
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
        System.out.println("Available courses: ");
        getSystem().getCourses().forEach(System.out::println);
        System.out.print("Enter course name to register: ");
        String name = input.next();


        for (Course c : getSystem().getCourses()) {
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
}
