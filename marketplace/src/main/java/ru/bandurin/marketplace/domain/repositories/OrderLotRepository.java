package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;

import java.util.Set;

public interface OrderLotRepository extends JpaRepository<OrderLot, Long> {
    Set<OrderLot> findByOrder_Customer(User orderCustomer);
}
