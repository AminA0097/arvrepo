package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TBL_TYPE")
@Data
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_FA_NAME", nullable = false)
    private String faName;

    @Column(name = "FLD_EN_NAME")
    private String enName;

    @Column(name = "FLD_SLUG", unique = true, nullable = false)
    private String slug;

    @Column(name = "FLD_DES")
    private String des;
}
