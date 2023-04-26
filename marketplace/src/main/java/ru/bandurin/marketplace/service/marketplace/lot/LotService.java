package ru.bandurin.marketplace.service.marketplace.lot;

import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.payload.marketplace.lot.LotDto;

import java.util.List;

public interface LotService {
    Lot getEntity(Long id);
    List<Lot> getEntitiesByLotTopic(Long topicId);
    List<Lot> getEntitiesByUser(Long userId);

    LotDto get(Long id);
    List<LotDto> getByLotTopic(Long topicId);
    List<LotDto> getByUser(Long userId);
    LotDto create(LotDto dto);
    LotDto update(Long id, LotDto dto);

    void delete(Long id);
}
