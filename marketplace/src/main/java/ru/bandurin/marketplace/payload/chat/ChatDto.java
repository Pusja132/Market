package ru.bandurin.marketplace.payload.chat;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {
    private Long id;
    private Long userId;
    private Long receiverId;
    private Long initiatorId;
    private Long supportId;
    private List<Long> messages;
    private Long orderId;
}
