package com.marwan.ecommerce.exceptions.user;

import com.marwan.ecommerce.exceptions.abstractions.ValidationException;

public class EmailExistsException extends ValidationException {
    public EmailExistsException(String email) {
        super("Email " + email + " exists");
    }
}
