package org.rd.tmpl.snv.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;

    private List<ERole> roles;

	public User() {
        super();
        this.username = null;
        this.password = null;
        this.roles    = new ArrayList<>();
    }

    public User(String username, String password, List<ERole> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles    = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ERole> getRoles() {
        return roles;
    }

    public void setRoles(List<ERole> roles) {
        this.roles = roles;
    }
}