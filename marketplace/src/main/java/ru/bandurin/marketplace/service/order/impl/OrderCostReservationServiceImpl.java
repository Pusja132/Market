package ru.bandurin.marketplace.service.order.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation_;
import ru.bandurin.marketplace.domain.mapper.OrderCostReservationMapper;
import ru.bandurin.marketplace.domain.repositories.OrderCostReservationRepository;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.payload.order.OrderCostReservationDto;
import ru.bandurin.marketplace.service.order.OrderCostReservationService;

import java.util.Map;

@Service
@RequiredArgsConstructor
class OrderCostReservationServiceImpl implements OrderCostReservationService {
    private final OrderCostReservationRepository repository;
    private final OrderCostReservationMapper mapper;

    @Override
    public OrderCostReservation getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        OrderCostReservation.class.toString(),
                        Map.of(OrderCostReservation_.ID, id)
                ));
    }

    @Override
    public OrderCostReservationDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }
}
