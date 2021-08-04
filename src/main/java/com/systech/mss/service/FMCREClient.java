package com.systech.mss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.config.APICall;
import com.systech.mss.controller.vm.SchemeVM;
import com.systech.mss.domain.Config;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MiniSponsorDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class FMCREClient {
    private Client client;
    private WebTarget target;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private UserService userService;

    @Inject
    private Logger log;

    Config config;
    private MultivaluedMap<String, Object> myHeaders;

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

    public List<SchemeVM> getAllScheme() {
        Response response = target.path(APICall.GET_ALL_SCHEME)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            String s = response.readEntity(String.class);
//            log.error(s);
            JSONObject jsonObject = new JSONObject(s); //response.readEntity(FmListDTO.class);
            List<SchemeVM> list = new ArrayList<>();
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        list.add(
                                new ObjectMapper().readValue(
                                        jsonArray.getJSONObject(i).toString(),
                                        SchemeVM.class
                                )
                        );
                    } catch (IOException ignored) {
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmListDTO getSchemeByName(String schemeName) {
        Response response = target.path(APICall.GET_SCHEME_BY_NAME)
                .path(schemeName)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getSchemeSponsors(long schemeId) {
        Response response = target.path(APICall.GET_SPONSORS_BY_SCHEME_ID)
                .path(String.valueOf(schemeId))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return FmListDTO.builder().success(false).build();
        }
    }

    public List<MiniSponsorDto> getSchemeDtoList(long schemeId) {
        FmListDTO fmListDTO = getSchemeSponsors(schemeId);
        if (fmListDTO.isSuccess()) {
            List<Object> objects = fmListDTO.getRows();
            List<MiniSponsorDto> miniSponsorDtos = new ArrayList<>();
            for (Object object : objects) {
                MiniSponsorDto miniSponsorDto = MiniSponsorDto.from(object);
                if (miniSponsorDto != null)
                    miniSponsorDtos.add(miniSponsorDto);
            }
            return miniSponsorDtos;
        }
        return new ArrayList<>();
    }

}
