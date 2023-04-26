package ru.bandurin.marketplace.service.marketplace.lot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot_;
import ru.bandurin.marketplace.domain.entities.security.Permission;
import ru.bandurin.marketplace.domain.repositories.LotRepository;
import ru.bandurin.marketplace.core.exception.AuthenticationException;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.mapper.LotMapper;
import ru.bandurin.marketplace.payload.marketplace.lot.LotDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotService;
import ru.bandurin.marketplace.service.marketplace.lot.LotTopicService;
import ru.bandurin.marketplace.service.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class LotServiceImpl implements LotService {
    private final LotRepository repository;
    private final LotMapper mapper;
    private final LotTopicService lotTopicService;
    private final UserService userService;

    @Override
    public Lot getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                Lot.class.toString(), Map.of(Lot_.ID, id)
        ));
    }

    @Override
    public List<Lot> getEntitiesByLotTopic(Long topicId) {
        return repository.findAllByLotTopic(lotTopicService.getEntity(topicId));
    }

    @Override
    public List<Lot> getEntitiesByUser(Long userId) {
        return repository.findAllByAuthor(userService.getEntity(userId));
    }

    @Override
    public LotDto get(Long id) {
        return mapper.getDto(getEntity(id));
    }

    @Override
    public List<LotDto> getByLotTopic(Long topicId) {
        return getEntitiesByLotTopic(topicId)
                .stream()
                .map(mapper::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LotDto> getByUser(Long userId) {
        return getEntitiesByUser(userId).stream()
                .map(mapper::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public LotDto create(LotDto dto) {
        return mapper.getDto(repository.save(mapper.getEntity(dto)));
    }

    @Override
    public LotDto update(Long id, LotDto dto) {
        Lot lot = getEntity(id);
        checkIfUserIsAuthorOrAdmin(lot);
        mapper.update(lot, dto);

        return mapper.getDto(repository.save(lot));
    }

    @Override
    public void delete(Long id) {
        checkIfUserIsAuthorOrAdmin(getEntity(id));

        repository.deleteById(id);
    }

    private void checkIfUserIsAuthorOrAdmin(Lot lot) {
        User currentUser = userService.getSelf();

        if (!currentUser.getId().equals(lot.getAuthor().getId())
                && currentUser.hasAuthority(Permission.PermissionName.SUPPORT)) {
            throw new AuthenticationException(); // TODO xyita
        }
    }
}
