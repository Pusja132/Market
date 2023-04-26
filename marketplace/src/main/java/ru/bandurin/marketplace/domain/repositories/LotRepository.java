package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByLotTopic(LotTopic lotTopic);
    List<Lot> findAllByAuthor(User user);
}
