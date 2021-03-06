package org.msh.etbm.services.admin.workspaces.impl;

/**
 * Store information of a user that comes from a json template
 * <p>
 * Created by rmemoria on 3/9/15.
 */
public class UserProfileTemplate {
    private String name;
    private String[] roles;
    private boolean allRoles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isAllRoles() {
        return allRoles;
    }

    public void setAllRoles(boolean allRoles) {
        this.allRoles = allRoles;
    }
}
