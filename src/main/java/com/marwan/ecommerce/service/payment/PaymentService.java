package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.dto.payment.PaymentPagingOptions;
import com.marwan.ecommerce.exception.payment.PaymentNotFoundException;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.model.enums.PaymentStatus;
import com.marwan.ecommerce.repository.PaymentRepository;
import com.marwan.ecommerce.service.common.BaseService;
import com.marwan.ecommerce.service.order.event.orderPaid.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService extends BaseService
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
        return paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public Payment getUserPayment(UUID paymentId, UUID userId)
    {
        return paymentRepository.findByPaymentIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public Page<Payment> getPayments(PaymentPagingOptions pagingOptions)
    {
        var pageable = constructPageable(pagingOptions);
        return paymentRepository.findAll(pageable);
    }

    public Page<Payment> getUserPayments(PaymentPagingOptions pagingOptions, UUID userId)
    {

        var pageable = constructPageable(pagingOptions);
        return paymentRepository.findAllByUserId(pageable, userId);
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