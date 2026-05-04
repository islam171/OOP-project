package exceptions;

public class LessonExistsException extends Exception{

    public LessonExistsException(String msg){
        super("This lesson already exists; " + msg);
    }

}
