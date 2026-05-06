package exceptions;

public class RatingTeacherException extends Exception {

    public RatingTeacherException(String studentName, String teacherName, String msg) {
        super("Student " + studentName + " already rate " + teacherName + " " + msg);
    }

    public RatingTeacherException() {
        super("Student does not have this teacher");
    }

}
