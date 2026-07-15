package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="TBL_ROLE")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_GROUP")
    private String group;

    @Column(name = "FLD_ROLE_NAME")
    private String roleName;

    @Column(name = "FLD_DELETED")
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
