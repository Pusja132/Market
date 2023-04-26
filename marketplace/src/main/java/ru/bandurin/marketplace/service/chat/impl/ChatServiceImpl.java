package ru.bandurin.marketplace.service.chat.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.chat.Chat;
import ru.bandurin.marketplace.domain.entities.chat.Chat_;
import ru.bandurin.marketplace.domain.entities.order.lot.OrderLot;
import ru.bandurin.marketplace.domain.entities.security.Permission;
import ru.bandurin.marketplace.domain.mapper.ChatMapper;
import ru.bandurin.marketplace.domain.repositories.ChatRepository;
import ru.bandurin.marketplace.core.exception.AuthenticationException;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.payload.chat.ChatDto;
import ru.bandurin.marketplace.service.chat.ChatService;
import ru.bandurin.marketplace.service.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ChatServiceImpl implements ChatService {
    private final ChatRepository repository;
    private final ChatMapper mapper;
    private final UserService userService;

    @Override
    public Chat getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        Chat.class.toString(),
                        Map.of(
                                Chat_.ID,
                                id
                        )
                ));
    }

    @Override
    public ChatDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Override
    public List<Chat> getEntities(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<Chat> getSelf() {
        User currentUser = userService.getSelf();
        List<Chat> initiatorChats = repository.findAllByInitiator(currentUser);
        List<Chat> receiverChats = repository.findAllByReceiver(currentUser);
        List<Chat> result = new ArrayList<>();


        if (currentUser.hasAuthority(Permission.PermissionName.SUPPORT)) {
            result.addAll(repository.findAllBySupport(currentUser));
        }

        result.addAll(initiatorChats);
        result.addAll(receiverChats);

        return result;
    }

    @Override
    public List<ChatDto> getSelfDto() {
        return getSelf().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChatDto create(ChatDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void connectToChat(Long id) {
        User currentUser = userService.getSelf();
        
        if (!currentUser.hasAuthority(Permission.PermissionName.SUPPORT)) {
            throw new AuthenticationException();
        }

        Chat chat = getEntity(id);

        if (chat.getOrder().getOrderLot().getOrderProcessStatus() == OrderLot.OrderProcessStatus.AWAIT_SUPPORT) {
            chat.setSupport(currentUser);
        } else {
            throw new RuntimeException("ORDER LOT DONT AWAIT SUPPORT");
        }

        repository.save(chat);
    }

    @Override
    public void delete(Collection<Long> ids) {
        User currentUser = userService.getSelf();
        List<Chat> chats = getEntities(ids);


        for (Chat chat : chats) {
            if (!chat.getInitiator().getId().equals(currentUser.getId())
                    && !chat.getReceiver().getId().equals(currentUser.getId())
                    && !currentUser.hasAuthority(Permission.PermissionName.SUPPORT) ) {
                throw new AuthenticationException();
            }
        }

        repository.deleteAllById(ids);
    }
}
