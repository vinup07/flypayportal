package com.flypay.flypayportal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flypay.flypayportal.model.Feature;
import com.flypay.flypayportal.service.FeatureService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @PostMapping
    @Operation(summary = "Create Feature", description = "Create a new feature")
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        return new ResponseEntity<>(featureService.createFeature(feature), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Feature", description = "Get a feature by ID")
    public ResponseEntity<Feature> getFeature(@PathVariable UUID id) {
        return ResponseEntity.ok(featureService.getFeature(id));
    }
    
    @GetMapping
    @Operation(summary = "Get All Features", description = "Get active features")
    public ResponseEntity<List<Feature>> getAllActiveFeatures() {
        return ResponseEntity.ok(featureService.getActiveFeatures());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Feature", description = "Update an existing feature")
    public ResponseEntity<Feature> updateFeature(@PathVariable UUID id,
                                                 @RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.updateFeature(id, feature));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Feature", description = "Soft delete a feature by ID")
    public ResponseEntity<Void> deleteFeature(@PathVariable UUID id) {
        featureService.softDeleteFeature(id);
        return ResponseEntity.noContent().build();
    }
}
