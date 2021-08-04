package com.systech.mss.service;

import com.systech.mss.config.APICall;
import com.systech.mss.domain.MailConfig;
import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.repository.MobileMoneyRepository;
import com.systech.mss.service.dto.FmListDTO;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class MailConfigService {
    private Client client;

    private WebTarget target;

    @Inject
    private Logger log;

    @Inject
    private MailConfigRepository mailConfigRepository;

    MailConfig mailConfig;

    private MultivaluedMap<String, Object> myHeaders;



    public MailConfig createConfig(MailConfig mailConfig){
        return mailConfigRepository.create(mailConfig);
    }

    private Optional<MailConfig> getMailConfig() {
        return mailConfigRepository
                .findAll()
                .stream()
                .findFirst();
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

    public List<MailConfig> getAll(){
        List<MailConfig> mailConfigs=mailConfigRepository.findAll();
        return mailConfigs;
    }






}
