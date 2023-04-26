package ru.bandurin.marketplace.payload.order;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCostReservationDto {
    private Long id;
    private String orderCostReservationStatus;
    private Long orderId;
}
