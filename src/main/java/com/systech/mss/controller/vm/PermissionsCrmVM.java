package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsCrmVM implements Serializable {
    @Ignore
    boolean homeMenuActivated = true;
    @Ignore
    boolean sponsorMenuActivated = true;
    @Ignore
    boolean claimsMenuActivated = true;
    @Ignore
    boolean ticketsMenuActivated = true;
    @Ignore
    boolean manageAccountMenuActivated = true;
    @Ignore
    boolean auditTrailMenuActivated = true;
    @Column
    boolean approveClaimAuthorizerActivated = true;
}
