package users;


import exceptions.UserException;
import storage.Database;
import storage.Log;

import java.util.List;

public class Admin extends Employee {

    public Admin(String username, String password, double salary) {
        super(username, password, salary);
    }

    public void addUser(User user) throws UserException {
        Database database= Database.getInstance();
        database.addUser(user);
    }

    public List<User> getUsers() {
        Database database = Database.getInstance();
        return database.getUsers();
    }

    public void removeUser(int userId) throws UserException {
        Database database = Database.getInstance();
        User user = database.getUserById(userId);
        database.deleteUser(user);
    }

    public void increaseYearOfStudents(){
        Database database = Database.getInstance();
        database.increaseYearOfStudents();
    }

    public List<Log> viewLogs(){
        Database database = Database.getInstance();
        return database.getLogs();
    }

    @Override
    public String toString(){
        return "Admin; " + super.toString();
    }

}
