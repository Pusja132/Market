package ru.bandurin.marketplace.service.marketplace.lot;

import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic;
import ru.bandurin.marketplace.payload.marketplace.lot.LotTopicDto;

import java.util.List;

public interface LotTopicService {
    LotTopic getEntity(Long id);
    List<LotTopic> getAllEntities();

    LotTopicDto get(Long id);
    List<LotTopicDto> getAll();
    LotTopicDto create(LotTopicDto dto);
    LotTopicDto update(Long id, LotTopicDto dto);

    void delete(Long id);
}
