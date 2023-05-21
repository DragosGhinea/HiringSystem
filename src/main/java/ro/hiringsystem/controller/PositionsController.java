package ro.hiringsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.model.enums.Position;

@RestController
@RequestMapping("/api/v1/positions")
public class PositionsController {

    @GetMapping
    public ResponseEntity<Position[]> getAllPositions() {
        Position[] positions = Position.values();
        return ResponseEntity.ok(positions);
    }
}