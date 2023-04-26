package ru.bandurin.marketplace.service.order;

import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.payload.order.OrderCostReservationDto;

public interface OrderCostReservationService {
    OrderCostReservation getEntity(Long id);

    OrderCostReservationDto get(Long id);
}
