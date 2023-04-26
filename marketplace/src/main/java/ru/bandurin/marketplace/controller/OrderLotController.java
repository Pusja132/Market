package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.order.lot.OrderLotDto;
import ru.bandurin.marketplace.service.order.lot.OrderLotService;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/v1/lot/order/lot")
@RequiredArgsConstructor
public class OrderLotController {
    private final OrderLotService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrderLotDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderLotDto> create(@RequestBody OrderLotDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PostMapping("{id}/reservationconfirm")
    public ResponseEntity<Void> confirmReservation(@PathVariable Long id) throws AuthenticationException {
        service.confirmReservation(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/customerready")
    public ResponseEntity<Void> confirmCustomerReady(@PathVariable Long id) throws AuthenticationException {
        service.confirmCustomerReady(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/vendorready")
    public ResponseEntity<Void> confirmVendorReady(@PathVariable Long id) throws AuthenticationException {
        service.confirmVendorReady(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/pause")
    public ResponseEntity<Void> pauseAndCallAdmin(@PathVariable Long id) throws AuthenticationException {
        service.pauseAndCallAdmin(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) throws AuthenticationException {
        service.cancel(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
