package ru.bandurin.marketplace.service.order.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.domain.entities.order.Order_;
import ru.bandurin.marketplace.domain.repositories.OrderRepository;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.mapper.OrderMapper;
import ru.bandurin.marketplace.payload.order.OrderDto;
import ru.bandurin.marketplace.service.order.OrderService;
import ru.bandurin.marketplace.service.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserService userService;

    @Override
    public Order getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        Order.class.toString(),
                        Map.of(Order_.ID, id)
                ));
    }

    @Override
    public List<Order> getEntitiesByUser(Long id) {
        return repository.findAllByCustomer(userService.getEntity(id));
    }

    @Override
    public OrderDto get(Long id) {
        return mapper.getDto(getEntity(id));
    }

    @Override
    public List<OrderDto> getByUser(Long id) {
        return getEntitiesByUser(id).stream()
                .map(mapper::getDto)
                .collect(Collectors.toList());
    }
}
