package ru.bandurin.marketplace.domain.entities.order;

import jakarta.persistence.*;
import lombok.*;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User customer;

    @Column(nullable = false)
    private LocalDateTime date;

    private String customerDescription;

    @Column(nullable = false)
    private Double finalCost;

    @OneToOne(mappedBy = "order")
    private OrderCostReservation orderCostReservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @OneToOne(mappedBy = "order")
    private OrderLot orderLot;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_chat_id", nullable = false)
    private Chat orderChat;

    public enum OrderStatus {
        IN_PROGRESS,
        FINISHED,
        CANCELLED
    }

    public enum OrderType {
        SERVICE
    }
}
