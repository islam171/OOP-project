package storage;

import users.User;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable  {

    private String username;
    private Date date;
    private String action;

    public Log(String username, String action){
        setDate(new Date());
        setUsername(username);
        setAction(action);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String toString(){
        return "LOG: " + date.toString() + " " + this.getUsername() + " " + this.getAction();
    }
}
