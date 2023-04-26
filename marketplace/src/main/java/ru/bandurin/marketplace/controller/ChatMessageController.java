package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.ValueDto;
import ru.bandurin.marketplace.payload.chat.ChatMessageDto;
import ru.bandurin.marketplace.service.chat.ChatMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService service;

    @GetMapping("/messages/{id}")
    public ResponseEntity<ChatMessageDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<ChatMessageDto>> getByChat(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByChat(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/messages/send")
    public ResponseEntity<Void> send(@PathVariable Long id, @RequestBody ValueDto<String> dto) {
        service.send(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
