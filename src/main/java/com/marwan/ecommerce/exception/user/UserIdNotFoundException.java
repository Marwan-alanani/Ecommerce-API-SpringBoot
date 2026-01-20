package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class UserIdNotFoundException extends NotFoundException {
    public UserIdNotFoundException(UUID id) {
        super(101,"No user with the given id: " + id + " was found");
    }
}
