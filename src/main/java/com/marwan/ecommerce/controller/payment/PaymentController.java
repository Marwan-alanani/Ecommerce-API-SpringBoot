package com.marwan.ecommerce.controller.payment;

import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.payment.AdminPaymentDto;
import com.marwan.ecommerce.dto.payment.UserPaymentDto;
import com.marwan.ecommerce.mapper.PaymentMapper;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController
{
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<AdminPaymentDto>> getAllPayments(
            @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        Page<Payment> paymentPage = paymentService.getPayments(pageable);
        List<AdminPaymentDto> paymentDtos = paymentPage.stream()
                .map(paymentMapper::toAdminPaymentDto)
                .toList();
        return ResponseEntity.ok(toPageDto(paymentPage, paymentDtos));

    }

    @GetMapping("/me")
    ResponseEntity<PageDto<UserPaymentDto>> getUserPayments(
            @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    )
    {
        Page<Payment> paymentPage = paymentService.getUserPayments(
                pageable,
                customUserDetails.getUserId()
        );
        List<UserPaymentDto> paymentDtos = paymentPage.stream()
                .map(paymentMapper::toUserPaymentDto)
                .toList();
        return ResponseEntity.ok(toPageDto(paymentPage, paymentDtos));
    }


    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getPayment(@PathVariable UUID paymentId)
    {
        Payment payment = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(paymentMapper.toAdminPaymentDto(payment));
    }


    @GetMapping("/me/{paymentId}")
    ResponseEntity<?> getUserPayment(
            @PathVariable UUID paymentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {

        Payment payment = paymentService.getUserPayment(paymentId, userDetails.getUserId());
        return ResponseEntity.ok(paymentMapper.toUserPaymentDto(payment));
    }

}
