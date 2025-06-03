package com.flypay.flypayportal.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.flypay.flypayportal.enumeration.CommonStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pipeline extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pipelineId;

    private String pipelineName;
    private LocalDate testDate;
    @Enumerated(EnumType.STRING)
    private CommonStatus pipelineStatus;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PipelineParameter> parameters = new HashSet<>();

    // Constructors, Getters, Setters
}

