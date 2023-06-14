package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.service.ManagerUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/manager")
@RequiredArgsConstructor
public class ManagerUsersController {
    private final ManagerUserService managerUserService;

    @GetMapping("profile/{id}")
    public ResponseEntity<ManagerUserDto> getManagerUser(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(managerUserService.getById(id));
    }

    @PostMapping("create")
    public ResponseEntity<ManagerUserDto> createMangerUser(@RequestBody ManagerUserDto managerUserDto){
        return ResponseEntity.ok(managerUserService.create(managerUserDto));
    }
}
