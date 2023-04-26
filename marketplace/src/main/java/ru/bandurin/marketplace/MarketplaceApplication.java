package ru.bandurin.marketplace;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bandurin.marketplace.domain.entities.security.Role;

@SpringBootApplication
@RequiredArgsConstructor
public class MarketplaceApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
