package users;

import java.util.Comparator;


public class ComparatorGPA implements Comparator<Student> {

    public int compare(Student a, Student b) {
        return (int) (a.getGPA() - b.getGPA());
    }
}
