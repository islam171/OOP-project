package menus;

import research.PaperComparatorByCitation;
import research.ResearchPaper;
import research.ResearchProject;
import research.ResearcherDecorator;
import storage.Database;
import exceptions.ProjectExistsException;
import storage.Log;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ResearcherMenu {

    private ResearcherDecorator researcher;

    public ResearcherMenu(ResearcherDecorator researcher) {
        this.researcher = researcher;
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        Database database = Database.getInstance();

        while (true) {
            System.out.print("""
                    
                    ===== Researcher Menu =====
                    1. View my papers
                    2. Add paper
                    3. Print papers by citations
                    4. Print papers by date
                    5. Print papers by length (pages)
                    6. View my projects
                    7. Join project
                    8. Create project
                    9. View H-Index
                    0. Back
                    ===========================
                    """);
            String command = input.nextLine();

            switch (command) {
                case "1" -> viewPapers();
                case "2" -> addPaper(input, database);
                case "3" -> researcher.printPapers(new PaperComparatorByCitation());
                case "4" -> researcher.printPapers((a, b) -> a.getDate().compareTo(b.getDate()));
                case "5" -> researcher.printPapers((a, b) -> Integer.compare(a.getPages(), b.getPages()));
                case "6" -> researcher.viewProjects();
                case "7" -> joinProject(input, database);
                case "8" -> createProject(input, database);
                case "9" -> System.out.println("Your H-Index: " + researcher.getHIndex());
                case "0" -> { return; }
                default -> System.out.println("Incorrect command, try again.");
            }
        }
    }

    private void viewPapers() {
        List<ResearchPaper> papers = researcher.getPapers();
        if (papers.isEmpty()) {
            System.out.println("You have no papers yet.");
            return;
        }
        System.out.println("===== Your Papers =====");
        for (int i = 0; i < papers.size(); i++) {
            System.out.println((i + 1) + ". " + papers.get(i));
        }
    }

    private void addPaper(Scanner input, Database db) {
        try {
            System.out.print("Enter paper name: ");
            String name = input.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter number of pages: ");
            int pages = Integer.parseInt(input.nextLine().trim());
            if (pages <= 0) {
                System.out.println("Pages must be positive.");
                return;
            }

            System.out.print("Enter publication date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(input.nextLine().trim());

            ResearchPaper paper = new ResearchPaper(name, pages, date);
            researcher.addPaper(paper);
            System.out.println("Paper added successfully!");
            Log log = new Log(db.getUser().getUsername(), "added paper");
            db.addLogs(log);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private void joinProject(Scanner input, Database database) {
        List<ResearchProject> allProjects = database.getProjects();

        if (allProjects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        // показываем только те, в которых ещё не состоит
        List<ResearchProject> available = allProjects.stream()
                .filter(p -> !p.getParticipants().contains(researcher))
                .toList();

        if (available.isEmpty()) {
            System.out.println("You are already in all available projects.");
            return;
        }

        System.out.println("===== Available Projects =====");
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i).getTopic());
        }

        System.out.print("Enter project number: ");
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            if (choice < 1 || choice > available.size()) {
                System.out.println("Invalid choice.");
                return;
            }
            ResearchProject project = available.get(choice - 1);
            researcher.joinProject(project);
            System.out.println("Successfully joined: " + project.getTopic());
            Log log = new Log(database.getUser().getUsername(), "joined to project " + project.getTopic());
            database.addLogs(log);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void createProject(Scanner input, Database database) {
        try {
            System.out.print("Enter project topic: ");
            String topic = input.nextLine().trim();
            if (topic.isEmpty()) {
                System.out.println("Topic cannot be empty.");
                return;
            }

            ResearchProject project = new ResearchProject(topic);
            project.addParticipant(researcher); // создатель сразу участник
            database.addProject(project);
            System.out.println("Project created: " + topic);
            Log log = new Log(database.getUser().getUsername(), "created project");
            database.addLogs(log);

        } catch (ProjectExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}