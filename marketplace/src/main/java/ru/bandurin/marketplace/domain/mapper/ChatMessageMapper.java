package ru.bandurin.marketplace.domain.mapper;

import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.chat.ChatMessage;
import ru.bandurin.marketplace.payload.chat.ChatMessageDto;

@Component
public class ChatMessageMapper {
    public ChatMessageDto toDto(ChatMessage chatMessage) {
        return new ChatMessageDto(
                chatMessage.getId(),
                chatMessage.getSender().getId(),
                chatMessage.getText(),
                chatMessage.getChat().getId()
        );
    }
}
