package com.marwan.ecommerce.controller.webhook;

import com.marwan.ecommerce.service.payment.PaymentWebhookService;
import com.stripe.exception.SignatureVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WebhookController
{
    private final PaymentWebhookService paymentWebhookService;

    @PostMapping("/payment")
    public ResponseEntity<?> handlePaymentWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
    )
    {
        paymentWebhookService.handle(signature, payload);
        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<?> handlePaymentWebhookException()
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
