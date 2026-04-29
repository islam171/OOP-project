package users;


import main.UniSystem;
import storage.Database;
import storage.Log;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public void addUser(User user) {
        Database database= Database.getInstance();
       database.addUser(user);
    }

    public void showUsers() {
        System.out.print("\n");
        System.out.print("All Users: \n");
        Database database = Database.getInstance();
        for (User user : database.getUsers()) {
            System.out.print(user + "\n");
        }
    }

    public void removeUser(Scanner input) {
        Database database = Database.getInstance();
        System.out.print("\n");
        System.out.print("Enter user id: ");
        int id = input.nextInt();
        database.removeUser(id);
    }


    public void updateUser(){
        System.out.print("\n");
        Scanner input = new Scanner(System.in);

        // ATTENTION - check while user id is not correct
        System.out.print("Enter user Id: ");
        int id = input.nextInt();
        Database database = Database.getInstance();
        User user = database.getUserById(id);
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

        database.updateUser(user, req);
    }

    public void showLogs(){
        Database database = Database.getInstance();
        System.out.print("System logs: \n");
        for(Log log : database.getLogs()){
            System.out.print(log + "\n");
        }
    }

}
