package com.systech.mss.service;

import com.systech.mss.config.APICall;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.SponsorConfig;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.repository.SponsorConfigRepository;
import com.systech.mss.service.dto.FmListDTO;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class SponsorConfigService {
    private Client client;

    private WebTarget target;

    @Inject
    private Logger log;

    @Inject
    private SponsorConfigRepository sponsorConfigRepository;

    @Inject
    private ConfigRepository configRepository;

    Config config;

    SponsorConfig sponsorConfig;

    private MultivaluedMap<String, Object> myHeaders;

    public SponsorConfig createConfig(SponsorConfig sponsorConfig){
        return sponsorConfigRepository.create(sponsorConfig);
    }

    public FmListDTO getConfigs(long id) {
        try {
            Response response = target.path(APICall.GET_CONFIGS)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }
    public List<SponsorConfig> getAll(){
        List<SponsorConfig> sponsorConfigs=sponsorConfigRepository.findAll();
        for(SponsorConfig sc: sponsorConfigs){

            sc.setShortDate(sc.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            sc.setShortDateTime(sc.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));


    }
        return sponsorConfigs;
    }

}
