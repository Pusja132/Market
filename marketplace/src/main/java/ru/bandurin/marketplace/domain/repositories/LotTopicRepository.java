package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic;

public interface LotTopicRepository extends JpaRepository<LotTopic, Long> {
}
