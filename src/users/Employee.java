package users;


import main.UniSystem;

import java.util.Scanner;

public class Employee extends User{

    private double salary;

    public Employee(String username, String password, double salary) {
        super(username, password);
        setSalary(salary);
    }

    @Override
    public boolean showCommands() {
        return false;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}
