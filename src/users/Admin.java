package users;


import main.UniSystem;
import storage.Database;
import storage.Log;

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
        System.out.println("Show logs: 5");
        System.out.println("Exit: 0");
        System.out.print("Select command: ");

        Scanner input = new Scanner(System.in);

        int command = input.nextInt();

        switch (command) {
            case 0:
                return false;
            case 1:
                addUser();
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
            case 5:
                this.showLogs();
                break;
            default:
                return false;
        }

        return true;
    }

    public void addUser() {

        Database database= Database.getInstance();
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Adding New User ---");

        System.out.println("Select role: 1-Student, 2-Teacher, 3-Manager");
        System.out.print("Role: ");
        int roleChoice = input.nextInt();
        input.nextLine(); // Очистка буфера


        System.out.print("Enter new ID: ");
        int newId = input.nextInt();
        input.nextLine();

        System.out.print("Enter full name: ");
        String name = input.nextLine();

        System.out.print("Enter password: ");
        String pass = input.nextLine();

        User newUser = null;

        switch (roleChoice) {
            case 1 -> { // Student
                System.out.print("Enter major: ");
                String major = input.next();
                System.out.print("Enter year: ");
                int year = input.nextInt();
                newUser = new Student(name, pass, getSystem(), major, year);
            }
            case 2 -> { // Teacher
                System.out.print("Enter salary: ");
                double salary = input.nextDouble();
                System.out.print("Enter teacher type (1-Tutor, 2-Professor): ");
                int type = input.nextInt();
                TeacherType title = (type == 2) ? TeacherType.PROFESSOR : TeacherType.TUTOR;
                newUser = new Teacher(name, pass, getSystem(), salary, title);
            }
            case 3 -> { // Manager
                System.out.print("Enter salary: ");
                double salary = input.nextDouble();
                System.out.print("Enter manager type (1-OR, 2-Finance): ");
                int type = input.nextInt();
                ManagerType mType = (type == 2) ? ManagerType.FINANCE : ManagerType.OR;
                newUser = new Manager(name, pass, getSystem(), salary, mType);
            }
            default -> System.out.println("Invalid role!");
        }

        // 4. Сохраняем в базу
        if (newUser != null) {
            getSystem().addUser(newUser);
            System.out.println("User added successfully: " + newUser);
        }
    }

    public void showUsers() {
        System.out.print("\n");
        System.out.print("All Users: \n");
        for (User user : getSystem().getUsers()) {
            System.out.print(user + "\n");
        }
    }

    public void removeUser(Scanner input) {
        System.out.print("\n");
        System.out.print("Enter user id: ");
        int id = input.nextInt();
        getSystem().removeUser(id);
    }


    public void updateUser(){
        System.out.print("\n");
        Scanner input = new Scanner(System.in);

        // ATTENTION - check while user id is not correct
        System.out.print("Enter user Id: ");
        int id = input.nextInt();

        User user = getSystem().getUserById(id);
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

        getSystem().updateUser(user, req);
    }

    public void showLogs(){
        System.out.print("System logs: \n");
        for(Log log : getSystem().getLogs()){
            System.out.print(log + "\n");
        }
    }

}
