package com.systech.mss.controller.vm;


import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsCREVM {
    @Ignore
    boolean homeMenuActivated = true;
    @Ignore
    boolean schemesMenuActivated = true;
    @Ignore
    boolean allowInitiateClaims = true;
    @Ignore
    boolean allowUploadDocs = true;
    @Ignore
    boolean ticketsMenuActivated = true;
    @Ignore
    boolean manageAccountMenuActivated = true;
    @Ignore
    boolean auditTrailMenuActivated = true;
}
