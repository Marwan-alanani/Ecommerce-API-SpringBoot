package com.marwan.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    // this is for data seeding
//    @Bean
//    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder)
//    {
//        return args -> {
//            userRepository.save(User.create(
//                    "Admin",
//                    "Admin",
//                    UserRole.ADMIN,
//                    "admin@mail.com",
//                    passwordEncoder.encode("password"))
//            );
//        };
//    }
    //
    //            userRepository.save(User.create(
    //                    "Mazen",
    //                    "Walid",
    //                    UserRole.USER,
    //                    "mazen@mail.com",
    //                    passwordEncoder.encode("password"))
    //            );
    //            userRepository.save(User.create(
    //                    "Malik",
    //                    "Walid",
    //                    UserRole.USER,
    //                    "malik@mail.com",
    //                    passwordEncoder.encode("password"))
    //            );
    //        };
    //    }

}
