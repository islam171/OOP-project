package research;

import java.util.Comparator;
import java.util.Date;

public class PaperComparatorByDate implements Comparator<ResearchPaper> {


    @Override
    public int compare(ResearchPaper o1, ResearchPaper o2) {
        if(o1.getDate().isAfter(o2.getDate())){
            return 1;
        }else if(o1.getDate().isBefore(o2.getDate())){
            return -1;
        }
        return 0;
    }
}
