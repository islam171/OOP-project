package users;

import academic.Course;
import academic.Lesson;
import main.UniSystem;
import storage.Database;

import java.util.Scanner;

public class Teacher extends Employee {

    private TeacherType teacherType;

    public Teacher(String username, String password, double salary, TeacherType teacherType) {
        super(username, password, salary);
        setTeacherType(teacherType);
    }


    public boolean showCommands() {
        System.out.println("view courses: 1");
        System.out.println("view students: 2");
        System.out.println("View transcript: 3");
        System.out.println("View all lessons: 4");
        System.out.println("View lesson by ID: 5");
        System.out.println("Break: 0");

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
            default: return false;
        }
        return true;

    }

    public void viewCourses(){
        System.out.println("Your courses: ");
        Database database= Database.getInstance();
        for (Course course : database.getCourses()){
            if(course.getInstructor().equals(this)){
                System.out.println(course);
            }
        }
    }

    public void viewStudents(){
        Database database= Database.getInstance();
        System.out.println("Students: ");
        for(User student : database.getStudents()){
            System.out.println(student);
        }
    }

    public void viewLessons(){
        Database database= Database.getInstance();
        System.out.print("Lessons: ");
        for(Lesson lesson : database.getLessons()){
            if(lesson.getCourse().getInstructor().equals(this)){
                System.out.println(lesson);
            }
        }
    }

    public void putMark(Scanner input){

        System.out.print("Enter student ID: ");
        int studentId = input.nextInt();
        System.out.print("Enter lesson ID: ");
        int lessonId = input.nextInt();
        System.out.print("Enter attendance (0/1): ");
        int attendance = input.nextInt();
        System.out.print("Enter mark: ");
        int markValue = input.nextInt();

        // логика добавления оценок

    }

    public void viewTranscript(){

    }

    public void rateTeachers(){

    }

    public TeacherType getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }
}
