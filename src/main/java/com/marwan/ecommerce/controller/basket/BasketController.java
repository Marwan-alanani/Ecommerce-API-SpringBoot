package com.marwan.ecommerce.controller.basket;

import com.marwan.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/baskets")
public class BasketController
{
    @PostMapping("/create")
    public ResponseEntity<?> createBasket(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }


    @PostMapping("/addItem")
    public ResponseEntity<?> addBasketItem(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @PostMapping("/removeItem")
    public ResponseEntity<?> removeBasketItem(
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @GetMapping("/basket")
    public ResponseEntity<?> getBasket(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @PostMapping("/remove/{basketId}")
    public ResponseEntity<?> removeBasket(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long basketId)
    {
        throw new NotImplementedException();
    }




}
