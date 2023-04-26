package ru.bandurin.marketplace.domain.entities.marketplace.lot;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lot_topics")
public class LotTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "lotTopic")
    private List<Lot> lots;
}
