package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.entity.UserScheduleEntry;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;
import com.darkhub.smart_tasker.exception.ExceptionFactory;
import com.darkhub.smart_tasker.repository.ProjectRepository;
import com.darkhub.smart_tasker.repository.UserRepository;
import com.darkhub.smart_tasker.repository.UserScheduleEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.darkhub.smart_tasker.dto.UserScheduleEntryRequest;
import com.darkhub.smart_tasker.dto.UserScheduleEntryResponse;

import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserScheduleEntryService {

    private final UserScheduleEntryRepository entryRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public UserScheduleEntry createEntry(UUID userId, UUID projectId, UserScheduleEntry entry) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> ExceptionFactory.projectNotFound(projectId));
        if (project.getStatus() != ProjectStatus.ACTIVE) {
            throw ExceptionFactory.conflict("Cannot add schedule entries to an inactive project");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail("User ID: " + userId));

        validateEntry(entry);

        entry.setUser(user);
        entry.setProject(project);
        return entryRepository.save(entry);
    }

    public UserScheduleEntryResponse create(UserScheduleEntryRequest request) {
        UserScheduleEntry entry = UserScheduleEntry.builder()
                .name(request.getName())
                .date(request.getDate())
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .color(request.getColor())
                .isMandatory(request.getIsMandatory())
                .notes(request.getNotes())
                .reminderMinutesBefore(request.getReminderMinutesBefore())
                .build();

        UserScheduleEntry saved = createEntry(request.getUserId(), request.getProjectId(), entry);
        return toResponse(saved);
    }

    public UserScheduleEntry getById(UUID id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> ExceptionFactory.notFound("Schedule entry not found with ID: " + id));
    }

    public List<UserScheduleEntry> getAllByUser(UUID userId) {
        return entryRepository.findByUserId(userId);
    }

    public List<UserScheduleEntry> getAllByProject(UUID projectId) {
        return entryRepository.findByProjectId(projectId);
    }

    public UserScheduleEntry updateEntry(UUID id, UserScheduleEntry updated) {
        UserScheduleEntry existing = getById(id);

        validateEntry(updated);

        existing.setName(updated.getName());
        existing.setDate(updated.getDate());
        existing.setDayOfWeek(updated.getDayOfWeek());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        existing.setLocation(updated.getLocation());
        existing.setColor(updated.getColor());
        existing.setIsMandatory(updated.getIsMandatory());
        existing.setNotes(updated.getNotes());
        existing.setReminderMinutesBefore(updated.getReminderMinutesBefore());

        return entryRepository.save(existing);
    }

    public void deleteEntry(UUID id) {
        UserScheduleEntry entry = getById(id);
        entryRepository.delete(entry);
    }

    private void validateEntry(UserScheduleEntry entry) {
        if (entry.getDate() == null && entry.getDayOfWeek() == null) {
            throw ExceptionFactory.invalidField("Either 'date' or 'dayOfWeek' must be provided.");
        }
        if (entry.getStartTime() != null && entry.getEndTime() != null &&
                entry.getStartTime().isAfter(entry.getEndTime())) {
            throw ExceptionFactory.invalidField("Start time must be before end time.");
        }
    }

    public List<UserScheduleEntryResponse> getByUser(UUID userId) {
        return getAllByUser(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<UserScheduleEntryResponse> getByProject(UUID projectId) {
        return getAllByProject(projectId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        deleteEntry(id);
    }

    // Mapear entidad a DTO
    private UserScheduleEntryResponse toResponse(UserScheduleEntry entry) {
        return UserScheduleEntryResponse.builder()
                .id(entry.getId())
                .userId(entry.getUser().getId())
                .projectId(entry.getProject().getId())
                .name(entry.getName())
                .date(entry.getDate())
                .dayOfWeek(entry.getDayOfWeek())
                .startTime(entry.getStartTime())
                .endTime(entry.getEndTime())
                .location(entry.getLocation())
                .color(entry.getColor())
                .isMandatory(entry.getIsMandatory())
                .notes(entry.getNotes())
                .reminderMinutesBefore(entry.getReminderMinutesBefore())
                .build();
    }
}
