package com.darkhub.smart_tasker.controller;

import com.darkhub.smart_tasker.dto.JSendResponse;
import com.darkhub.smart_tasker.dto.ProjectRequest;
import com.darkhub.smart_tasker.dto.ProjectResponse;
import com.darkhub.smart_tasker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public JSendResponse<ProjectResponse> createProject(@RequestBody ProjectRequest request,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        ProjectResponse project = projectService.createProject(request, userDetails.getUsername());
        return JSendResponse.success(project);
    }

    @GetMapping
    public JSendResponse<List<ProjectResponse>> listProjects(@AuthenticationPrincipal UserDetails userDetails) {
        List<ProjectResponse> projects = projectService.getProjectsByUser(userDetails.getUsername());
        return JSendResponse.success(projects);
    }
}
