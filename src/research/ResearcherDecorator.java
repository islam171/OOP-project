package research;

import storage.Database;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResearcherDecorator implements Researcher{

    private User user;
    private List<ResearchPaper> papers = new ArrayList<>();

    public ResearcherDecorator(User user){
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getHIndex() {
        this.papers.sort(new PaperComparatorByCitation());
        int min = this.papers.getFirst().getCitation();

        int count = 1;
        for(ResearchPaper paper : this.papers){
            min = Math.min(paper.getCitation(), min);
            if(min < count){
                break;
            }
            count++;
        }
        return count;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return this.papers;
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        this.papers.add(paper);
    }

    @Override
    public void removePaper(ResearchPaper paper) {
        this.papers.remove(paper);
    }


    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        this.papers.sort(comparator);
        Collections.sort(this.papers, comparator);
        for(ResearchPaper p : this.papers){
            System.out.println(p);
        }
    }

    @Override
    public void joinProject(ResearchProject project){
        project.addParticipant(this);
    }

    @Override
    public List<ResearchProject> getProjects(){
        Database database = Database.getInstance();
        return database.getProjects().stream().filter(item -> item.getParticipants().contains(this)).toList();
    }

    @Override
    public void viewProjects(){
        System.out.println("Projects: ");
        for(ResearchProject project : getProjects()){
            System.out.println(project);
        }
    }

}
