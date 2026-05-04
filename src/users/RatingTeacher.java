package users;

public class RatingTeacher {

    private Teacher teacher;
    private Student student;
    private int rating;

    public RatingTeacher(Teacher teacher, Student student, int rating){
        this.rating =rating;
        this.teacher = teacher;
        this.student = student;
    }

}
