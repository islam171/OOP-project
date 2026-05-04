package research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface Researcher {

    List<ResearchPaper> papers = new ArrayList<>();


    List<ResearchPaper> getPapers();

    void addPaper(ResearchPaper paper);

    void removePaper(ResearchPaper paper);

    void viewProjects();

    void printPapers(Comparator<ResearchPaper> comparator);

    int getHIndex();

    void joinProject(ResearchProject project);

    List<ResearchProject> getProjects();
}
