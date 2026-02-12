package com.security.model;

import java.util.Date;

public class NetworkIncident extends Incident {
    private String sourceIp;
    private String protocol;

    public NetworkIncident() { super(); }

    public NetworkIncident(String sourceIp, String protocol, int id, String description, int priority, String sourceOrigin, Date createdAt) {
        super(id, description, priority, sourceOrigin, createdAt);
        this.sourceIp = sourceIp;
        this.protocol = protocol;
    }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }
}