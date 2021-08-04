package com.systech.mss.service;

import com.systech.mss.config.APICall;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.repository.MobileMoneyRepository;
import com.systech.mss.service.dto.FmListDTO;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
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

public class MobileMoneyConfigService {

    private Client client;

    private WebTarget target;

    @Inject
    private Logger log;

    @Inject
    private MobileMoneyRepository mobileMoneyRepository;

    MobileMoneyConfig mobileMoneyConfig;

    private MultivaluedMap<String, Object> myHeaders;

//    @PostConstruct
//    public void setup() {
//        client = ClientBuilder.newClient();
//        if (getMMConfig().isPresent()) {
//            mobileMoneyConfig = getMMConfig().get();
//            myHeaders = new MultivaluedHashMap<>();
//            myHeaders.add("username", mobileMoneyConfig.  .getFmUsername());
//            myHeaders.add("password", config.getFmPassword());
//            target = client.target(config.getFmBasePath()); //http:168.235.82.130:8084/Xe/api
//        }
//    }

    public MobileMoneyConfig createConfig(MobileMoneyConfig mobileMoneyConfig){
        return mobileMoneyRepository.create(mobileMoneyConfig);
    }

    private Optional<MobileMoneyConfig> getMMConfig() {
        return mobileMoneyRepository
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

    public List<MobileMoneyConfig> getAll(){
        List<MobileMoneyConfig> mobileMoneyConfigs=mobileMoneyRepository.findAll();
        return mobileMoneyConfigs;
    }






}
