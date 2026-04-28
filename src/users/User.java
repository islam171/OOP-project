package users;

import main.UniSystem;

import java.lang.reflect.Array;
import java.util.*;

public abstract class User {
    private String username;
    private String password;
    private static int ID = 0;
    private int id;
    private UniSystem system;

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

    public abstract boolean showCommands();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
