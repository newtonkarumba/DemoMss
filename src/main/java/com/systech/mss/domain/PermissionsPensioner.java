package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = PermissionsPensioner.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class PermissionsPensioner extends BaseModel  implements Serializable {

    @Transient
    public static final String TB_NAME = "pensionerperms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean homeMenuActivated = true;
    @Column
    boolean personalInfoMenuActivated = true;
    @Column
    boolean payrollsMenuActivated = true;
    @Column
    boolean deductionsMenuActivated = true;
    @Column
    boolean coeMenuActivated = true;
    @Column
    boolean ticketsMenuActivated = true;
    @Column
    boolean manageAccountMenuActivated = true;
    @Column
    boolean auditTrailMenuActivated = true;
    @Column
    boolean canRegisterSelfie = false;
    @Column
    boolean canAuthenticateSelfie = true;
    @Column
    boolean canUpdateSelfie = false;
}
