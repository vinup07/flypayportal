package com.flypay.flypayportal.service;

import java.util.List;
import java.util.UUID;

import com.flypay.flypayportal.model.Pipeline;

public interface PipelineService {

	Pipeline createPipeline(UUID featureId, Pipeline pipeline);

	Pipeline getPipeline(UUID id);

	Pipeline updatePipeline(UUID id, Pipeline updated);

	void softDeletePipeline(UUID id);

	List<Pipeline> getAllPipelines();

	List<Pipeline> getActivePipelines();

}
