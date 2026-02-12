package com.security.model;

import java.io.Serializable;
import java.util.Date;

public class Incident implements Serializable {
    private int id;
    private String description;
    private int priority;
    private String sourceOrigin;
    private String status;
    private Date createdAt;

    public Incident() { 
        this.status = "PENDIENTE"; 
        this.createdAt = new Date();
    }

    public Incident(int id, String description, int priority, String sourceOrigin, Date createdAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.sourceOrigin = sourceOrigin;
        this.createdAt = createdAt;
        this.status = "PENDIENTE";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public String getSourceOrigin() { return sourceOrigin; }
    public void setSourceOrigin(String sourceOrigin) { this.sourceOrigin = sourceOrigin; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getPriorityText() {
        switch (this.priority) {
            case 1: return "Baja";
            case 2: return "Media";
            case 3: return "Alta";
            case 4: return "Crítica";
            default: return "Desconocida";
        }
    }
}