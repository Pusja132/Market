package ru.bandurin.marketplace.payload.order.lot;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLotProcessDto {
    private Long id;
    private Boolean isOrderCostReservationReady;
    private Boolean isVendorReady;
    private Boolean isCustomerReady;
    private String orderProcessStatus;
}
