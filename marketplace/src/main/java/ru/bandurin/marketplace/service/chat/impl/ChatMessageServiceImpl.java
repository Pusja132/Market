package ru.bandurin.marketplace.service.chat.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.chat.ChatMessage;
import ru.bandurin.marketplace.domain.entities.chat.ChatMessage_;
import ru.bandurin.marketplace.domain.entities.security.Permission;
import ru.bandurin.marketplace.domain.mapper.ChatMessageMapper;
import ru.bandurin.marketplace.domain.repositories.ChatMessageRepository;
import ru.bandurin.marketplace.core.exception.AuthenticationException;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.payload.ValueDto;
import ru.bandurin.marketplace.payload.chat.ChatMessageDto;
import ru.bandurin.marketplace.service.chat.ChatMessageService;
import ru.bandurin.marketplace.service.chat.ChatService;
import ru.bandurin.marketplace.service.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatMessageMapper mapper;
    private final ChatService chatService;
    private final UserService userService;
    
    @Override
    public ChatMessage getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                ChatMessage.class.getName(),
                Map.of(ChatMessage_.ID, id)
        ));
    }

    @Override
    public ChatMessageDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Override
    public List<ChatMessage> getEntityByChat(Long chatId) {
        User currentUser = userService.getSelf();
        Chat chat = chatService.getEntity(chatId);

        checkIsUserHasPermissions(currentUser, chat);

        return repository.findAllByChat(chat);
    }

    @Override
    public List<ChatMessageDto> getByChat(Long chatId) {
        return getEntityByChat(chatId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void send(Long chatId, ValueDto<String> value) {
        User currentUser = userService.getSelf();
        Chat chat = chatService.getEntity(chatId);
        checkIsUserHasPermissions(currentUser, chat);

        ChatMessage chatMessage = new ChatMessage(
                null,
                currentUser,
                value.getValue(),
                chat
        );

        repository.save(chatMessage);
    }

    private void checkIsUserHasPermissions(User user, Chat chat) {
        if (!user.hasAuthority(Permission.PermissionName.SUPPORT)
                && !user.getId().equals(chat.getInitiator().getId())
                && !user.getId().equals(chat.getReceiver().getId())) {
            throw new AuthenticationException();
        }
    }
}
