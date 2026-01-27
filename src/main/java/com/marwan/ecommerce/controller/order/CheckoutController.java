package com.marwan.ecommerce.controller.order;

import com.marwan.ecommerce.dto.order.CheckoutResponseDto;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.order.CheckoutService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController
{
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@AuthenticationPrincipal CustomUserDetails userDetails)
            throws StripeException
    {
        CheckoutResponseDto response = checkoutService.checkout(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }


}
