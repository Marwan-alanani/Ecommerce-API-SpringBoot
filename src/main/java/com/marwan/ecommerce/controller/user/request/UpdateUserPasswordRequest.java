package com.marwan.ecommerce.controller.user.request;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdateUserPasswordRequest {

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String currentPassword;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String newPassword;

}
