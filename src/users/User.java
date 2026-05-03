package users;

import academic.Course;
import main.UniSystem;
import storage.Database;

import javax.xml.crypto.Data;
import java.awt.dnd.DropTarget;
import java.lang.reflect.Array;
import java.util.*;

public abstract class User implements Comparable<User> {
    private String username;
    private String password;
    private static int ID = 0;
    private int id;

    public User(String username, String password) {
        setId(ID++);
        setPassword(password);
        setUsername(username);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + "; Username: " + this.getUsername() + ";";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(User o) {
        if (o == null) {
            throw new NullPointerException("Cannot compare with null");
        }
        return Integer.compare(this.getId(), o.getId());
    }

    public boolean login(String password){
        Database database = Database.getInstance();
        return database.login(this, password);
    }

    public void logout(){
        Database database = Database.getInstance();
        database.logout();
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof User)) return false;

        User u = (User) o;
        return this.getId() == u.getId();
    }

    public int hashCode(){
        return  Objects.hash(id);
    }
}
