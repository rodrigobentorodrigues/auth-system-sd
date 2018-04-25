package com.ifpb.sd.domain;

import java.io.Serializable;

/**
 *
 * @author rodrigobento
 */
public class User implements Serializable {
    
    private int id;
    private String appId;
    private String email;
    private String senha;

    public User() {
    }

    public User(String appId, String email, String senha) {
        this.appId = appId;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", appId=" + appId + ", email=" + email + ", senha=" + senha + '}';
    }
   
}
