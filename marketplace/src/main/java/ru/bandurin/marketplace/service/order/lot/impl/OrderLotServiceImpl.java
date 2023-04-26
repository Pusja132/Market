package ru.bandurin.marketplace.service.order.lot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.domain.entities.order.OrderCostReservation;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot_;
import ru.bandurin.marketplace.domain.repositories.OrderLotRepository;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.mapper.OrderLotMapper;
import ru.bandurin.marketplace.payload.order.lot.OrderLotDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotService;
import ru.bandurin.marketplace.service.order.lot.OrderLotService;
import ru.bandurin.marketplace.service.user.UserService;

import javax.security.sasl.AuthenticationException;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
class OrderLotServiceImpl implements OrderLotService {
    private final OrderLotRepository repository;
    private final OrderLotMapper mapper;
    private final UserService userService;
    private final LotService lotService;

    @Override
    public OrderLot getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        OrderLot.class.toString(),
                        Map.of(OrderLot_.ID, id)
                ));
    }

    public Set<OrderLot> getSelf() {
        User currentUser = userService.getSelf();

        return repository.findByOrder_Customer(currentUser);
    }

    @Override
    public OrderLotDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Override
    @Transactional
    public OrderLotDto create(OrderLotDto dto) {
        User currentUser = userService.getSelf();

        if (currentUser.getId()
                .equals(lotService.getEntity(dto.getLotId())
                        .getAuthor()
                        .getId())) {
            throw new RuntimeException("CUSTOMER IS A AUTHOR OF LOT");
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void confirmReservation(Long id) throws AuthenticationException {
        OrderLot orderLot = getEntity(id);

        if (orderLot.getOrderProcessStatus() != OrderLot.OrderProcessStatus.AWAIT_RESERVATION) {
            throw new RuntimeException("WRONG STATUS");
        }

        if (orderLot.getOrder().getOrderCostReservation().getOrderCostReservationStatus() != OrderCostReservation.OrderCostReservationStatus.READY) {
            throw new RuntimeException("COST RESERVATION NOT READY");
        }

        User currentUser = userService.getSelf();

        if (orderLot.getOrder()
                .getCustomer()
                .getId()
                .equals(currentUser.getId())) {
            orderLot.setOrderProcessStatus(OrderLot.OrderProcessStatus.AWAIT_VENDOR);
        } else {
            throw new AuthenticationException("FORBIDDEN");
        }
        repository.save(orderLot);
    }

    @Override
    public void confirmVendorReady(Long id) throws AuthenticationException {
        OrderLot orderLot = getEntity(id);

        if (orderLot.getOrderProcessStatus() != OrderLot.OrderProcessStatus.AWAIT_VENDOR) {
            throw new RuntimeException("WRONG STATUS");
        }

        User currentUser = userService.getSelf();

        if (orderLot.getLot()
                .getAuthor()
                .getId()
                .equals(currentUser.getId())) {
            orderLot.setOrderProcessStatus(OrderLot.OrderProcessStatus.AWAIT_CUSTOMER);
            repository.save(orderLot);
        } else {
            throw new AuthenticationException("FORBIDDEN");
        }
    }

    @Override
    public void confirmCustomerReady(Long id) throws AuthenticationException {
        OrderLot orderLot = getEntity(id);

        if (orderLot.getOrderProcessStatus() != OrderLot.OrderProcessStatus.AWAIT_CUSTOMER) {
            throw new RuntimeException("WRONG STATUS");
        }

        User currentUser = userService.getSelf();

        if (orderLot.getOrder()
                .getCustomer()
                .getId()
                .equals(currentUser.getId())) {
            orderLot.setOrderProcessStatus(OrderLot.OrderProcessStatus.FINISHED);
            orderLot.getOrder()
                    .setOrderStatus(Order.OrderStatus.FINISHED);
            orderLot.getOrder().getOrderCostReservation()
                    .setOrderCostReservationStatus(OrderCostReservation.OrderCostReservationStatus.FINISHED);
            repository.save(orderLot);
        } else {
            throw new AuthenticationException("FORBIDDEN");
        }
    }

    @Override
    public void pauseAndCallAdmin(Long id) throws AuthenticationException {
        OrderLot orderLot = getEntity(id);

        if (orderLot.getOrderProcessStatus() != OrderLot.OrderProcessStatus.FINISHED
                || orderLot.getOrderProcessStatus() != OrderLot.OrderProcessStatus.CANCELLED) {
            throw new RuntimeException("WRONG STATUS");
        }

        User currentUser = userService.getSelf();

        if (orderLot.getOrder()
                .getCustomer()
                .getId()
                .equals(currentUser.getId())
                || orderLot.getLot()
                .getAuthor()
                .getId()
                .equals(currentUser.getId())) {
            orderLot.setOrderProcessStatus(OrderLot.OrderProcessStatus.AWAIT_SUPPORT);
            repository.save(orderLot);
        } else {
            throw new AuthenticationException("FORBIDDEN");
        }
    }

    @Override
    public void cancel(Long id) throws AuthenticationException {
        OrderLot orderLot = getEntity(id);

        if (orderLot.getOrderProcessStatus() == OrderLot.OrderProcessStatus.FINISHED
                || orderLot.getOrderProcessStatus() == OrderLot.OrderProcessStatus.CANCELLED) {
            throw new RuntimeException("ALREADY FINISHED");
        }

        if (orderLot.getOrderProcessStatus() == OrderLot.OrderProcessStatus.AWAIT_CUSTOMER) {
            throw new AuthenticationException("FORBIDDEN");
        }

        User currentUser = userService.getSelf();

        if (orderLot.getOrder()
                .getCustomer()
                .getId()
                .equals(currentUser.getId())
                || orderLot.getLot()
                .getAuthor()
                .getId()
                .equals(currentUser.getId())) {
            orderLot.setOrderProcessStatus(OrderLot.OrderProcessStatus.CANCELLED);
            orderLot.getOrder()
                    .setOrderStatus(Order.OrderStatus.CANCELLED);
            orderLot.getOrder()
                    .getOrderCostReservation()
                    .setOrderCostReservationStatus(OrderCostReservation.OrderCostReservationStatus.CANCELLED);
            repository.save(orderLot);
        } else {
            throw new AuthenticationException("FORBIDDEN");
        }
    }
}
