package com.systech.mss.seurity;

import com.systech.mss.controller.vm.ErrorVM;
import io.jsonwebtoken.ExpiredJwtException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExpiredJwtExceptionMapper implements ExceptionMapper<ExpiredJwtException> {
  @Override
  public Response toResponse(ExpiredJwtException e) {
    return Response.status(Response.Status.UNAUTHORIZED)
            .entity(ErrorVM.builder().msg("Authorization failed, session expired").success(false).build())
            .build();
  }
}
