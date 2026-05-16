package menus;

import exceptions.AuthUserDoesNotExist;
import exceptions.AuthWrongPassword;
import storage.Database;
import users.Admin;
import users.User;

import java.util.Scanner;

public class MainMenu {

    public void start() {
        while (true) {

            String s = """
                    Welcome to UNI
                    1.Login
                    2.Quit
                    """;
            System.out.print(s);

            Scanner input = new Scanner(System.in);
            String command = input.nextLine();

            if (command.equals("1")) {
                try{
                    login();
                }catch (AuthUserDoesNotExist | AuthWrongPassword e){
                    System.out.println(e.getMessage());
                    continue;
                }
                Database database = Database.getInstance();
                User user = database.getUser();
                if (user == null) {
                    System.out.print("Something went wrong, Please try again\n");
                    continue;
                }
                if(user instanceof Admin) {
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.menu();
                }
            } else if (command.equals("2")) {
                System.out.println("Bye\n");
                break;
            } else {
                System.out.print("Incorrect command\n");
            }
        }
    }

    public void login() throws AuthUserDoesNotExist, AuthWrongPassword {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: \n");
        String username = input.nextLine();

        System.out.print("Enter password: \n");
        String password = input.nextLine();

        Database database = Database.getInstance();
        User user = database.getUserByUsername(username);
        if (user != null) {
            database.login(user, password);
        }else{

            throw new AuthUserDoesNotExist();
        }
    }


}
