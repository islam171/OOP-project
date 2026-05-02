package research;

import users.User;

import java.util.ArrayList;
import java.util.List;

public class ResearcherDecorator implements Researcher{

    private User user;
    private List<ResearchPaper> papers = new ArrayList<>();

    public ResearcherDecorator(User user){

        // тут можно определять класс юзера и проверять может ли он стать ресерчиром

        setUser(user);
    }


    @Override
    public int getHIndex() {
        return 0;
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
    public void printPapers(PaperComparator comparator) {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
