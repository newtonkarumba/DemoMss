package com.systech.mss.seurity;


import com.systech.mss.controller.vm.ErrorVM;
import io.jsonwebtoken.MalformedJwtException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MalformedJwtExceptionMapper implements ExceptionMapper<MalformedJwtException> {
  @Override
  public Response toResponse(MalformedJwtException e) {
    return Response.status(Response.Status.UNAUTHORIZED)
            .entity(ErrorVM.builder().msg(e.getLocalizedMessage()).success(false).build())
            .build();
  }
}
