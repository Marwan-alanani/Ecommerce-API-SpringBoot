package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class CartTest
{
    @Test
    public void Cart_AddCartItem_ShouldAddToCart()
    {
        User user = User.create(
                "Marwan",
                "Walid",
                UserRole.USER,
                "marwan@mail.com",
                ""
        );

        Cart cart = Cart.create(user.getUserId());
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "bed",
                "",
                BigDecimal.valueOf(2),
                "",
                category
        );
        CartItem cartItem = CartItem.fromProduct(product);
        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem);
        Assertions.assertEquals(cartItem.getCart(), cart);
        Assertions.assertEquals(cart.getCartItems().getFirst(), cartItem);
        Assertions.assertEquals(1, cart.getCartItems().size());
    }

    @Test
    public void Cart_RemoveCartItem_ShouldRemoveFromCart()
    {
        User user = User.create(
                "Marwan",
                "Walid",
                UserRole.USER,
                "marwan@mail.com",
                ""
        );

        Cart cart = Cart.create(user.getUserId());
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "bed",
                "",
                BigDecimal.valueOf(2),
                "",
                category
        );

        Product product2 = Product.create(
                "toothbrush",
                "",
                BigDecimal.valueOf(2),
                "",
                category
        );
        CartItem cartItem = CartItem.fromProduct(product);
        CartItem cartItem2 = CartItem.fromProduct(product2);
        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem2);
        Assertions.assertEquals(2, cart.getCartItems().size());
        cart.remove(cartItem.getProduct().getProductId());
        Assertions.assertEquals(1, cart.getCartItems().size());
    }

    @Test
    public void Cart_GetTotalCost_ShouldReturnTotalCost()
    {

        User user = User.create(
                "Marwan",
                "Walid",
                UserRole.USER,
                "marwan@mail.com",
                ""
        );

        Cart cart = Cart.create(user.getUserId());
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "bed",
                "",
                BigDecimal.valueOf(2),
                "",
                category
        );

        Product product2 = Product.create(
                "toothbrush",
                "",
                BigDecimal.valueOf(2),
                "",
                category
        );
        CartItem cartItem = CartItem.fromProduct(product);
        CartItem cartItem2 = CartItem.fromProduct(product2);
        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem2);
        var total = cartItem2.getTotalPrice().add(cartItem2.getTotalPrice());
        Assertions.assertEquals(total, cart.getTotalCost());
    }

}
