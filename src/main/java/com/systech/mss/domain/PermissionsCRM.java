package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = PermissionsCRM.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class PermissionsCRM extends BaseModel  implements Serializable {

    @Transient
    public static final String TB_NAME = "crmperms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean homeMenuActivated = true;
    @Column
    boolean sponsorMenuActivated = true;
    @Column
    boolean claimsMenuActivated = true;
    @Column
    boolean ticketsMenuActivated = true;
    @Column
    boolean manageAccountMenuActivated = true;
    @Column
    boolean auditTrailMenuActivated = true;
    @Column
    boolean approveClaimAuthorizerActivated = true;
}
