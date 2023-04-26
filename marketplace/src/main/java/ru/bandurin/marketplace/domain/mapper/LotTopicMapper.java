package ru.bandurin.marketplace.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.LotTopic;
import ru.bandurin.marketplace.domain.repositories.LotRepository;
import ru.bandurin.marketplace.payload.marketplace.lot.LotTopicDto;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LotTopicMapper {
    private final LotRepository lotRepository;

    public LotTopic getEntity(LotTopicDto dto) {
        return LotTopic.builder()
                .id(null)
                .name(dto.getName())
                .description(dto.getDescription())
                .lots(new ArrayList<>())
                .build();
    }

    public LotTopicDto getDto(LotTopic entity) {
        return LotTopicDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .lotIds(entity.getLots()
                        .stream()
                        .map(Lot::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public void update(LotTopic entity, LotTopicDto dto) {
        if (dto.getName() != null && !dto.getName().isBlank() && !dto.getName().equals(entity.getName())) {
            entity.setName(dto.getName());
        }

        if (dto.getDescription() != null
                && !dto.getDescription().isBlank()
                && !dto.getDescription().equals(entity.getDescription())
        ) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getLotIds() != null) {
            entity.setLots(lotRepository.findAllById(dto.getLotIds()));
        }
    }
}
