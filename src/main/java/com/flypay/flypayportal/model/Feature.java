package com.flypay.flypayportal.model;

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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID featureId;

    private String featureName;
    @Enumerated(EnumType.STRING)
    private CommonStatus featureStatus;

    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pipeline> pipelines = new HashSet<>();

    // Constructors, Getters, Setters
}
