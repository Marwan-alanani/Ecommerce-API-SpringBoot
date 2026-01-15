package com.marwan.ecommerce.errors.exceptions.user;

import com.marwan.ecommerce.errors.exceptions.abstractions.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UUID id) {
        super("No user with the given " + id + " id was found");
    }
}
