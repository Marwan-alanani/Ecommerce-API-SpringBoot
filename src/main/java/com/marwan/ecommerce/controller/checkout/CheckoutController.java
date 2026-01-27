package com.marwan.ecommerce.controller.checkout;

import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.checkout.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController
{
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        UUID orderId =  checkoutService.checkout(userDetails.getUserId());
        return ResponseEntity.ok(orderId);
    }


}
