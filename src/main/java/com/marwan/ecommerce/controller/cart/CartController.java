package com.marwan.ecommerce.controller.cart;

import com.marwan.ecommerce.controller.cart.request.AddOrUpdateCartItemRequest;
import com.marwan.ecommerce.dto.cart.CartDto;
import com.marwan.ecommerce.dto.cart.CartItemDto;
import com.marwan.ecommerce.mapper.CartMapper;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.CartItem;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.cart.CartService;
import com.marwan.ecommerce.service.cart.command.AddCartItemCommand;
import com.marwan.ecommerce.service.cart.command.RemoveCartItemCommand;
import com.marwan.ecommerce.service.cart.command.UpdateCartItemCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController
{
    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/me")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Cart cart = cartService.getCartWithUserId(userDetails.getUserId());
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping
    public ResponseEntity<?> createCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Cart cart = cartService.createCart(userDetails.getUserId());
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }


    @PostMapping("/me/items")
    public ResponseEntity<?> addCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddOrUpdateCartItemRequest request)
    {
        CartItem item = cartService.addCartItem(
                new AddCartItemCommand(
                        userDetails.getUserId(),
                        request.productId(),
                        request.quantity())
        );

        CartItemDto cartItemDto = cartMapper.cartItemEntitytoCartItemDto(item);

        return ResponseEntity.ok(cartItemDto);
    }

    @PutMapping("/me/items")
    public ResponseEntity<?> updateCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddOrUpdateCartItemRequest request)

    {
        CartItem item = cartService.updateCartItem(
                new UpdateCartItemCommand(
                        request.productId(),
                        userDetails.getUserId(),
                        request.quantity())
        );

        return ResponseEntity.ok(cartMapper.cartItemEntitytoCartItemDto(item));
    }

    @DeleteMapping("/me/items/{productId}")
    public ResponseEntity<?> removeCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID productId)
    {
        cartService.removeCartItem(new RemoveCartItemCommand(
                userDetails.getUserId(),
                productId
        ));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable UUID cartId)
    {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        cartService.clearCart(userDetails.getUserId());
        return ResponseEntity.ok().build();
    }


}
