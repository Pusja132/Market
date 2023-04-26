package ru.bandurin.marketplace.domain.entities.order.lot;

import jakarta.persistence.*;
import lombok.*;
import ru.bandurin.marketplace.domain.entities.order.Order;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_lots")
public class OrderLot {
    @Id
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id",nullable = false)
    private Lot lot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderProcessStatus orderProcessStatus;

    public enum OrderProcessStatus {
        AWAIT_RESERVATION, // ожидание резезрвации суммы(первый шаг ордера)
        AWAIT_VENDOR, // ожидание подтверждения выполнения услуги вендора
        AWAIT_CUSTOMER, // ожидания подтверждения заказчика о выполнении услуги
        FINISHED, // завершено
        CANCELLED, // отменено (только до статуса await_customer
        AWAIT_SUPPORT // ожидание ответа поддержки
    }
}
