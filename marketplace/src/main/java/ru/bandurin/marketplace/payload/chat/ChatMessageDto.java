package ru.bandurin.marketplace.payload.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long id;
    private Long senderId;
    private String text;
    private Long chatId;
}
