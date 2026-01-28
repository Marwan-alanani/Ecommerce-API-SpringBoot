package com.marwan.ecommerce.service.payment;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeWebhookService implements WebhookService
{

    @Value("${stripe.webhookKey}")
    private String webhookKey;
    private final PaymentService paymentService;

    @Override
    public void handle(String signature, String payload)
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
        } catch (SignatureVerificationException e) {
        }
    }
}
