package com.darkhub.smart_tasker.controller;

import com.darkhub.smart_tasker.dto.JSendResponse;
import com.darkhub.smart_tasker.dto.ProjectMemberRequest;
import com.darkhub.smart_tasker.dto.ProjectMemberResponse;
import com.darkhub.smart_tasker.entity.enums.MemberRole;
import com.darkhub.smart_tasker.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService memberService;

    @GetMapping
    public ResponseEntity<JSendResponse<List<ProjectMemberResponse>>> getMembers(
            @PathVariable UUID projectId,
            @RequestParam String requesterEmail
    ) {
        List<ProjectMemberResponse> members = memberService.getMembers(projectId, requesterEmail);
        return ResponseEntity.ok(JSendResponse.success(members));
    }

    @PostMapping
    public ResponseEntity<JSendResponse<ProjectMemberResponse>> addMember(
            @PathVariable UUID projectId,
            @RequestBody ProjectMemberRequest request,
            @RequestParam String requesterEmail
    ) {
        ProjectMemberResponse added = memberService.addMember(projectId, request, requesterEmail);
        return ResponseEntity.ok(JSendResponse.success(added));
    }

    @DeleteMapping
    public ResponseEntity<JSendResponse<Void>> removeMember(
            @PathVariable UUID projectId,
            @RequestParam String memberEmail,
            @RequestParam String requesterEmail
    ) {
        memberService.removeMember(projectId, memberEmail, requesterEmail);
        return ResponseEntity.ok(JSendResponse.success(null));
    }

    @PutMapping("/role")
    public ResponseEntity<JSendResponse<ProjectMemberResponse>> updateRole(
            @PathVariable UUID projectId,
            @RequestParam String memberEmail,
            @RequestParam MemberRole newRole,
            @RequestParam String requesterEmail
    ) {
        ProjectMemberResponse updated = memberService.updateRole(projectId, memberEmail, newRole, requesterEmail);
        return ResponseEntity.ok(JSendResponse.success(updated));
    }
}
