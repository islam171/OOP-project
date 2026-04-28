package users;


import main.UniSystem;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String username, String password, UniSystem system) {
        super(username, password, system);
    }

    @Override
    public boolean showCommands() {

        System.out.print("Admin's commands: \n");
        System.out.println("addUser: 1");
        System.out.println("showAllUsers: 2");
        System.out.println("Remove user: 3");
        System.out.println("Update user: 4");
        System.out.println("Break: 0");
        System.out.print("Select command: ");
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
            case 4:
                this.updateUser(input);
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public void update() {

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
        System.out.print("\n");
        System.out.print("All Users: \n");
        for (User user : this.getSystem().getUsers()) {
            System.out.println(user);
        }
        System.out.print("\n");
    }

    public void removeUser(Scanner input) {
        System.out.print("\n");
        System.out.println("Enter user id: ");
        int id = input.nextInt();
        this.getSystem().removeUser(id);
        System.out.print("\n");
    }

    public void updateUser(Scanner input) {
        System.out.print("Enter user Id: ");
        int id = input.nextInt();
        User user = this.getSystem().getUsers().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        if (user != null) {

            if (user instanceof Admin) {
                System.out.print("Admin can't change admin\n");
                return;
            }
            // show user
            System.out.print("\n");
            System.out.print(user);
            System.out.print("\n");
            user.update();
        }
    }




}
