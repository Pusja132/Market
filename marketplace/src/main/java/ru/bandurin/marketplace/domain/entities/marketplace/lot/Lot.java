package ru.bandurin.marketplace.domain.entities.marketplace.lot;

import jakarta.persistence.*;
import lombok.*;
import ru.bandurin.marketplace.domain.entities.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private LotTopic lotTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User author;

    @Column(nullable = false)
    private Double price;
}
