package ru.bandurin.marketplace.service.chat;

import ru.bandurin.marketplace.domain.entities.chat.ChatMessage;
import ru.bandurin.marketplace.payload.ValueDto;
import ru.bandurin.marketplace.payload.chat.ChatMessageDto;

import java.util.List;

public interface ChatMessageService {
    ChatMessage getEntity(Long id);
    ChatMessageDto get(Long id);
    List<ChatMessage> getEntityByChat(Long chatId);
    List<ChatMessageDto> getByChat(Long chatId);
    void send(Long chatId, ValueDto<String> dto);
}
