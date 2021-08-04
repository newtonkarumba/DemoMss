package com.systech.mss.seurity;

import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.domain.User;
import com.systech.mss.repository.UserRepository;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@JwtTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private TokenProvider jwtUtil;

    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        // Extract the token from the HTTP Authorization header starting at index 7
        String jwtToken = authorizationHeader.substring(7);
        Optional<User> oneByLogin = userRepository.findOneByLogin(jwtUtil.extractUsername(jwtToken));
        if (oneByLogin.isPresent()) {
            jwtUtil.validateToken(jwtToken, oneByLogin.get());
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(
                    ErrorVM.builder().msg("Not Authorized")
                            .success(false).build()
            ).build());
        }

    }
}
