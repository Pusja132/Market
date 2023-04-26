package ru.bandurin.marketplace.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.chat.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChat(Chat chat);
}
