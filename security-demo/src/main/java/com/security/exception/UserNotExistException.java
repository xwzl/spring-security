package com.security.exception;

/**
 * 自定义异常
 *
 * @author xuweizhi
 * @since 2019/05/12 12:06
 */
public class UserNotExistException extends RuntimeException {

    private static final long serialVersionUID = 2120783242434631643L;

    private String id;

    public UserNotExistException(String id) {
        super("User not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
