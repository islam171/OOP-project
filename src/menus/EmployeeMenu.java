package menus;

import exceptions.*;
import research.ResearcherDecorator;
import storage.Database;
import storage.Log;
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
                        """ +
                        (isResearcher ?
                                "4. Researcher menu\n" :
                                "4. Become Researcher\n") +
                        """
                        0. Logout
                        ===========================
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
                        System.out.print("Logging out...\n");
                        return;
                    }
                    default -> System.out.print("Incorrect command, try again.\n");
                }

            } catch (PermissionException e) {
                System.out.print(e.getMessage()+"\n");
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
                if (user == null) throw new UserException("User not found");

                System.out.print("Enter message text: ");
                String text = input.nextLine();

                this.employee.sendMessage(user, text);
                System.out.print("Message sent successfully.\n");
                Log log = new Log(db.getUser().getUsername(), "sent message");
                db.addLogs(log);
                return;

            } catch (PermissionException | MessageException | UserException e) {
                System.out.print(e.getMessage() + "\n");
                return;
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
        try{
            this.employee.becomeResearcher();
            System.out.print("You are now registered as a Researcher!\n");
            Log log = new Log(database.getUser().getUsername(), "become researcher");
            database.addLogs(log);
        } catch (ResearcherException | UserException e) {
            System.out.print(e.getMessage() + "\n");
        }
    }
}