package ru.bandurin.marketplace.service.order;

import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.payload.order.OrderDto;

import java.util.List;

public interface OrderService {
    Order getEntity(Long id);
    List<Order> getEntitiesByUser(Long id);

    OrderDto get(Long id);
    List<OrderDto> getByUser(Long id);
}
