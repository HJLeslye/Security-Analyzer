package com.security.service;

import com.security.model.Incident;
import java.util.List;
import java.util.stream.Collectors;

public class IncidentAnalyzer {
    
    // Método que analiza una lista de incidentes buscando riesgos Críticos (Prioridad 4)
    public List<Incident> detectHighRisks(List<Incident> logs) {
        return logs.stream()
                .filter(incident -> incident.getPriority() >= 4)
                .collect(Collectors.toList());
    }
}