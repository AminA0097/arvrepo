package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TBL_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_EMAIL")
    private String email;


    @Column(name = "FLD_USER_NAME")
    private String userName;

    @Column(name = "FLD_PASS")
    private String pass;

    @Column(name = "FLD_PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "FLD_IS_ACTIVE")
    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "TBL_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROEL_ID")
    )
    private List<RoleEntity> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
