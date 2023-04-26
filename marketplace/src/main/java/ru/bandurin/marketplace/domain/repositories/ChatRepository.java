package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.chat.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByInitiator(User initiator);
    List<Chat> findAllByReceiver(User receiver);
    List<Chat> findAllBySupport(User support);
}
