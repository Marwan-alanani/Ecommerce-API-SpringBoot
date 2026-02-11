package com.marwan.ecommerce.config;

import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.repository.CategoryRepository;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevDataConfig
{

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    CommandLineRunner seedUserData(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        return args -> {
            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = User.create(
                        "admin",
                        "admin",
                        UserRole.ADMIN,
                        adminEmail,
                        passwordEncoder.encode(adminPassword)
                );

                User user = User.create(
                        "user",
                        "user",
                        UserRole.USER,
                        "defaultuser@mail.com",
                        passwordEncoder.encode("password")
                );
                userRepository.save(admin);
                userRepository.save(user);
            }
        };
    }

    @Bean
    CommandLineRunner seedCategoryData(CategoryService categoryService,
            CategoryRepository categoryRepository)
    {
        return args -> {
            if (categoryRepository.count() == 0) {
                List<CreateCategoryCommand> commands = new ArrayList<>(List.of(
                        new CreateCategoryCommand("Furniture"),
                        new CreateCategoryCommand("Fashion"),
                        new CreateCategoryCommand("Electronics")
                )

                );

                for (CreateCategoryCommand command : commands) {
                    categoryService.create(command);
                }
            }
        };
    }

    @Bean
    CommandLineRunner seedProductData(ProductRepository productRepository,
            CategoryRepository categoryRepository)
    {
        return args -> {
            if (productRepository.count() == 0) {
                Category furniture = categoryRepository.findByName("Furniture").orElse(null);
                Category fashion = categoryRepository.findByName("Fashion").orElse(null);
                Category electronics = categoryRepository.findByName("Electronics").orElse(null);

                List<Product> products = new ArrayList<>(List.of(
                        Product.create(
                                "Bed",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic" +
                                        ".com/images?q=tbn:ANd9GcSAjEccafdE29oPjq8fXKg2K0HM6_x63WTL9g&s",
                                furniture
                        ),
                        Product.create(
                                "Drawer",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTt_B7WVTx-g0xk63Y5NyCOdtufTn6MtTmGUg&s",
                                furniture
                        ),

                        Product.create(
                                "Desk",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://www.officestock.com.au/assets/full/CDK126BI.jpg?20210405235659",
                                furniture
                        ),
                        Product.create(
                                "T-shirt",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic" +
                                        ".com/images?q=tbn:ANd9GcSuFDcsZpcD7gF-zD8NQU_N7SSzoOQKrXvThQ&s",
                                fashion
                        ),

                        Product.create(
                                "Jeans",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2rpOb-4Qw9zEYHDdo3_N7tYczkwZzO4LLrQ&s",
                                fashion
                        ),

                        Product.create(
                                "Jacket",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRS9yaUixpCxwbJIHxVgr-7imMUuAlq3J_-DQ&s",
                                fashion
                        ),
                        Product.create(
                                "TV",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5uPThN2OG8iYPzlCOEmGCvGqy2euKbBVTRQ&s",
                                electronics
                        ),
                        Product.create(
                                "Mobile Phone",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://2b.com.eg/media/catalog/product/cache/45bcba66b667d1ca52af48b101a5f0cb/a/p/apple-iphone-16-pro-black-1_1_1_2_1.jpg",
                                electronics
                        ),
                        Product.create(
                                "Laptop",
                                "",
                                BigDecimal.valueOf(10.2),
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxd9zq7NatrST5edevjeknTKTKmuL5ElwDag&s",
                                electronics
                        )

                ));
                for (Product product : products) {
                    productRepository.save(product);
                }
            }
        };
    }

}
