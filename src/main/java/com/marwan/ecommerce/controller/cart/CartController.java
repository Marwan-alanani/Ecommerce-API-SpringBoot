package com.marwan.ecommerce.controller.cart;

import com.marwan.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController
{
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }


    @PostMapping("/cartItem")
    public ResponseEntity<?> addCartItem(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @PostMapping("/removeItem")
    public ResponseEntity<?> removeCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @GetMapping("/basket")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        throw new NotImplementedException();
    }

    @PostMapping("/remove/{basketId}")
    public ResponseEntity<?> removeCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                    @PathVariable Long basketId)
    {
        throw new NotImplementedException();
    }

}
