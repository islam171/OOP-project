package exceptions;

public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String msg){
        super(msg);
    }
}
