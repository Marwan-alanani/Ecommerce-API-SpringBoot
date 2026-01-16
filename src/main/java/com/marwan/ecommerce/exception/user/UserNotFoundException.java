package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.abstractions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String email) {
        super("No user with the given email: " + email +" was found");
    }
}
