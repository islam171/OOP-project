package exceptions;

public class CourseHasInstructor extends RuntimeException {
    public CourseHasInstructor(String message) {
        super(message);
    }
}
