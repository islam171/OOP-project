package academic;

import users.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class News implements Serializable {
    private static int ID = 0;

    private int id;
    private Date date;
    private String title;
    private String content;


    public News(String title, String content) {
        ID++;
        this.id = ID;
        this.date = new Date();
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof News)) return false;

        News u = (News) o;
        return this.getId() == u.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
