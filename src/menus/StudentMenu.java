package menus;

import academic.Course;
import academic.Mark;
import research.ResearcherDecorator;
import storage.Database;
import storage.Table;
import users.Student;
import users.Teacher;

import javax.xml.crypto.Data;
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
            boolean isResearcher = database.getResearcherByUser(this.student) != null;
            String s = """
                    
                    ====== Student Menu ======
                    1. View courses
                    2. View marks
                    3. View transcript
                    4. Register for course
                    5. Mark attendance
                    6. View teacher
                    7. Rate teacher
                    8. Retake course
                    """ +
                        (isResearcher ?
                        "9. Researcher menu\\n" :
                        "9. Become Researcher\\n") +
                    """
                    0. Exit
                    ===========================
                    """;

            System.out.print(s);
            String command = input.nextLine();

            switch (command) {
                case "1":
                    student.viewCourse();
                    break;
                case "2":
                    viewMarks();
                    break;
                case "3":
                    student.viewTranscript();
                    break;
                case "4":
                    while (true) {
                        System.out.print("Enter course id: ");
                        int courseId = input.nextInt();
                        input.nextLine();

                        Course course = database.getCourses().stream().filter(item -> item.getId() == courseId).findFirst().orElse(null);
                        if (course == null) {
                            System.out.print("Course not found, try again");
                            continue;
                        }
                        student.registerForCourse(course);
                        break;
                    }
                    break;
                case "5":
                    System.out.print("Enter lesson id: ");
                    int lessonId = Integer.parseInt(input.nextLine());
                    student.setAttendance(lessonId);
                    break;
                case "6":
                    student.viewCourse();
                    System.out.print("Enter course name: ");
                    String courseName = input.nextLine();

                    for (Course c : student.getCourses()) {
                        if (c.getName().equalsIgnoreCase(courseName)) {
                            student.viewTeacher(c);
                            break;
                        }
                    }
                    break;
                case "7":
                    while(true){
                        Database db = Database.getInstance();

                        System.out.print("Enter teacher username: ");
                        String teacherName = input.nextLine();

                        System.out.print("Enter rating (1-5): ");
                        int rating = Integer.parseInt(input.nextLine());
                        if(rating < 0 || rating > 5 ){
                            System.out.print("invalid value\n");
                            continue;
                        }

                        Teacher teacher = (Teacher) db.getUserByUsername(teacherName);

                        try {
                            student.rateTeacher(teacher, rating);
                            System.out.println("Teacher rated");
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + " try again");
                        }
                    }
                    break;
                case "8":
                    student.retakeCourse();
                    break;
                case "9":
                    if (isResearcher) {
                        ResearcherDecorator rd = database.getResearcherByUser(this.student);
                        new ResearcherMenu(rd).menu();
                    } else {
                        becomeResearcher(input, database);
                    }
                case "0":
                    return;
                default:
                    System.out.println("incorrect comm");
            }
        }
    }

    public void viewMarks(){
        List<Course> courses = student.getCourses();
        if(courses.isEmpty()){
            System.out.print("No course\n");
            return;
        }
        for (Course c : courses) {
            System.out.print(c.getName() + "\n");
            List<Mark> marks = student.viewMarks();
            if (marks.isEmpty()) {
                System.out.println("no marks");
            } else {
                for (Mark m : marks.stream().filter(item -> item.getCourse().equals(c)).toList()) {
                    System.out.print(m + "\n");
                }
            }
        }
    }

    private void becomeResearcher(Scanner input, Database database) {
        System.out.print("Confirm becoming a researcher? (yes/no): ");
        String confirm = input.nextLine().trim();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        try {
            database.makeResearcher(this.student.getId());
            System.out.println("You are now registered as a Researcher!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
