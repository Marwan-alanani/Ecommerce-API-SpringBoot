package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class EmailExistsException extends ValidationException {
    public EmailExistsException(String email) {
        super("Email " + email + " exists");
    }
}
