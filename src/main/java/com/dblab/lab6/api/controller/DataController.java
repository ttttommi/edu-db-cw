package com.dblab.lab6.api.controller;

import com.dblab.lab6.api.dto.DatafileDto;
import com.dblab.lab6.api.dto.RequestDto;
import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.entity.Request;
import com.dblab.lab6.api.service.DataService;
import com.dblab.lab6.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;
    private final RequestService requestService;

    @Autowired
    public DataController(DataService dataService, RequestService requestService) {
        this.dataService = dataService;
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<Datafile>> getAllDatafiles() {
        return ResponseEntity.ok(dataService.findAll());
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<Datafile> getDatafile(@PathVariable Long dataId) {
        return ResponseEntity.ok(dataService.findById(dataId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Datafile> createDatafile(@RequestBody DatafileDto datafileDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataService.create(datafileDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/requests")
    public ResponseEntity<Request> createUploadRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createRequest(requestDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/requests/{dataId}")
    public ResponseEntity<Request> createEditRequest(@PathVariable Long dataId, @RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.editRequest(dataId, requestDto));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.findAll());
    }
}
