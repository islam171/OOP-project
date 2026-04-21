package users;

public class Student extends User{

    private int credits;
    private String major;
    private double GPA;

    public Student(String username, String password, UniSystem system, int credits) {
        super(username, password, system);
        setCredits(credits);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public boolean showCommands() {
        return true;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
