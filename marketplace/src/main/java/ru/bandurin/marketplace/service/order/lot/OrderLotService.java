package ru.bandurin.marketplace.service.order.lot;

import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;
import ru.bandurin.marketplace.payload.order.lot.OrderLotDto;

import javax.security.sasl.AuthenticationException;

public interface OrderLotService {
    OrderLot getEntity(Long id);

    OrderLotDto get(Long id);
    OrderLotDto create(OrderLotDto dto);

    void confirmReservation(Long id) throws AuthenticationException;
    void confirmVendorReady(Long id) throws AuthenticationException;
    void confirmCustomerReady(Long id) throws AuthenticationException;
    void pauseAndCallAdmin(Long id) throws AuthenticationException;
    void cancel(Long id) throws AuthenticationException;
}
