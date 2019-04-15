package com.likorn_devaki.wordbook.Exception;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.postgresql.util.ServerErrorMessage;

public class UserExistException extends PSQLException {

    public UserExistException(String msg, PSQLState state, Throwable cause) {
        super(msg, state, cause);
    }

    public UserExistException(String msg, PSQLState state) {
        super(msg, state);
    }

    public UserExistException(ServerErrorMessage serverError) {
        super(serverError);
    }
}
