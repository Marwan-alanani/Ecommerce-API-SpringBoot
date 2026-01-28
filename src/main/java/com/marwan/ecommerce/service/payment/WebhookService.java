package com.marwan.ecommerce.service.payment;

public interface WebhookService
{
    public void handle(String signature, String payload);
}
