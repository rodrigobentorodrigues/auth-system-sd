package com.ifpb.sd.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author rodrigobento
 */
public class Token implements Serializable {
    
    private String value;
    private boolean valid;
    private LocalDateTime dateHour;
    private String provider;
    private int user;

    public Token() {
    }

    public Token(String value, boolean valid, LocalDateTime dateHour, 
            String provider, int user) {
        this.value = value;
        this.valid = valid;
        this.dateHour = dateHour;
        this.provider = provider;
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean isValid) {
        this.valid = isValid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    @Override
    public String toString() {
        return "Token{" + "value=" + value + ", valid=" + valid + ", dateHour=" + dateHour + ", provider=" + provider + ", user=" + user + '}';
    }
    
}
