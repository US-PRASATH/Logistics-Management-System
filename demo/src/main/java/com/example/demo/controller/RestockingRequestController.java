package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RestockingRequest;
import com.example.demo.service.RestockingRequestService;

@RestController
@CrossOrigin
@RequestMapping("/api/restocking-requests")
public class RestockingRequestController {

    @Autowired
    private RestockingRequestService restockingRequestService;

    @GetMapping
    public ResponseEntity<List<RestockingRequest>> getAllRestockingRequests() {
        return ResponseEntity.ok(restockingRequestService.getAllRestockingRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestockingRequest> getRestockingRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(restockingRequestService.getRestockingRequestById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createRestockingRequest(@RequestBody RestockingRequest restockingRequest) {
        restockingRequestService.createRestockingRequest(restockingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestockingRequest(@PathVariable Long id, @RequestBody RestockingRequest restockingRequest) {
        restockingRequestService.updateRestockingRequest(id, restockingRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateRestockingRequestStatus(@PathVariable Long id, @RequestBody RestockingRequest.Status restockingRequestStatus) {
        restockingRequestService.updateRestockingRequestStatus(id, restockingRequestStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestockingRequest(@PathVariable Long id) {
        restockingRequestService.deleteRestockingRequest(id);
        return ResponseEntity.noContent().build();
    }
}