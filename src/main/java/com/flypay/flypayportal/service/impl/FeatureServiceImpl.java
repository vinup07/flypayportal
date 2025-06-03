package com.flypay.flypayportal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flypay.flypayportal.enumeration.CommonStatus;
import com.flypay.flypayportal.model.Feature;
import com.flypay.flypayportal.repository.FeatureRepository;
import com.flypay.flypayportal.service.FeatureService;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository repository;

    @Override
    public Feature createFeature(Feature feature) {
    	feature.setFeatureStatus(CommonStatus.ACTIVE);
        feature.setCreatedAt(LocalDateTime.now());
        feature.setUpdatedAt(LocalDateTime.now());
        return repository.save(feature);
    }

    @Override
    public Feature updateFeature(UUID id, Feature updatedFeature) {
        Feature existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
        existing.setFeatureName(updatedFeature.getFeatureName());
        existing.setFeatureStatus(updatedFeature.getFeatureStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }
    
    @Override
    public void softDeleteFeature(UUID id) {
        Feature feature = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
        feature.setFeatureStatus(CommonStatus.INACTIVE);
        feature.setUpdatedAt(LocalDateTime.now());
        repository.save(feature);
    }

    @Override
    public Feature getFeature(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
    }

    @Override
    public List<Feature> getAllFeatures() {
        return repository.findAll();
    }

    @Override
    public List<Feature> getActiveFeatures() {
        return repository.findByFeatureStatus(CommonStatus.ACTIVE);
    }
}
