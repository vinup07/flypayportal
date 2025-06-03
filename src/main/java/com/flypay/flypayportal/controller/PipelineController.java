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

import com.flypay.flypayportal.model.Pipeline;
import com.flypay.flypayportal.service.PipelineService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/pipelines")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @PostMapping("/feature/{featureId}")
    @Operation(summary = "Create Pipeline", description = "Create a new pipeline for a feature")
    public ResponseEntity<Pipeline> createPipeline(@PathVariable UUID featureId,
                                                   @RequestBody Pipeline pipeline) {
        return new ResponseEntity<>(pipelineService.createPipeline(featureId, pipeline), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Pipeline", description = "Get a pipeline by ID")
    public ResponseEntity<Pipeline> getPipeline(@PathVariable UUID id) {
        return ResponseEntity.ok(pipelineService.getPipeline(id));
    }

    @GetMapping
    @Operation(summary = "Get All Pipelines", description = "Get all pipelines")
    public ResponseEntity<List<Pipeline>> getAllPipelines() {
        return ResponseEntity.ok(pipelineService.getAllPipelines());
    }

    @GetMapping("/active")
    @Operation(summary = "Get Active Pipelines", description = "Get all active pipelines")
    public ResponseEntity<List<Pipeline>> getActivePipelines() {
        return ResponseEntity.ok(pipelineService.getActivePipelines());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Pipeline", description = "Update an existing pipeline")
    public ResponseEntity<Pipeline> updatePipeline(@PathVariable UUID id,
                                                   @RequestBody Pipeline pipeline) {
        return ResponseEntity.ok(pipelineService.updatePipeline(id, pipeline));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Pipeline", description = "Soft delete a pipeline by ID")
    public ResponseEntity<Void> deletePipeline(@PathVariable UUID id) {
        pipelineService.softDeletePipeline(id);
        return ResponseEntity.noContent().build();
    }
}
