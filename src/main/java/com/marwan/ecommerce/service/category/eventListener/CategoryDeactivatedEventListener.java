package com.marwan.ecommerce.service.category.eventListener;

import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.event.CategoryDeactivatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDeactivatedEventListener
{
    private final ProductRepository productRepository;

    @EventListener
    public void onCategoryDeactivatedEvent(CategoryDeactivatedEvent event)
    {
        List<Product> productList = productRepository.findByCategoryId(event.categoryId());
        productList.forEach(product -> {
            product.setCategoryId(null);
        });
        productRepository.saveAll(productList);
    }

}
