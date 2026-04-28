package users;


import main.UniSystem;

import java.util.Scanner;

public class Employee extends User{

    private int salary;

    public Employee(String username, String password, UniSystem system) {
        super(username, password, system);
    }

    @Override
    public boolean showCommands() {
        return false;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void update() {
        Scanner input = new Scanner(System.in);

        int command;
        do {
            System.out.print("Choose commands: \n");
            System.out.print("Change username: 1\n");
            System.out.print("Change password: 2\n");
            System.out.print("Change salary: 3\n");
            System.out.print("Enter command: ");
            System.out.print("Exit: 0\n");
            command = input.nextInt();
            switch (command) {
                case 0-> {
                    break;
                }
                case 1 -> {
                    System.out.print("Enter new username: ");
                    setUsername(input.next());
                }
                case 2 -> {
                    System.out.print("Enter new password: ");
                    setPassword(input.next());
                }
                case 3 -> {
                    System.out.print("Enter new salary: ");
                    setSalary(input.nextInt());
                }
                default -> {
                    System.out.print("Command is invalid:");
                }
            }
        } while (command != 0);
        System.out.print("\n");
    }

}
