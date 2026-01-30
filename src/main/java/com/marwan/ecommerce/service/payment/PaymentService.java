package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.exception.payment.PaymentNotFoundException;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.model.enums.PaymentStatus;
import com.marwan.ecommerce.repository.PaymentRepository;
import com.marwan.ecommerce.service.order.event.orderPaid.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService
{


    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void pay(UUID paymentId)
    {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException(paymentId));
        if (payment.getStatus() == PaymentStatus.SUCCEEDED) {
            // for idempotency
            return;
        }


        payment.markSucceeded();
        payment.getOrder().markPaid();
        paymentRepository.save(payment);

        applicationEventPublisher.publishEvent(
                new OrderPaidEvent(payment.getOrder().getOrderId())
        );

    }

    public Payment getPayment(UUID paymentId)
    {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException(paymentId));
    }

    @Transactional
    public void save(Payment payment)
    {
        paymentRepository.save(payment);
    }

    @Transactional
    public void handleFailure(UUID paymentId)
    {
        Payment payment = getPayment(paymentId);
        if (payment.getStatus() == PaymentStatus.FAILED) {
            return;
        }
        payment.markFailed();
        payment.getOrder().markPaymentFailed();
        paymentRepository.save(payment);
    }
}