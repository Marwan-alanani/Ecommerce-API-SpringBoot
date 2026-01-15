package com.marwan.ecommerce.errors.exceptions.user;

import com.marwan.ecommerce.errors.exceptions.abstractions.ValidationException;

public class EmailExistsException extends ValidationException {
    public EmailExistsException(String email) {
        super("Email " + email + " exists");
    }
}
