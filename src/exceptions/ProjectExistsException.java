package exceptions;

public class ProjectExistsException extends Exception{

    public ProjectExistsException(String msg){
        super("Project " + msg);
    }
}
