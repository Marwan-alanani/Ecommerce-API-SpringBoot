package com.marwan.ecommerce.service.payment;

public interface PaymentWebhookService
{
    void handle(String signature, String payload);
}
