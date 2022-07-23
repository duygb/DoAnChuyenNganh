package com.nlu.common.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String mess){
        super(mess);
    }
}
