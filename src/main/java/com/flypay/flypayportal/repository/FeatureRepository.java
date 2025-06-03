package com.flypay.flypayportal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flypay.flypayportal.enumeration.CommonStatus;
import com.flypay.flypayportal.model.Feature;

public interface FeatureRepository extends JpaRepository<Feature, UUID> {
	
    List<Feature> findByFeatureStatus(CommonStatus status);
}
