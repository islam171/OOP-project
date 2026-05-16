package menus;

import academic.Course;
import academic.Lesson;
import storage.Database;
import types.MarkType;
import users.Student;
import users.Teacher;

import java.util.List;
import java.util.Scanner;

public class TeacherMenu {

    private Teacher teacher;

    public TeacherMenu(Teacher teacher) {
        this.teacher = teacher;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);

        while (true) {
            String s = """
                    TeacherMenu
                    -->
                    1. View my courses
                    2. View students
                    3. View lessons
                    4. Put mark
                    5. View student info
                    0. Exit
                    <---
                    """;

            System.out.print(s);
            String command = input.nextLine();

            switch (command) {

                case "1":
                    teacher.viewCourses();
                    break;

                case "2":
                    teacher.viewStudents();
                    break;

                case "3":
                    teacher.viewLessons();
                    break;

                case "4":
                    try {
                        Database db = Database.getInstance();

                        System.out.print("student id: ");
                        int studentId = Integer.parseInt(input.nextLine());

                        Student student = db.getStudentById(studentId);

                        if (student == null) {
                            System.out.println("student not found");
                            break;
                        }

                        System.out.print("mark type (EXAM / MIDTERM / FINAL): ");
                        String typeStr = input.nextLine();
                        MarkType markType = MarkType.valueOf(typeStr.toUpperCase());

                        System.out.print("points: ");
                        double points = Double.parseDouble(input.nextLine());

                        teacher.putMark(student, markType, points);
                        System.out.println("mark added");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "5":
                    System.out.print("enter student id: ");
                    int id = Integer.parseInt(input.nextLine());

                    try {
                        teacher.viewStudentInfo(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("wrong comm");
            }
        }
    }
}
