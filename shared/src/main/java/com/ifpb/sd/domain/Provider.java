package com.ifpb.sd.domain;

import java.io.Serializable;

/**
 *
 * @author rodrigobento
 */
public class Provider implements Serializable {
    
    private String appId;
    private String host;
    private String name;
    private int port;

    public Provider() {
    }

    public Provider(String appId, String host, String name, int port) {
        this.appId = appId;
        this.host = host;
        this.name = name;
        this.port = port;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Provider{" + "appId=" + appId + ", host=" + host + ", name=" + name + ", port=" + port + '}';
    }
    
}
