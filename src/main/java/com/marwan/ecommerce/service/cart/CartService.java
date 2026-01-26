package com.marwan.ecommerce.service.cart;

import com.marwan.ecommerce.exception.cart.CartAlreadyExistsForUserException;
import com.marwan.ecommerce.exception.cart.CartIdNotFoundException;
import com.marwan.ecommerce.exception.cart.CartWithUserIdNotFoundException;
import com.marwan.ecommerce.exception.product.NotEnoughProductException;
import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.CartItem;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.CartRepository;
import com.marwan.ecommerce.service.cart.command.AddOrUpdateCartItemCommand;
import com.marwan.ecommerce.service.cart.command.RemoveCartItemCommand;
import com.marwan.ecommerce.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService
{
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart createCart(UUID userId) throws CartAlreadyExistsForUserException
    {
        Cart cart = cartRepository.getCartWithItemsByUserId(userId)
                .orElse(null);

        if (cart != null) {
            throw new CartAlreadyExistsForUserException(userId);
        }
        cart = Cart.create(userId);
        cartRepository.save(cart);
        return cart;
    }

    private Cart getOrCreateCart(UUID userId)
    {

        Cart cart = cartRepository.getCartWithItemsByUserId(userId)
                .orElse(null);

        if (cart == null) {
            cart = Cart.create(userId);
            cartRepository.save(cart);
        }
        return cart;
    }

    public Cart getCart(UUID cartId)
            throws CartIdNotFoundException
    {
        return cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartIdNotFoundException(cartId));
    }

    public Cart getCartWithUserId(UUID userId)
            throws CartWithUserIdNotFoundException
    {
        return cartRepository.getCartWithItemsByUserId(userId)
                .orElseThrow(() -> new CartWithUserIdNotFoundException(userId));

    }


    public CartItem addOrUpdateCartItem(AddOrUpdateCartItemCommand command)
            throws NotEnoughProductException, ProductIdNotFoundException,
            CartWithUserIdNotFoundException
    {

        Product product = productService.getProduct(command.getProductId(), true);

        Cart cart = getOrCreateCart(command.getUserId());
        CartItem cartItem = cart.getCartItemByProductId(command.getProductId());

        int totalQuantity = (cartItem == null) ? command.getQuantity() :
                cartItem.getQuantity() + command.getQuantity();

        if (product.getBalance() < totalQuantity) {
            throw new NotEnoughProductException(
                    product.getName(),
                    product.getBalance(),
                    totalQuantity
            );
        }

        if (cartItem == null) {
            cartItem = CartItem.fromProduct(product);
            cartItem.setQuantity(totalQuantity);
            cart.addCartItem(cartItem);
        } else {
            cartItem.setQuantity(totalQuantity);
        }
        cartRepository.save(cart);
        return cartItem;
    }

    public void removeCartItem(RemoveCartItemCommand command)
            throws CartWithUserIdNotFoundException
    {
        Cart cart = getCartWithUserId(command.userId());
        cart.remove(command.productId());
        cartRepository.save(cart);
    }


    public void clearCart(UUID userId)
            throws CartWithUserIdNotFoundException
    {
        Cart cart = getCartWithUserId(userId);
        cart.clear();
        cartRepository.save(cart);
    }

}
