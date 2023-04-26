package ru.bandurin.marketplace.service.marketplace.lot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic_;
import ru.bandurin.marketplace.domain.repositories.LotTopicRepository;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.mapper.LotTopicMapper;
import ru.bandurin.marketplace.payload.marketplace.lot.LotTopicDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotTopicService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class LotTopicServiceImpl implements LotTopicService {
    private final LotTopicRepository repository;
    private final LotTopicMapper mapper;

    @Override
    public LotTopic getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                LotTopic.class.toString(),
                Map.of(LotTopic_.ID, id)
        ));
    }

    @Override
    public List<LotTopic> getAllEntities() {
        return repository.findAll();
    }

    @Override
    public LotTopicDto get(Long id) {
        return mapper.getDto(getEntity(id));
    }

    @Override
    public List<LotTopicDto> getAll() {
        return getAllEntities().stream()
                .map(mapper::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public LotTopicDto create(LotTopicDto dto) {
        return mapper.getDto(repository.save(mapper.getEntity(dto)));
    }

    @Override
    public LotTopicDto update(Long id, LotTopicDto dto) {
        LotTopic entity = getEntity(id);
        mapper.update(entity, dto);

        return mapper.getDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
