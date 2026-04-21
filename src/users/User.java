package users;

import main.UniSystem;

;

public abstract class User {
    private String username;
    private String password;
    private static int ID = 0;
    private int id;
    private UniSystem system;

    public User(String username, String password, UniSystem system) {
        setId(ID++);
        setPassword(password);
        setUsername(username);
        this.system = system;
    }

    @Override
    public String toString() {
        return "Username: " + this.getUsername() + "; Id: " + getId() + ";";
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

    public UniSystem getSystem() {
        return system;
    }

    public void setSystem(UniSystem system) {
        this.system = system;
    }
}
