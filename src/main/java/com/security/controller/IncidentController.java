package com.security.controller;

import com.security.dao.IncidentDAO;
import com.security.model.Incident;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@Named(value = "incidentController")
@SessionScoped
public class IncidentController implements Serializable {
    private List<Incident> allIncidents;
    private List<Incident> criticalIncidents;
    private PieChartModel pieStatus;    
    private PieChartModel piePriority;  
    private IncidentDAO dao = new IncidentDAO();
    
    // Objeto inicializado para evitar PropertyNotFoundException
    private Incident newIncident = new Incident(); 
    
    private double securityLevel;
    private int criticalCount;

    @PostConstruct
    public void init() { refresh(); }

    public void refresh() {
        this.allIncidents = dao.findAll();
        
        // FILTRO: Solo incidentes críticos (4) con estado PENDIENTE
        this.criticalIncidents = allIncidents.stream()
                .filter(i -> i.getPriority() == 4 && "PENDIENTE".equals(i.getStatus()))
                .collect(Collectors.toList());
        this.criticalCount = criticalIncidents.size();
        
        long total = allIncidents.size();
        long corregidos = allIncidents.stream().filter(i -> "CORREGIDO".equals(i.getStatus())).count();
        
        // Redondeo matemático a dos decimales
        double rawLevel = total > 0 ? (corregidos * 100.0 / total) : 0.0;
        this.securityLevel = Math.round(rawLevel * 100.0) / 100.0;
        
        createModels(corregidos, total);
    }

    private void createModels(long corregidos, long total) {
        pieStatus = new PieChartModel();
        ChartData d1 = new ChartData();
        PieChartDataSet ds1 = new PieChartDataSet();
        List<Number> v1 = new ArrayList<>();
        v1.add(corregidos > 0 ? corregidos : 0.0001);
        v1.add(total - corregidos);
        ds1.setData(v1);
        List<String> c1 = new ArrayList<>();
        // Lógica de colores dinámica según seguridad
        if (securityLevel >= 80) c1.add("#28a745");
        else if (securityLevel >= 33.33) c1.add("#ff8c00");
        else c1.add("#cc0000");
        c1.add("#e9ecef");
        ds1.setBackgroundColor(c1);
        d1.addChartDataSet(ds1);
        pieStatus.setData(d1);

        piePriority = new PieChartModel();
        ChartData d2 = new ChartData();
        PieChartDataSet ds2 = new PieChartDataSet();
        long b = allIncidents.stream().filter(i -> i.getPriority() == 1).count();
        long m = allIncidents.stream().filter(i -> i.getPriority() == 2).count();
        long a = allIncidents.stream().filter(i -> i.getPriority() == 3).count();
        long cr = allIncidents.stream().filter(i -> i.getPriority() == 4).count();
        List<Number> v2 = new ArrayList<>();
        v2.add(b); v2.add(m); v2.add(a); v2.add(cr);
        ds2.setData(v2);
        List<String> c2 = new ArrayList<>();
        c2.add("#28a745"); c2.add("#007bff"); c2.add("#ffcc00"); c2.add("#cc0000");
        ds2.setBackgroundColor(c2);
        d2.addChartDataSet(ds2);
        piePriority.setData(d2);
    }

    // Getters y Setters obligatorios para JSF
    public Incident getNewIncident() { return newIncident; }
    public void setNewIncident(Incident newIncident) { this.newIncident = newIncident; }
    public List<Incident> getAllIncidents() { return allIncidents; }
    public List<Incident> getCriticalIncidents() { return criticalIncidents; }
    public PieChartModel getPieStatus() { return pieStatus; }
    public PieChartModel getPiePriority() { return piePriority; }
    public double getSecurityLevel() { return securityLevel; }
    public int getCriticalCount() { return criticalCount; }

    public void save() {
        dao.insert(newIncident);
        newIncident = new Incident(); 
        refresh();
    }

    public void markAsResolved(int id) {
        dao.updateStatus(id, "CORREGIDO");
        refresh();
    }

    public void delete(int id) {
        dao.delete(id);
        refresh();
    }
}