package com.flypay.flypayportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/v1/execution")
public class PipelineExecutionController {
    private String azureOrg="colesgroup";
    private String azureProject="Fintechpaymentservices";
    

    @PostMapping("/trigger")
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

    @GetMapping("/status")
    public ResponseEntity<String> getPipelineStatus(@RequestParam String runId) {
        String azurePipelineStatusUrl = "https://dev.azure.com/your-org/your-project/_apis/runs/" + runId + "?api-version=6.0-preview.1";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(azurePipelineStatusUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch pipeline status.");
        }
    }

    @GetMapping("/artifacts")
    public ResponseEntity<String> downloadArtifacts(@RequestParam String runId) {
        String artifactsUrl = "https://dev.azure.com/your-org/your-project/_apis/runs/" + runId + "/artifacts?api-version=6.0-preview.1";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(artifactsUrl, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                String tempDir = System.getProperty("java.io.tmpdir");
                String artifactsPath = tempDir + "/artifacts.zip";
                Files.write(Paths.get(artifactsPath), response.getBody());

                File artifactsFile = new File(artifactsPath);
                if (artifactsFile.isDirectory()) {
                    String zipFilePath = tempDir + "/artifacts-compressed.zip";
                    try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
                        for (File file : artifactsFile.listFiles()) {
                            zos.putNextEntry(new ZipEntry(file.getName()));
                            Files.copy(file.toPath(), zos);
                            zos.closeEntry();
                        }
                    }
                    return ResponseEntity.ok("Artifacts downloaded and zipped successfully: " + zipFilePath);
                } else {
                    return ResponseEntity.ok("Artifacts downloaded successfully: " + artifactsPath);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process artifacts.");
            }
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to download artifacts.");
        }
    }
}