package main;

import academic.Course;
import research.PaperComparatorByCitation;
import research.ResearcherDecorator;
import types.TeacherType;
import users.Teacher;

public class UniSystem {

    static void main(){

        Teacher teacher = new Teacher("islam", "ds", 200000.0, TeacherType.LECTOR);
        ResearcherDecorator researcher = new ResearcherDecorator(teacher);
        researcher.printPapers(new PaperComparatorByCitation());
    }
}
