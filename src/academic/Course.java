package academic;

import users.Teacher;
import java.util.Objects;

public class Course {
    private String name;
    private int credit;
    private Teacher instructor;
    private static int ID;
    private int id;

    public Course(String name, int credit, Teacher teacher){
        setName(name);
        setCredit(credit);
        setInstructor(teacher);
        setId(++ID);
    }

    // курс может быть и без инструктора напримеер ваканисия
    public Course(String name, int credit){
        setName(name);
        setCredit(credit);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    public String toString(){
        return "Course, Name: "  + getName() + "; credits: " + getCredit() + " instructor: " + getInstructor();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof Course)) return false;

        Course c = (Course) o;
        return this.getId() == c.getId();
    }

    public int hashCode(){
        return  Objects.hash(id);
    }
}
