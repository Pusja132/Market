package ru.bandurin.marketplace.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;
import ru.bandurin.marketplace.domain.repositories.OrderCostReservationRepository;
import ru.bandurin.marketplace.domain.repositories.OrderRepository;
import ru.bandurin.marketplace.payload.order.lot.OrderLotDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotService;
import ru.bandurin.marketplace.service.order.OrderService;

@Component
@RequiredArgsConstructor
public class OrderLotMapper {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final LotService lotService;
    private final OrderCostReservationRepository orderCostReservationRepository;

    public OrderLotDto toDto(OrderLot orderLot) {
        return new OrderLotDto(
                orderLot.getId(),
                orderService.get(orderLot.getOrder().getId()),
                orderLot.getLot().getId(),
                orderLot.getOrderProcessStatus().name()
        );
    }

    public OrderLot toEntity(OrderLotDto dto) {
        Lot lot = lotService.getEntity(dto.getLotId());
        Order order = orderMapper.toEntity(dto.getOrderDto(), lot.getAuthor().getId());
        orderRepository.save(order);
        OrderCostReservation orderCostReservation = OrderCostReservation.builder() // TODO это не работает
                .id(null)
                .orderCostReservationStatus(OrderCostReservation.OrderCostReservationStatus.AWAIT)
                .order(order)
                .build();
        orderCostReservationRepository.save(orderCostReservation);


        return new OrderLot(
                null,
                order,
                lot,
                OrderLot.OrderProcessStatus.AWAIT_RESERVATION
        );
    }

    public void update(OrderLot entity, OrderLotDto dto) {
        if (dto.getOrderLotStatus() != null) {
            entity.setOrderProcessStatus(OrderLot.OrderProcessStatus.valueOf(dto.getOrderLotStatus()));
        }
    }
}
