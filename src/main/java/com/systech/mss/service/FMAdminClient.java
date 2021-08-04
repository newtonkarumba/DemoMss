package com.systech.mss.service;

import com.systech.mss.config.APICall;
import com.systech.mss.domain.Config;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.FmListDTO;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Optional;
@RequestScoped
public class FMAdminClient {
    private Client client;
    private WebTarget target;
    @Inject
    private ConfigRepository configRepository;
    Config config;
    private MultivaluedMap<String, Object> myHeaders;

    @Inject
    Logger log;

    @PostConstruct
    public void setup() {
        client = ClientBuilder.newClient();
        if (getFMConfig().isPresent()) {
            config = getFMConfig().get();
            myHeaders = new MultivaluedHashMap<>();
            myHeaders.add("username", config.getFmUsername());
            myHeaders.add("password", config.getFmPassword());
            target = client.target(config.getFmBasePath()); //http:168.235.82.130:8084/Xe/api
        }
    }

    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

    public FmListDTO getAdminDetails(long StaffNo) {
        try {
            Response response = target.path(APICall.GET_ADMIN_DETAILS)
                    .path(String.valueOf(StaffNo))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }



    public FmListDTO getUserDetails(long userId) {
        try {
            Response response = target.path(APICall.GET_USER_DETAILS)
                    .path(String.valueOf(userId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }
    public FmListDTO getSessionDetails(long Id) {
        try {
            Response response = target.path(APICall.GET_SESSION_DETAILS)
                    .path(String.valueOf(Id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public Response requestMemberCertificate() {
        return null;
    }


    public Response changePwd() {
        return null;
    }

}
