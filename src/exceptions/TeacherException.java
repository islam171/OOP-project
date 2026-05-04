package exceptions;

public class TeacherException extends Exception{

    public TeacherException(String username, String msg){
        super("Teacher " + username + msg);
    }
}
