package ru.bandurin.marketplace.payload.order;

import lombok.*;
import ru.bandurin.marketplace.payload.chat.ChatDto;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long customerId;
    private LocalDateTime date;
    private String customerDescription;
    private Double finalCost;
    private Long orderCostReservationId;
    private String orderStatus;
    private String orderType;
    private Long orderChatId;
}
