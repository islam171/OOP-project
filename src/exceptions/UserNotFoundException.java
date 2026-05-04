package exceptions;

import users.User;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(User user, String msg){
        super("User " + user.getUsername() + " is not found " + msg);
    }

}
