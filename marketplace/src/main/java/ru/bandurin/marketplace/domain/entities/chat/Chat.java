package ru.bandurin.marketplace.domain.entities.chat;

import jakarta.persistence.*;
import lombok.*;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.order.Order;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_id")
    private User support;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;

    @OneToOne(mappedBy = "orderChat")
    private Order order;

}
