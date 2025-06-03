package com.flypay.flypayportal.service;

import java.util.List;
import java.util.UUID;

import com.flypay.flypayportal.model.Feature;

public interface FeatureService {

	Feature createFeature(Feature feature);

	Feature updateFeature(UUID id, Feature updatedFeature);

	void softDeleteFeature(UUID id);

	Feature getFeature(UUID id);

	List<Feature> getAllFeatures();

	List<Feature> getActiveFeatures();

}
