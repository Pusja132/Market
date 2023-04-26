package ru.bandurin.marketplace.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.chat.ChatMessage;
import ru.bandurin.marketplace.payload.chat.ChatDto;
import ru.bandurin.marketplace.service.user.UserService;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final UserService userService;

    public ChatDto toDto(Chat entity) {
        return ChatDto.builder()
                .id(entity.getId())
                .receiverId(entity.getReceiver().getId())
                .initiatorId(entity.getInitiator().getId())
                .supportId(entity.getSupport() == null ? null : entity.getSupport().getId())
                .messages(entity.getChatMessages()
                        .stream()
                        .map(ChatMessage::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Chat toEntity(ChatDto dto) {
        return Chat.builder()
                .id(null)
                .receiver(userService.getEntity(dto.getReceiverId()))
                .initiator(userService.getEntity(dto.getInitiatorId()))
                .support(dto.getSupportId() == null ? null : userService.getEntity(dto.getSupportId()))
                .chatMessages(new ArrayList<>())
                .build();
    }
}
