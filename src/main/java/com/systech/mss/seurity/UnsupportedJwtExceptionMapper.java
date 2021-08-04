package com.systech.mss.seurity;


import com.systech.mss.controller.vm.ErrorVM;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnsupportedJwtExceptionMapper implements ExceptionMapper<UnsupportedJwtException> {
  @Override
  public Response toResponse(UnsupportedJwtException e) {
    return Response.status(Response.Status.UNAUTHORIZED)
            .entity(ErrorVM.builder().msg(e.getLocalizedMessage()).success(false).build())
            .build();
  }
}
