package com.marwan.ecommerce.dto.purchase;

import java.util.Date;
import java.util.UUID;

public record PurchaseDto(
        UUID purchaseId,
        UUID productId,
        double price,
        int quantity,
        UUID supplierId,

        Date createdDateTime

)
{

}
