package menus;

import academic.Course;
import academic.Mark;
import storage.Database;
import storage.Table;
import users.Student;
import users.Teacher;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {

    private Student student;

    public StudentMenu(Student student) {
        this.student = student;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();

        while (true) {
            String s = """
                    StudentMenu
                    -->
                    1. View courses
                    2. View marks
                    3. View transcript
                    4. Register for course
                    5. Mark attendance
                    6. View teacher
                    7. Rate teacher
                    8. Retake course
                    0. Exit
                    <--
                    """;

            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    student.viewCourse();
                    break;
                case "2":
                    List<Mark> marks = student.viewMarks();
                    if (marks.isEmpty()) {
                        System.out.println("no marks");
                    } else {
                        for (Mark m : marks) {
                            System.out.println(m);
                        }
                    }
                    break;
                case "3":
                    student.viewTranscript();
                    break;
                case "4":
                    while(true){

                        List<Course> courses = database.getCourses();

                        try {
                            Table.printTable(courses);

                        } catch (IllegalAccessException e) {
                            System.out.print(e);
                        }

                        System.out.print("enter course id: ");
                        int courseId = input.nextInt();

                        Course course = database.getCourses().stream().filter(item -> item.getId() == courseId).findFirst().orElse(null);
                        if(course == null){
                            System.out.print("Course not found, try again");
                            continue;
                        }

                        student.registerForCourse(course);
                        input.nextLine();
                        break;
                    }

                    break;

                case "5":
                    System.out.print("enter lesson id: ");
                    int lessonId = Integer.parseInt(input.nextLine());
                    student.setAttendance(lessonId);
                    break;
                case "6":
                    student.viewCourse();
                    System.out.print("enter course name: ");
                    String courseName = input.nextLine();

                    for (Course c : student.getCourses()) {
                        if (c.getName().equalsIgnoreCase(courseName)) {
                            student.viewTeacher(c);
                            break;
                        }
                    }
                    break;
                case "7":
                    Database db = Database.getInstance();

                    System.out.print("enter teacher username: ");
                    String teacherName = input.nextLine();

                    System.out.print("enter rating (1-5): ");
                    int rating = Integer.parseInt(input.nextLine());

                    Teacher teacher = (Teacher) db.getUserByUsername(teacherName);

                    try {
                        student.rateTeacher(teacher, rating);
                        System.out.println("teacher rated");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "8":
                    student.retakeCourse();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("incorrect comm");
            }
        }
    }
}
