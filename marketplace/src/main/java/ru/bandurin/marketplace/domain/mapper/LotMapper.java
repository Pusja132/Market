package ru.bandurin.marketplace.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.payload.marketplace.lot.LotDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotTopicService;
import ru.bandurin.marketplace.service.user.UserService;

@Component
@RequiredArgsConstructor
public class LotMapper {
    private final UserService userService;
    private final LotTopicService lotTopicService;

    public Lot getEntity(LotDto dto) {
        return Lot.builder()
                .id(null)
                .lotTopic(lotTopicService.getEntity(dto.getLotTopicId()))
                .name(dto.getName())
                .author(userService.getSelf())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }

    public LotDto getDto(Lot entity) {
        return LotDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .lotTopicId(entity.getLotTopic().getId())
                .price(entity.getPrice())
                .userId(entity.getAuthor().getId())
                .build();
    }

    public void update(Lot entity, LotDto dto) {
        if (dto.getName() != null
                && !dto.getName().isBlank()
                && !dto.getName().equals(entity.getName())
        ) {
            entity.setName(dto.getName());
        }

        if (dto.getDescription() != null
                && !dto.getDescription().isBlank()
                && !dto.getDescription().equals(entity.getDescription())
        ) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getPrice() != null && !dto.getPrice().equals(entity.getPrice())) {
            entity.setPrice(dto.getPrice());
        }
    }
}
