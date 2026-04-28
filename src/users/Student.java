package users;

import main.UniSystem;

import java.util.Scanner;

public class Student extends User{

    private int course;
    private String major;
    private double GPA;

    public Student(String username, String password, UniSystem system, int course) {
        super(username, password, system);
        setCourse(course);
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public boolean showCommands() {
        return true;
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

    @Override
    public void update() {
        Scanner input = new Scanner(System.in);

        int command;
        do {
            System.out.print("Choose commands\n");
            System.out.print("Change username: 1\n");
            System.out.print("Change password: 2\n");
            System.out.print("Change major: 3\n");
            System.out.print("Change course: 4\n");
            System.out.print("Exit: 0\n");
            System.out.print("Enter command: ");
            command = input.nextInt();

            switch (command) {
                case 0-> {
                    break;
                }
                case 1 -> {
                    System.out.print("Enter new username: ");
                    setUsername(input.next());
                }
                case 2 -> {
                    System.out.print("Enter new password: ");
                    setPassword(input.next());
                }
                case 3 -> {
                    System.out.print("Enter new major: ");
                    setMajor(input.next());
                }
                case 4 -> {
                    System.out.print("Enter new course: ");
                    setCourse(input.nextInt());
                }
                default -> {
                    System.out.print("Command is invalid");
                }
            }
        } while (command != 0);
        System.out.print("\n");
    }

}
