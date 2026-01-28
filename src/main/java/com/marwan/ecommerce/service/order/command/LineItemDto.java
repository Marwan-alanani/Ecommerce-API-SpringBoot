package com.marwan.ecommerce.service.order.command;

import java.math.BigDecimal;

public record LineItemDto(String name, BigDecimal unitPrice, long quantity) {}
