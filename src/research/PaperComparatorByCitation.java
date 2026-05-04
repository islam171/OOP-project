package research;

import java.util.Comparator;

public class PaperComparatorByCitation implements Comparator<ResearchPaper> {

    @Override
    public int compare(ResearchPaper o1, ResearchPaper o2) {
        return Integer.compare(o1.getCitation(), o2.getCitation());
    }
}
