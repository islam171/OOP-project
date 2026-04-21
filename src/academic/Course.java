package academic;

import users.Teacher;

public class Course {
    private String name;
    private int credit;
    private Teacher instructor;;

    public Course(String name, int credit, Teacher teacher){
        setName(name);
        setCredit(credit);
        setInstructor(teacher);
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
}
