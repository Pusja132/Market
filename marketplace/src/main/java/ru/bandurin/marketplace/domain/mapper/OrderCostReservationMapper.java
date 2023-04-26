package ru.bandurin.marketplace.domain.mapper;

import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.payload.order.OrderCostReservationDto;

@Component
public class OrderCostReservationMapper {
    public OrderCostReservationDto toDto(OrderCostReservation entity) {
        return new OrderCostReservationDto(
                entity.getId(),
                entity.getOrderCostReservationStatus().name(),
                entity.getOrder().getId()
        );
    }


}
