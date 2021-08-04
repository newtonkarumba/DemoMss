package com.systech.mss.seurity;

import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Utility class for Security.
 */
@RequestScoped
@Path("/api")
public class SecurityHelper {

    @Context
    private SecurityContext securityContext;

    @Path(value = "/current-user")
    @GET
    public String getCurrentUserLogin() {
        if (securityContext == null || securityContext.getCallerPrincipal() == null) {
            return null;
        }
        return securityContext.getCallerPrincipal().getName();
    }
}
