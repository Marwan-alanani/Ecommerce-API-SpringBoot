package com.marwan.ecommerce.controller.webhook;

import com.marwan.ecommerce.service.payment.PaymentService;
import com.marwan.ecommerce.service.payment.PaymentWebhookService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    ) throws SignatureVerificationException
    {
        paymentWebhookService.handle(signature, payload);
        return ResponseEntity.ok().build();

    }
}
