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
public class PermissionsPensionerVM implements Serializable {
    @Ignore
    boolean homeMenuActivated = true;
    @Ignore
    boolean personalInfoMenuActivated = true;
    @Ignore
    boolean payrollsMenuActivated = true;
    @Ignore
    boolean deductionsMenuActivated = true;
    @Ignore
    boolean coeMenuActivated = true;
    @Ignore
    boolean ticketsMenuActivated = true;
    @Ignore
    boolean manageAccountMenuActivated = true;
    @Ignore
    boolean auditTrailMenuActivated = true;
    @Ignore
    boolean canRegisterSelfie = false;
    @Ignore
    boolean canAuthenticateSelfie = true;
    @Ignore
    boolean canUpdateSelfie = false;
}
