package ru.bandurin.marketplace.payload.marketplace.lot;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotTopicDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> lotIds;
}
