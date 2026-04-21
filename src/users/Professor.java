package users;

import research.PaperComparator;
import research.ResearchPaper;
import research.Researcher;

import java.util.List;

public class Professor extends Teacher implements Researcher {

    protected Professor(String username, String password, UniSystem system) {
        super(username, password, system);
    }

    @Override
    public int getHIndex() {
        return 0;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return List.of();
    }

    @Override
    public void addPaper(ResearchPaper paper) {

    }

    @Override
    public void printPapers(PaperComparator comparator) {

    }
}
