package users;

import academic.ManagerType;
import main.UniSystem;

public class Manager extends Employee {

    private ManagerType type;

    public Manager(String username, String password, UniSystem system, ManagerType type) {
        super(username, password, system);
        setType(type);
    }

    @Override
    public boolean showCommands() {
        return true;
    }

    public void addCourse(){
        // add course
    }

    public void assignTeacher(){
        // add teacher on course
    }

    public void approveRegister(){

    }

    public void addNews(){

    }

    public void removeNews(){

    }

    public void updateNews(){

    }

    public void viewStudentsByGPA(){

    }

    public void viewStudentsByName(){

    }


    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }
}
