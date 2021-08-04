package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = PermissionsSponsor.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class PermissionsSponsor extends BaseModel  implements Serializable {

    @Transient
    public static final String TB_NAME = "sponsorperms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean homeMenuActivated = true;
    @Column
    boolean personalInfoMenuActivated = true;
    @Column
    boolean membersMenuActivated = true;
    @Column
    boolean claimsMenuActivated = true;
    @Column
    boolean stagedContributionsMenuActivated = true;
    @Column
    boolean billsMenuActivated = true;
    @Column
    boolean receiptsMenuActivated = true;
    @Column
    boolean dmsMenuActivated = true;
    @Column
    boolean ticketsMenuActivated = true;
    @Column
    boolean usersAccountMenuActivated = true;
    @Column
    boolean manageAccountMenuActivated = true;
    @Column
    boolean auditTrailMenuActivated = true;
    @Column
    boolean allowBookBill = true;
    @Column
    boolean allowStageContributions = true;
    @Column
    boolean allowAddSingleUser = true;
    @Column
    boolean allowAddBatchUser = true;
    @Column
    boolean allowApproveDocuments = true;

    @Column
    boolean tpfaMenuActivated = true;

    @Column
    boolean sponsorConfigMenuActivated = true;

    @Column
    boolean benefitsMenuActivated = true;

}
