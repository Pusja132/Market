package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.marketplace.lot.LotTopicDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotTopicService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lot/topic")
@RequiredArgsConstructor
public class LotTopicController {
    private final LotTopicService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<LotTopicDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LotTopicDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LotTopicDto> create(@RequestBody LotTopicDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotTopicDto> update(@PathVariable Long id, @RequestBody LotTopicDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
