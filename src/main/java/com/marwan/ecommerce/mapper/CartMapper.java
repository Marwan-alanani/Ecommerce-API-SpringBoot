package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.dto.cart.CartDto;
import com.marwan.ecommerce.dto.cart.CartItemDto;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper
{
    @Mapping(target = "cartItems", source = "cartItems")
    @Mapping(target = "totalCost", expression = "java(cart.getTotalCost())")
    CartDto cartEntitytoCartDto(Cart cart);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "unitPrice", source = "product.sellingPrice")
    CartItemDto cartItemEntitytoCartItemDto(CartItem cartItem);
}
