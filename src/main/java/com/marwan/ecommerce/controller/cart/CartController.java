package com.marwan.ecommerce.controller.cart;

import com.marwan.ecommerce.controller.cart.request.AddCartItemRequest;
import com.marwan.ecommerce.controller.cart.request.UpdateCartItemRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/cart") // GET /carts/me
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Cart cart = cartService.getCartWithUserId(userDetails.getUserId());
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/cart") // POST /carts/me
    public ResponseEntity<CartDto> createCart(
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Cart cart = cartService.createCart(userDetails.getUserId());
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }


    @PostMapping("/cart/item") // POST /carts/me/items
    public ResponseEntity<CartItemDto> addCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddCartItemRequest request)
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

    @PatchMapping("/cart/item") // Patch /carts/me/items
    public ResponseEntity<CartItemDto> updateCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateCartItemRequest request)

    {
        CartItem item = cartService.updateCartItem(
                new UpdateCartItemCommand(
                        request.productId(),
                        userDetails.getUserId(),
                        request.quantity())
        );

        return ResponseEntity.ok(cartMapper.cartItemEntitytoCartItemDto(item));
    }

    @DeleteMapping("/cart/item/{productId}") // DELETE /carts/me/{productId}
    public ResponseEntity<Void> removeCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID productId)
    {
        cartService.removeCartItem(new RemoveCartItemCommand(
                userDetails.getUserId(),
                productId
        ));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID cartId)
    {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = cartMapper.cartEntitytoCartDto(cart);

        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/cart/clear") // PUT /carts/me
    public ResponseEntity<Void> clearUserCart(
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        cartService.clearCart(userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart") // DELETE /carts/me
    public ResponseEntity<Void> removeUserCart(
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        cartService.removeUserCart(userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
    // TODO: add get all carts
}
