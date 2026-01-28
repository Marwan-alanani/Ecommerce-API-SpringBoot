package com.marwan.ecommerce.controller.order;

import com.marwan.ecommerce.dto.order.CheckoutResponseDto;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.order.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController
{
    private final CheckoutService checkoutService;

    @Value("${stripe.webhookKey}")
    private String webhookKey;

    @PostMapping
    public ResponseEntity<?> checkout(@AuthenticationPrincipal CustomUserDetails userDetails)
            throws StripeException
    {
        CheckoutResponseDto response = checkoutService.checkout(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
    )
    {
        try {

            var event = Webhook.constructEvent(payload, signature, webhookKey);
            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
            switch (event.getType()) {
                case "payment_intent.succeeded" -> {

                }
                case "payment_intent.failed" -> {

                }
            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
