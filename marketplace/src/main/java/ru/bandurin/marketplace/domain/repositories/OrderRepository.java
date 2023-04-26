package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User user);
}
