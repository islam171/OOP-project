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
        System.out.println("Exit: 0");
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
                this.updateUser();
                break;
            default:
                return false;
        }

        return true;
    }

    public void addUser(Scanner input) {
        System.out.print("\n");
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
            System.out.print(user + "\n");
        }
    }

    public void removeUser(Scanner input) {
        System.out.print("\n");
        System.out.print("Enter user id: ");
        int id = input.nextInt();
        this.getSystem().removeUser(id);
    }


    public void updateUser(){
        System.out.print("\n");
        Scanner input = new Scanner(System.in);

        // ATTENTION - check while user id is not correct
        System.out.print("Enter user Id: ");
        int id = input.nextInt();

        User user = this.getSystem().getUserById(id);
        if (user == null) {
            System.out.print("User is not found");
        }

        System.out.print("\n");
        System.out.print(user);
        System.out.print("\n");

        if (user instanceof Admin) {
            System.out.print("Admin can't change admin");
            return;
        }

        Request req = new Request();

        int command;
        do {

            System.out.print("""     
                    
                    Change username: 1
                    Change password: 2
                    """);

            if (user instanceof Student) {
                System.out.print(""" 
                        Change major: 3
                        Change year: 4""");
            } else if (user instanceof Employee) {
                System.out.print("Change salary: 3\n");

                if (user instanceof Teacher) {
                    System.out.print("Change teacher type: 4");
                } else if (user instanceof Manager) {
                    System.out.print("Change manager type: 4\n");
                }
            }
            System.out.print("Exit to menu: 0\n");
            System.out.print("Select command: ");
            command = input.nextInt();
            System.out.print("\n");
            switch (command) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    req.username = input.next();
                }
                case 2 -> {
                    System.out.print("Enter password: ");
                    req.password = input.next();
                }
                case 3 -> {
                    if (user instanceof Student) {
                        System.out.print("Enter major: ");
                        req.major = input.next();
                    } else if (user instanceof Employee) {
                        System.out.print("Enter salary: ");
                        req.salary = input.nextDouble();
                    }
                }
                case 4 -> {
                    if (user instanceof Student) {
                        System.out.print("Enter year: ");
                        req.year = input.nextInt();
                    } else if (user instanceof Teacher) {
                        System.out.print("Enter salary: ");
                        // teacher type
                    } else if (user instanceof Manager) {
                        System.out.print("Enter salary: ");
                        // manger type
                    }
                }
                default -> {
                    break;
                }
            }
        } while (command != 0);
        this.getSystem().updateUser(user, req);

    }

}
