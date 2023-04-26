package ru.bandurin.marketplace.service.chat;

import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.payload.chat.ChatDto;

import java.util.Collection;
import java.util.List;

public interface ChatService {
    Chat getEntity(Long id);

    ChatDto get(Long id);

    List<Chat> getEntities(Collection<Long> ids);

    List<Chat> getSelf();

    List<ChatDto> getSelfDto();

    ChatDto create(ChatDto dto);

    void connectToChat(Long id);

    void delete(Collection<Long> ids);
}
