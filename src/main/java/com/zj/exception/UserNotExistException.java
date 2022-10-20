package com.zj.exception;

public class UserNotExistException  extends RuntimeException{
    public UserNotExistException() {
        super("User Not Exist!");
    }
}
