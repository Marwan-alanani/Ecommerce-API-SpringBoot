package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.exception.payment.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StripeWebhookService implements PaymentWebhookService
{

    @Value("${stripe.webhookKey}")
    private String webhookKey;
    private final PaymentService paymentService;

    @Override
    public void handle(String signature, String payload)
    {
        try {

            var event = Webhook.constructEvent(payload, signature, webhookKey);
            var json = event.getDataObjectDeserializer().getRawJson();
            switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    var paymentIntent = PaymentIntent.GSON.fromJson(json, PaymentIntent.class);
                    var paymentIdStr = paymentIntent.getMetadata().get("paymentId");
                    if (paymentIdStr == null || paymentIdStr.isEmpty()) {
                        // maybe log the event id
                        return;
                    }
                    paymentService.pay(UUID.fromString(paymentIdStr));
                }

                case "payment_intent.payment_failed" -> {
                    var paymentIntent = PaymentIntent.GSON.fromJson(json, PaymentIntent.class);
                    System.out.println("--------------------------------------------------");
                    System.out.println(paymentIntent.getMetadata().get("paymentId"));
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (SignatureVerificationException e) {
            throw new PaymentException();
        }
    }
}