package com.flypay.flypayportal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flypay.flypayportal.enumeration.CommonStatus;
import com.flypay.flypayportal.model.Feature;
import com.flypay.flypayportal.model.Pipeline;
import com.flypay.flypayportal.model.PipelineParameter;
import com.flypay.flypayportal.repository.FeatureRepository;
import com.flypay.flypayportal.repository.PipelineRepository;
import com.flypay.flypayportal.service.PipelineService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private FeatureRepository featureRepository;
    
    private final ObjectMapper objectMapper;
    
    private String azureOrg="colesgroup";
    private String azureProject="Fintechpaymentservices";
    
    public PipelineServiceImpl(ObjectMapper objectMapper) {
    	 this.objectMapper = new ObjectMapper();
         this.objectMapper.registerModule(new JavaTimeModule());
	}

    @Override
    public Pipeline createPipeline(UUID featureId, Pipeline pipeline) {
        try {
            Feature feature = featureRepository.findById(featureId)
                    .orElseThrow(() -> new RuntimeException("Feature not found with ID: " + featureId));

            pipeline.setFeature(feature);
            pipeline.setPipelineStatus(CommonStatus.ACTIVE);
            pipeline.setCreatedAt(LocalDateTime.now());
            pipeline.setUpdatedAt(LocalDateTime.now());

            for (PipelineParameter param : pipeline.getParameters()) {
                param.setPipeline(pipeline);
            }
            Pipeline pipelineResponse = pipelineRepository.save(pipeline);
            JsonNode joltJson = transformToAzurePipelineRequest(pipelineResponse);
            triggerPipeline(pipelineResponse.getPipelineName(), joltJson);
            return pipelineResponse;
        } catch (Exception e) {
            log.error("Error occurred while creating pipeline for featureId {}: {}", featureId, e.getMessage(), e);
            throw new RuntimeException("Failed to create pipeline: " + e.getMessage(), e);
        }
    }
    
    public ResponseEntity<String> triggerPipeline(@RequestParam String pipelineId, @RequestBody JsonNode parameters) {
        String azurePipelineUrl = "https://dev.azure.com/"+ azureOrg +"/"+azureProject+"/_apis/pipelines/" + pipelineId + "/runs?api-version=6.0-preview.1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic OjJIUWFhMFE2bUVIcVNZQUwwdUZXWFJkdUFqdGZ5b0dqSkFScHBVN1dsMFU1TTJTZXJhV1VKUVFKOTlCREFDQUFBQUFKUWJrMEFBQVNBWkRPd1paRg==");
    
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(azurePipelineUrl,requestEntity, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Pipeline triggered successfully!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to trigger pipeline.");
        }
    }
        
    
    private JsonNode transformToAzurePipelineRequest(Pipeline pipeline) {
    	try {
            ObjectNode root = objectMapper.createObjectNode();

            // Create resources.repositories.self.refName
            ObjectNode selfNode = objectMapper.createObjectNode();
            selfNode.put("refName", pipeline.getBranch());

            ObjectNode repositoriesNode = objectMapper.createObjectNode();
            repositoriesNode.set("self", selfNode);

            ObjectNode resourcesNode = objectMapper.createObjectNode();
            resourcesNode.set("repositories", repositoriesNode);

            root.set("resources", resourcesNode);

            // Add parameters
            ObjectNode templateParameters = objectMapper.createObjectNode();
            pipeline.getParameters().forEach(param ->
                templateParameters.put(param.getParamKey(), param.getParamValue())
            );
            root.set("templateParameters", templateParameters);

            return root;

        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.createObjectNode().put("error", "Failed to transform pipeline: " + e.getMessage());
        }
	}

	@Override
    public Pipeline updatePipeline(UUID id, Pipeline updated) {
        try {
            Pipeline existing = pipelineRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pipeline not found with ID: " + id));

            existing.setPipelineName(updated.getPipelineName());
            existing.setTestDate(updated.getTestDate());
            existing.setPipelineStatus(updated.getPipelineStatus());
            existing.setUpdatedAt(LocalDateTime.now());

            return pipelineRepository.save(existing);
        } catch (Exception e) {
            log.error("Error occurred while updating pipeline with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update pipeline: " + e.getMessage(), e);
        }
    }

    @Override
    public void softDeletePipeline(UUID id) {
        try {
            Pipeline pipeline = pipelineRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pipeline not found with ID: " + id));
            pipeline.setPipelineStatus(CommonStatus.INACTIVE);
            pipeline.setUpdatedAt(LocalDateTime.now());
            pipelineRepository.save(pipeline);
        } catch (Exception e) {
            log.error("Error occurred while soft-deleting pipeline with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to soft delete pipeline: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pipeline> getAllPipelines() {
        try {
            return pipelineRepository.findAll();
        } catch (Exception e) {
            log.error("Error occurred while fetching all pipelines: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all pipelines: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pipeline> getActivePipelines() {
        try {
            return pipelineRepository.findByPipelineStatus(CommonStatus.ACTIVE);
        } catch (Exception e) {
            log.error("Error occurred while fetching active pipelines: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch active pipelines: " + e.getMessage(), e);
        }
    }

    @Override
    public Pipeline getPipeline(UUID id) {
        try {
            return pipelineRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pipeline not found with ID: " + id));
        } catch (Exception e) {
            log.error("Error occurred while fetching pipeline with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch pipeline: " + e.getMessage(), e);
        }
    }
}
