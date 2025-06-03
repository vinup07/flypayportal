package com.flypay.flypayportal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flypay.flypayportal.enumeration.CommonStatus;
import com.flypay.flypayportal.model.Feature;
import com.flypay.flypayportal.model.Pipeline;
import com.flypay.flypayportal.model.PipelineParameter;
import com.flypay.flypayportal.repository.FeatureRepository;
import com.flypay.flypayportal.repository.PipelineRepository;
import com.flypay.flypayportal.service.PipelineService;

@Service
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public Pipeline createPipeline(UUID featureId, Pipeline pipeline) {
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new RuntimeException("Feature not found"));

        pipeline.setFeature(feature);
        pipeline.setPipelineStatus(CommonStatus.ACTIVE);
        pipeline.setCreatedAt(LocalDateTime.now());
        pipeline.setUpdatedAt(LocalDateTime.now());

        for (PipelineParameter param : pipeline.getParameters()) {
            param.setPipeline(pipeline); 
        }

        return pipelineRepository.save(pipeline);
    }

    @Override
    public Pipeline updatePipeline(UUID id, Pipeline updated) {
        Pipeline existing = pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));

        existing.setPipelineName(updated.getPipelineName());
        existing.setTestDate(updated.getTestDate());
        existing.setPipelineStatus(updated.getPipelineStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        return pipelineRepository.save(existing);
    }

    @Override
    public void softDeletePipeline(UUID id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));
        pipeline.setPipelineStatus(CommonStatus.INACTIVE);
        pipeline.setUpdatedAt(LocalDateTime.now());
        pipelineRepository.save(pipeline);
    }

    @Override
    public List<Pipeline> getAllPipelines() {
        return pipelineRepository.findAll();
    }

    @Override
    public List<Pipeline> getActivePipelines() {
        return pipelineRepository.findByPipelineStatus(CommonStatus.ACTIVE);
    }

    @Override
    public Pipeline getPipeline(UUID id) {
        return pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));
    }
}

