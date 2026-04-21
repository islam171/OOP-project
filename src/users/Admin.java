package users;


import main.UniSystem;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String username, String password, UniSystem system) {
        super(username, password, system);
    }

    @Override
    public boolean showCommands() {
            System.out.println("addUser: 1");
            System.out.println("showAllUsers: 2");
            System.out.println("Remove user: 3");
            System.out.println("Update user: 4");
            System.out.println("Break: 0");

            Scanner input = new Scanner(System.in);
            int command = input.nextInt();
            switch (command) {
                case 0:
                    return false;
                case 1:
                    addUser(input);
                    break;
                case 2:
                    showUsers();
                    break;
                case 3:
                    this.removeUser(input);
                    break;
                default: return false;
            }

        return true;
    }

    public void addUser(Scanner input) {
        System.out.println("Enter username:");
        String username = input.next();
        System.out.println("Enter password:");
        String password = input.next();
        System.out.println("Enter credits:");
        int credits = input.nextInt();

        User student = new Student(username, password, this.getSystem(), credits);
        this.getSystem().addUser(student);
    }

    public void showUsers() {
        for (User user : this.getSystem().getUsers()) {
            System.out.println(user);
        }
    }

    public void removeUser(Scanner input) {
        System.out.println("Enter user id: ");
        int id = input.nextInt();
        this.getSystem().removeUser(id);
    }


}
