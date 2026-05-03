package research;

import java.util.ArrayList;
import java.util.List;

public interface Researcher {

    List<ResearchPaper> papers = new ArrayList<>();

    int getHIndex();

    List<ResearchPaper> getPapers();

    void addPaper(ResearchPaper paper);

    void printPapers(PaperComparator comparator);



}
