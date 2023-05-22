package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.InterviewConferenceRoomDto;
import ro.hiringsystem.service.InterviewConferenceRoomService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview")
@RequiredArgsConstructor
public class InterviewRoomController {
    private final InterviewConferenceRoomService interviewConferenceRoomService;

    @PostMapping("create")
    public ResponseEntity<InterviewConferenceRoomDto> createInterviewRoom(@RequestBody InterviewConferenceRoomDto interviewConferenceRoomDto){
        return ResponseEntity.ok(interviewConferenceRoomService.create(interviewConferenceRoomDto));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<InterviewConferenceRoomDto> getInterviewRoom(@PathVariable("id") UUID id){
        return ResponseEntity.ok(interviewConferenceRoomService.getByIdFullyLoaded(id));
    }

    @PostMapping("edit/{id}")
    public ResponseEntity<InterviewConferenceRoomDto> editInterviewRoom(
            @PathVariable("id") UUID id,
            @RequestBody InterviewConferenceRoomDto interviewConferenceRoomDto
    ){
        interviewConferenceRoomDto.setId(id);
        interviewConferenceRoomService.saveElement(interviewConferenceRoomDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Object> deleteInterviewRoom(@PathVariable("id") UUID id){
        if(interviewConferenceRoomService.deleteById(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
