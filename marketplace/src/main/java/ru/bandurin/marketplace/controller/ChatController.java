package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.ListDto;
import ru.bandurin.marketplace.payload.chat.ChatDto;
import ru.bandurin.marketplace.service.chat.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/self")
    public ResponseEntity<List<ChatDto>> getSelf() {
        return new ResponseEntity<>(service.getSelfDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChatDto> create(@RequestBody ChatDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> connectToChat(@PathVariable Long id) {
        service.connectToChat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody ListDto<Long> ids) {
        service.delete(ids.getList());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
