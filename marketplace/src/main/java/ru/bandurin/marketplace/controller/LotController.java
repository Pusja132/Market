package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.marketplace.lot.LotDto;
import ru.bandurin.marketplace.service.marketplace.lot.LotService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lot")
@RequiredArgsConstructor
public class LotController {
    private final LotService service;

    @GetMapping("/{id}")
    public ResponseEntity<LotDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<List<LotDto>> getByTopic(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByLotTopic(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<LotDto>> getByUser(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByUser(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<LotDto> create(@RequestBody LotDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotDto> update(@PathVariable Long id, @RequestBody LotDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
