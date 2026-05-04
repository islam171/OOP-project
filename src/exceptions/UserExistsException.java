package exceptions;

import users.User;

public class UserExistsException extends Exception {

    public UserExistsException(String username){
        super("User " + username + " already exists" );
    }

}
