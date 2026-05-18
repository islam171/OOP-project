package users;


import academic.Message;
import exceptions.*;
import storage.Database;

import java.util.List;

public class Employee extends User {

    private double salary;

    public Employee(String username, String password, double salary) {
        super(username, password);
        setSalary(salary);
    }

    public String toString() {
        return super.toString();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void sendMessage(User recipient, String text) throws PermissionException, MessageException {
        Database database = Database.getInstance();
        database.sendMessage(this, recipient, text);
    }

    public void viewMessages() throws PermissionException {
        Database database = Database.getInstance();
        List<Message> messages = database.getReceivedMessage(this);
        if (messages.isEmpty()) {
            System.out.print("No message");
            return;
        }
        System.out.println("Messages: \n");
        for (Message m : messages) {
            System.out.println(m);
        }
    }

    public void becomeResearcher() throws ResearcherException, UserException {
        Database database = Database.getInstance();
        if (database.getResearcherByUser(this) != null) {
            throw new ResearcherException("User is already researcher");
        }

        database.makeResearcher(this.getId());

    }

}
