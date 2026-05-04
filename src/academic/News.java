package academic;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {
   private static int ID = 0;

    public int id;
    public Date date;
    public String title;
    public  String content;


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
}
