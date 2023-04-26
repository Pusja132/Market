package ru.bandurin.marketplace.payload.order.lot;

import lombok.*;
import ru.bandurin.marketplace.payload.order.OrderDto;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLotDto {
    private Long id;
    private OrderDto orderDto;
    private Long lotId;
    private String OrderLotStatus;
}
