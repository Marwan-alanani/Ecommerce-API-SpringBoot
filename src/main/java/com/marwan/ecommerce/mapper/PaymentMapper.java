package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.dto.payment.AdminPaymentDto;
import com.marwan.ecommerce.dto.payment.UserPaymentDto;
import com.marwan.ecommerce.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper
{
    @Mapping(target = "orderId", source = "order.orderId")
    UserPaymentDto toUserPaymentDto(Payment payment);

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "userId", source = "order.userId")
    AdminPaymentDto toAdminPaymentDto(Payment payment);
}
