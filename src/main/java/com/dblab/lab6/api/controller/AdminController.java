package com.dblab.lab6.api.controller;

import com.dblab.lab6.api.dto.PermissionDto;
import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.entity.User;
import com.dblab.lab6.api.service.AuthService;
import com.dblab.lab6.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final RequestService requestService;

    @Autowired
    public AdminController(AuthService authService, RequestService requestService) {
        this.authService = authService;
        this.requestService = requestService;
    }

    @PostMapping("/requests/{requestId}")
    public ResponseEntity<Datafile> approveRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.approveRequest(requestId));
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<Void> denyRequest(@PathVariable Long requestId) {
        requestService.denyRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/permissions")
    public ResponseEntity<User> addPermissions(@PathVariable String username,
                                              @RequestBody List<PermissionDto> permissions) {
        return ResponseEntity.ok(authService.addPermissions(authService.findByUsername(username), permissions));
    }

    @DeleteMapping("/{username}/permissions")
    public ResponseEntity<User> deletePermissions(@PathVariable String username,
                                              @RequestBody List<PermissionDto> permissions) {
        return ResponseEntity.ok(authService.deletePermissions(authService.findByUsername(username), permissions));
    }
}
