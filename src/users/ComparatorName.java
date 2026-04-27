package users;

import java.util.Comparator;

public class ComparatorName implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return a.getUsername().compareTo(b.getUsername());
    }
}
