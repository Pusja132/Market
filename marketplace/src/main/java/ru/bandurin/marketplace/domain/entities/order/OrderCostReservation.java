package ru.bandurin.marketplace.domain.entities.order;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_cost_reservations")
public class OrderCostReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderCostReservationStatus orderCostReservationStatus;

    public enum OrderCostReservationStatus {
        AWAIT, // ожидание денег от покупателя
        READY, // деньги зарезервированы
        FINISHED, // деньги были переведены
        CANCELLED // резервация отменена
    }
}
