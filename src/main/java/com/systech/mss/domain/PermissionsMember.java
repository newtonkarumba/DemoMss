package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = PermissionsMember.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class PermissionsMember extends BaseModel implements Serializable {

    @Transient
    public static final String TB_NAME = "memberperms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    boolean homeMenuActivated = true;
    @Column
    boolean personalInfoMenuActivated = true;
    @Column
    boolean contributionsMenuActivated = true;
    @Column
    boolean balancesMenuActivated = true;
    @Column
    boolean claimsMenuActivated = true;
    @Column
    boolean projectionsMenuActivated = true;
    @Column
    boolean documentsMenuActivated = true;
    @Column
    boolean ticketsMenuActivated = true;
    @Column
    boolean manageAccountMenuActivated = true;
    @Column
    boolean auditTrailMenuActivated = true;
    @Column
    boolean bankMenuActivated = true;
    @Column
    boolean allowMakeContributions = true;
    @Column
    boolean allowInitiateClaim = true;
    @Column
    boolean allowStkPush = true;
    @Column
    boolean allowRequestStatement = true;
    @Column
    boolean allowBenefitCalculator = true;
    @Column
    boolean allowWhatIfAnalysis = true;
    @Column
    boolean benefitsFmActivated = true;
}
