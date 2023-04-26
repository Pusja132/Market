package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;

public interface OrderCostReservationRepository extends JpaRepository<OrderCostReservation, Long> {
}
