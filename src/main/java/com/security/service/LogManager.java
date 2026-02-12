package com.security.service;

import com.security.model.Incident;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    public List<Incident> analyzeRisks(List<Incident> logs) {
        List<Incident> criticals = new ArrayList<>();
        if (logs == null) return criticals;

        for (Incident i : logs) {
            if (i.getPriority() == 4) { // Prioridad Crítica
                criticals.add(i);
            }
        }
        return criticals;
    }
}