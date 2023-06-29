package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.cv.CVDto;
import ro.hiringsystem.service.CandidateUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/candidate")
@RequiredArgsConstructor
public class CandidateUsersController {
    private final CandidateUserService candidateUserService;

    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteCandidateUser(@PathVariable("id") UUID id) {
        candidateUserService.removeElementById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/all/paginated")
    public ResponseEntity<List<CandidateUserDto>> getAllCandidateUsersPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        if(page <= 0)
            page = 1;
        return ResponseEntity.ok(candidateUserService.getAll(page-1, size));
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<CandidateUserDto> getCandidateUser(@PathVariable("id") String id, Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(id.equals("me"))
            return ResponseEntity.ok((CandidateUserDto) authentication.getPrincipal());
        else
            return ResponseEntity.ok(candidateUserService.getById(UUID.fromString(id)));
    }

    @GetMapping(value="get/cv/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CVDto> getCandidateUserCV(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(candidateUserService.getUserCV(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CandidateUserDto> register(@RequestBody CandidateUserDto candidateUserDto){
        return ResponseEntity.ok(candidateUserService.create(candidateUserDto));
    }

    @PostMapping("edit/{id}")
    public ResponseEntity<CandidateUserDto> editCandidateUser(
            @PathVariable("id") UUID id,
            @RequestBody CandidateUserDto candidateUserDto
    ){
        candidateUserDto.setId(id);
        candidateUserService.saveElement(candidateUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("edit/cv/{id}")
    public ResponseEntity<CandidateUserDto> editCandidateUserCv(
            @PathVariable("id") UUID id,
            @RequestBody CVDto cvDto
    ){
        cvDto.setId(id);
        candidateUserService.updateCv(cvDto);
        return ResponseEntity.ok().build();
    }
}
