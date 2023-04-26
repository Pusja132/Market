package ru.bandurin.marketplace.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.domain.repositories.ChatRepository;
import ru.bandurin.marketplace.domain.repositories.OrderCostReservationRepository;
import ru.bandurin.marketplace.payload.order.OrderDto;
import ru.bandurin.marketplace.service.order.OrderCostReservationService;
import ru.bandurin.marketplace.service.user.UserService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final UserService userService;
    private final OrderCostReservationService orderCostReservationService;
    private final ChatRepository chatRepository;

    public OrderDto getDto(Order entity) {
        return OrderDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer().getId())
                .customerDescription(entity.getCustomerDescription())
                .finalCost(entity.getFinalCost())
                .orderType(entity.getOrderType().name())
                .orderStatus(entity.getOrderStatus().name())
                .date(entity.getDate())
//                .orderCostReservationId(entity.getOrderCostReservation().getId())
                .orderChatId(entity.getOrderChat().getId())
                .build();
    }

    public Order toEntity(OrderDto dto, Long receiverId) {
        User customer = userService.getSelf();
        Chat orderChat = new Chat(
                null,
                userService.getEntity(receiverId),
                customer,
                null,
                null,
                null);
        chatRepository.save(orderChat);

        return Order.builder()
                .id(null)
                .customer(customer)
                .customerDescription(dto.getCustomerDescription())
                .finalCost(dto.getFinalCost())
                .date(LocalDateTime.now())
                .orderType(Order.OrderType.valueOf(dto.getOrderType()))
                .orderStatus(Order.OrderStatus.IN_PROGRESS)
                .orderCostReservation(null)
                .orderChat(orderChat)
                .build();
    }
}
