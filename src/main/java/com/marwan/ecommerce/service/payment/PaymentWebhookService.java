package com.marwan.ecommerce.service.payment;

public interface PaymentWebhookService
{
    public void handle(String signature, String payload);
}
