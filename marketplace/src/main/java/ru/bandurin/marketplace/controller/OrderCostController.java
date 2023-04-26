package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bandurin.marketplace.payload.order.OrderCostReservationDto;
import ru.bandurin.marketplace.service.order.OrderCostReservationService;

@RestController
@RequestMapping("/api/v1/order/cost")
@RequiredArgsConstructor
public class OrderCostController {
    private final OrderCostReservationService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrderCostReservationDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }
}
