package users;


import academic.Message;
import exceptions.PermissionException;
import main.UniSystem;
import storage.Database;

import java.util.List;
import java.util.Scanner;

public class Employee extends User{

    private double salary;

    public Employee(String username, String password, double salary) {
        super(username, password);
        setSalary(salary);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void sendMessage(Employee recipient, String text) throws PermissionException {
        Database database = Database.getInstance();
        database.sendMessage(this, recipient, text);
    }

    public void viewMessages() throws PermissionException {
        Database database = Database.getInstance();
        List<Message> messages = database.getReceivedMessage(this);
        System.out.println("Messages:");
        for(Message m : messages){
            System.out.println(m);
        }
    }
}
