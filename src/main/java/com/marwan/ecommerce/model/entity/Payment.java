package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.Currency;
import com.marwan.ecommerce.model.enums.PaymentProvider;
import com.marwan.ecommerce.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public final class Payment
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID paymentId;

    @Column(unique = true)
    private String checkoutSessionId;

    @Enumerated(EnumType.STRING)
    private PaymentProvider provider;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    @Setter(AccessLevel.NONE)
    private Order order;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    private Instant createdDateTime;
    @UpdateTimestamp
    private Instant updatedDateTime;

    public static Payment forOrder(Order order)
    {
        return new Payment(
                UUID.randomUUID(),
                null,
                null,
                order,
                order.getTotalPrice(),
                order.getCurrency(),
                PaymentStatus.CREATED,
                null,
                null
        );
    }

    public void addPaymentProvider(PaymentProvider paymentProvider)
    {
        this.provider = paymentProvider;
    }

    public void markSucceeded()
    {
        this.status = PaymentStatus.SUCCEEDED;
    }

}
