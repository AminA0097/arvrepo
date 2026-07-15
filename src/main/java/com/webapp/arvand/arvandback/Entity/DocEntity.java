package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="TBL_DOC")
public class DocEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @JoinColumn(name = "FLD_DOC_TYPE")
    @ManyToOne
    private CoreThingEntity docType;

    @Column(name = "FLD_DCO_DATA", columnDefinition = "bytea")
    private byte[] docData;

    @Column(name = "FLD_DOC_NAME")
    private String docName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CoreThingEntity getDocType() {
        return docType;
    }

    public void setDocType(CoreThingEntity docType) {
        this.docType = docType;
    }

    public byte[] getDocData() {
        return docData;
    }

    public void setDocData(byte[] docData) {
        this.docData = docData;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
