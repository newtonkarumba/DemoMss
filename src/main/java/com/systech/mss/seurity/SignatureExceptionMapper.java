package com.systech.mss.seurity;

import com.systech.mss.controller.vm.ErrorVM;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class SignatureExceptionMapper implements ExceptionMapper<SignatureException> {
  @Override
  public Response toResponse(SignatureException e) {
    return Response.status(Response.Status.UNAUTHORIZED)
            .entity(ErrorVM.builder().msg(e.getLocalizedMessage()).success(false).build())
            .build();
  }
}
