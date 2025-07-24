package com.darkhub.smart_tasker.controller;

import com.darkhub.smart_tasker.dto.JSendResponse;
import com.darkhub.smart_tasker.dto.UserScheduleEntryRequest;
import com.darkhub.smart_tasker.dto.UserScheduleEntryResponse;
import com.darkhub.smart_tasker.service.UserScheduleEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class UserScheduleEntryController {

    private final UserScheduleEntryService scheduleService;

    @PostMapping
    public ResponseEntity<JSendResponse<UserScheduleEntryResponse>> create(
            @RequestBody UserScheduleEntryRequest request
    ) {
        return ResponseEntity.ok(JSendResponse.success(scheduleService.create(request)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<JSendResponse<List<UserScheduleEntryResponse>>> getForUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(JSendResponse.success(scheduleService.getByUser(userId)));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<JSendResponse<List<UserScheduleEntryResponse>>> getForProject(
            @PathVariable UUID projectId
    ) {
        return ResponseEntity.ok(JSendResponse.success(scheduleService.getByProject(projectId)));
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<JSendResponse<Void>> delete(
            @PathVariable UUID entryId
    ) {
        scheduleService.delete(entryId);
        return ResponseEntity.ok(JSendResponse.success(null));
    }
}
