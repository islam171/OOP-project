package menus;

import exceptions.CantBeResearcherException;
import exceptions.PermissionException;
import exceptions.UserNotFoundException;
import research.ResearcherDecorator;
import storage.Database;
import users.Employee;
import users.User;

import java.util.Scanner;

public class EmployeeMenu {

    Employee employee;

    public EmployeeMenu(Employee employee) {
        this.employee = employee;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();

        while (true) {
            try {
                boolean isResearcher = database.getResearcherByUser(this.employee) != null;
                System.out.println("""
                        
                        ====== Employee Menu ======
                        1. Send message
                        2. Read messages
                        3. View news
                        ===========================
                        """ +
                        (isResearcher ?
                                "4. Researcher menu\n" :
                                "4. Become Researcher\n") +
                        """
                        0. Logout
                        """);
                String command = input.nextLine();

                switch (command) {
                    case "1" -> sendMessage(input, database);
                    case "2" -> this.employee.viewMessages();
                    case "3" -> viewNews(database);
                    case "4" -> {
                        if (isResearcher) {
                            ResearcherDecorator rd = database.getResearcherByUser(this.employee);
                            new ResearcherMenu(rd).menu();
                        } else {
                            becomeResearcher(input, database);
                        }
                    }
                    case "0" -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Incorrect command, try again.");
                }

            } catch (PermissionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendMessage(Scanner input, Database db) {
        while (true) {
            try {
                System.out.print("Enter recipient user id: ");
                int userId = input.nextInt();
                input.nextLine();

                User user = db.getUserById(userId);
                if (user == null) throw new UserNotFoundException("");

                System.out.print("Enter message text: ");
                String text = input.nextLine();

                this.employee.sendMessage(user, text);
                System.out.println("Message sent successfully.");
                return;

            } catch (PermissionException e) {
                System.out.println(e.getMessage());
                return;
            } catch (UserNotFoundException e) {
                System.out.println("User not found, try again.");
            }
        }
    }

    public void viewNews(Database database) {
        var newsList = database.getNews();
        if (newsList.isEmpty()) {
            System.out.println("No news available.");
            return;
        }
        System.out.println("===== News =====");
        for (var news : newsList) {
            System.out.println(news);
        }
    }

    public void becomeResearcher(Scanner input, Database database) {
        if (database.getResearcherByUser(this.employee) != null) {
            System.out.println("You are already a researcher.");
            return;
        }

        System.out.print("Confirm becoming a researcher? (yes/no): ");
        String confirm = input.nextLine().trim();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        try {
            database.makeResearcher(this.employee.getId());
            System.out.println("You are now registered as a Researcher!");
        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (CantBeResearcherException e) {
            System.out.print(e.getMessage() + "\n");
        }
    }
}