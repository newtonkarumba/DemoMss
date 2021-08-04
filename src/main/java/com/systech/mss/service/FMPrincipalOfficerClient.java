package com.systech.mss.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.config.APICall;
import com.systech.mss.controller.vm.FMUserVM;
import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.YesNo;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.CumulativeInterestDTO;
import com.systech.mss.service.dto.FmListBooleanDto;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.vm.benefitrequest.BenefitsToFMVM;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class FMPrincipalOfficerClient {
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

    public FmListBooleanDto getPrincipalOfficerDetails(long id) {
        try {
            Response response = target.path(APICall.GET_PRINCIPAL_OFFICER_DETAILS)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }



    public FmListBooleanDto getPrincipalOfficerSchemes(long id) {
        try {
            Response response = target.path(APICall.GET_PRINCIPAL_OFFICER_SCHEMES)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }



    public FmListBooleanDto getPrincipalOfficerEmployers(long id,long schemeId) {
        try {
            Response response = target.path(APICall.GET_PRINCIPAL_OFFICER_EMPLOYERS)
                    .path(String.valueOf(id))
                    .path(String.valueOf(schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }


    public FmListBooleanDto getPrincipalOfficerCostCenters(long id,long schemeId,long sponsorId) {
        try {
            Response response = target.path(APICall.GET_PRINCIPAL_OFFICER_COST_CENTERS)
                    .path(String.valueOf(id))
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }


    public FmListBooleanDto getPaidClaims(long schemeId,long sponsorId,String dateFrom, String dateTo,int start, int size) {
        try {
            Response response = target.path(APICall.GET_PAID_CLAIMS)
                    .path(String.valueOf(dateFrom))
                    .path(String.valueOf(dateTo))
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .queryParam("start",start)
                    .queryParam("limit",size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }




    public FmListBooleanDto getAllClaims(long schemeId,long sponsorId,String dateFrom, String dateTo,int start, int size) {
        try {
            Response response = target.path(APICall.GET_ALL_CLAIMS)
                    .path(String.valueOf(dateFrom))
                    .path(String.valueOf(dateTo))
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .queryParam("start",start)
                    .queryParam("limit",size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }


    public FmListBooleanDto getPaidClaims(long schemeId,long sponsorId,int start, int size) {
        try {
            Response response = target.path(APICall.GET_PAID_CLAIMS)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .queryParam("start",start)
                    .queryParam("limit",size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }




    public FmListBooleanDto getAllClaims(long schemeId,long sponsorId,int start, int size) {
        try {
            Response response = target.path(APICall.GET_ALL_CLAIMS)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .queryParam("start",start)
                    .queryParam("limit",size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }




    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

}
