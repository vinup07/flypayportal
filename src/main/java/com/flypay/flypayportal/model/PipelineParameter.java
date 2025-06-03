package com.flypay.flypayportal.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String paramKey;
    private String paramValue;

    @ManyToOne
    @JoinColumn(name = "pipeline_id", nullable = false)
    private Pipeline pipeline;
}
