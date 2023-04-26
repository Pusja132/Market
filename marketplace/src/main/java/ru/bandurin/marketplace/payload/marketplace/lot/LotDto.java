package ru.bandurin.marketplace.payload.marketplace.lot;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotDto {
    private Long id;
    private String name;
    private String description;
    private Long lotTopicId;
    private Long userId;
    private Double price;
}
