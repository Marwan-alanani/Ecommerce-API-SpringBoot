package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.exception.product.NotEnoughProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;



@TestComponent
@ExtendWith(MockitoExtension.class)
public class ProductTest
{
    @Test
    public void Product_DecreaseBalance_ShouldNotThrowException()
    {
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "Product",
                "",
                BigDecimal.valueOf(1),
                "",
                category
        );
        product.increaseBalance(2);
        Assertions.assertDoesNotThrow(() -> product.decreaseBalance(product.getBalance()));
    }

    @Test
    public void Product_Deactivate_SetsEnabledFalse()
    {
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "Product",
                "",
                BigDecimal.valueOf(1),
                "",
                category
        );
        product.deactivate();
        Assertions.assertFalse(product.isEnabled());
    }

    @Test
    public void Product_IncreaseBalance_ShouldNotThrowException()
    {
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "Product",
                "",
                BigDecimal.valueOf(1),
                "",
                category
        );
        Assertions.assertDoesNotThrow(() -> product.increaseBalance(2));

    }

    @Test
    public void Product_IncreaseBalance_ShouldThrowException()
    {
        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "Product",
                "",
                BigDecimal.valueOf(1),
                "",
                category
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> product.increaseBalance(-2));

    }

    @Test
    public void Product_DecreaseBalance_ShouldThrowException()
    {

        Category category = Mockito.mock(Category.class);
        Product product = Product.create(
                "Product",
                "",
                BigDecimal.valueOf(1),
                "",
                category
        );
        product.increaseBalance(2);
        Assertions.assertThrows(NotEnoughProductException.class, () -> product.decreaseBalance(product.getBalance() + 1));
    }

}
