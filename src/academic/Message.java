package academic;

import users.User;

import java.io.Serializable;

public class Message implements Serializable {

    private User sender;
    private User recipient;
    private String text;

    public Message(User sender, User recipient, String text){
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
    }


    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }
}
