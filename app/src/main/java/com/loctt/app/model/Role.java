/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "Role")
public class Role implements Serializable{
    @Id
    @Column(name = "RoleID")
    private String roleID;
    @Column(name = "Role_Name")
    private String roleName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private Set<Employee> employees;

    public Role(String roleID, String roleName, Set<Employee> employees) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.employees = employees;
    }

    public Role() {
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    /**
     * @return the roleID
     */
    public String getRoleID() {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }
    
}
