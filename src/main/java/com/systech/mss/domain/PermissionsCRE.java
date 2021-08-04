package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = PermissionsCRE.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class PermissionsCRE extends BaseModel  implements Serializable {

    @Transient
    public static final String TB_NAME = "creperms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean homeMenuActivated = true;
    @Column
    boolean schemesMenuActivated = true;
    @Column
    boolean allowInitiateClaims = true;
    @Column
    boolean allowUploadDocs = true;
    @Column
    boolean ticketsMenuActivated = true;
    @Column
    boolean manageAccountMenuActivated = true;
    @Column
    boolean auditTrailMenuActivated = true;
}
