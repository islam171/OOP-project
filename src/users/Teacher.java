package users;

import academic.Course;
import academic.Lesson;
import main.UniSystem;

import java.util.Scanner;

public class Teacher extends Employee {
    private TeacherType teacherType;
    public Teacher(String username, String password, UniSystem system) {
        super(username, password, system);
        setTeacherType(teacherType);
    }

    public TeacherType getTeacherType() {
        return teacherType;
    }
    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }
//fully implemented sC
    public boolean showCommands() {
        System.out.println("view courses 1");
        System.out.println("view students 2");
        System.out.println("View transcript 3");
        System.out.println("View all lessons 4");
        System.out.println("View lesson by ID 5");
        System.out.println("Break 0");

        Scanner input = new Scanner(System.in);
        int command = input.nextInt();
        switch (command) {
            case 0:
                return false;
            case 1:
                viewCourses();
                break;
            case 2:
                viewStudents();
                break;
            case 3:
                viewTranscript();
            case 4:
                viewLessons();
            case 5:
                viewLessonById(input);
                break;

        }
        return true;

    }

    public void viewCourses() {
        System.out.println("Your courses ");
        for (Course c : getSystem().getCourses()) {
            if (c.getInstructor() != null && c.getInstructor().equals(this)) {
                System.out.println(c);
            }
        }
    }
    //added view lessons by id
    public void viewLessonById(Scanner input) {
        System.out.print("Enter lesson ID ");
        int lessonId = input.nextInt();
        Lesson l = getSystem().getLessonsById(lessonId);
        if (l == null) {
            System.out.println("Lesson with ID " + lessonId + " not found.");
            return;
        }
        if (!l.getCourse().getInstructor().equals(this)) {
            System.out.println("Access denied");
            return;
        }

        System.out.println("Lesson Details ");
        System.out.println(l);
    }
    public void viewStudents(){
        System.out.println("Students ");
        for(User student : this.getSystem().getStudents()){
            System.out.println(student);
        }
    }

    public void viewLessons(){
        System.out.print("Lessons ");
        for(Lesson lesson : this.getSystem().getLessons()){
            if(lesson.getCourse().getInstructor().equals(this)){
                System.out.println(lesson);
            }
        }
    }
//changed putmark
    public void putMark(Scanner input){

        System.out.print("Enter student ID: ");
        int studentId = input.nextInt();
        System.out.print("Enter lesson ID: ");
        int lessonId = input.nextInt();
        System.out.print("Enter attendance (0/1): ");
        int attendance = input.nextInt();
        System.out.print("Enter mark: ");
        int markValue = input.nextInt();

        Student s = getSystem().getStudentById(studentId);
        Lesson l = getSystem().getLessonsById(lessonId);
        if (s == null) {
            System.out.println("ID " + studentId + " not found.");
            return;
        }
        if (l == null) {
            System.out.println("ID " + lessonId + " not found.");
            return;
        }
        s.setAttendance(lessonId, attendance == 1);
        s.addLessonMark(l, markValue);
        if (s != null && l != null) {
            if (l.getStudent().equals(s)) {

                s.setAttendance(lessonId, attendance == 1);
                s.addLessonMark(l, markValue);

                System.out.println("Data updated for student " + s.getUsername());
            } else {
                System.out.println("Lesson ID " + lessonId + " does not belong to student " + studentId);
            }
        } else {
            System.out.println("Student or Lesson not found!");
        }
    }

    public void viewTranscript(){

    }

    public void rateTeachers(){

    }
    @Override
    public void update() {
        System.out.println("Teacher " + getUsername() + " has updates.");
    }
}
