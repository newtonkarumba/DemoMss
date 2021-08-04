package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.FmCrmController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.service.FMCRMClient;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.CumulativeInterestDTO;
import com.systech.mss.service.dto.FmListBooleanDto;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.SponsorDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FmCrmControllerImpl extends BaseController implements FmCrmController  {

    @Inject
    private FMCRMClient fmcrmClient;
    @Inject
    private FundMasterClient fundMasterClient;

    @Override
    public Response getSponsorsByCrmId(long id) {
        FmListBooleanDto fmListBooleanDto = fmcrmClient.getSponsorsByCrmId(id);
        if (fmListBooleanDto.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListBooleanDto.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorMemberListing(Long id, String profile, Long schemeId, int start, int size) {
        FmListDTO fmListDTO = fmcrmClient.getSponsorMemberListing(id,profile, schemeId,start,size);
        if(fmListDTO.isSuccess()){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();

    }

    @Override
    public Response getCountOfMembers(Long id) throws IOException, ParseException {

        FmListBooleanDto fmListBooleanDto = fmcrmClient.getSponsorsByCrmId(id);


        if (fmListBooleanDto.isSuccess()) {
            List<Object> objects=fmListBooleanDto.getRows();
            List<SponsorDTO> sponsorDTOS=new ArrayList<>();
            if(!objects.isEmpty()){
                for(Object obj: objects){
                    String jsonString=new ObjectMapper().writeValueAsString(obj);
                    JSONParser jsonParser =new JSONParser();
                    JSONObject jsonObject= (JSONObject) jsonParser.parse(jsonString);
                    if(jsonObject != null){
                        SponsorDTO sponsorDTO=new SponsorDTO();
                        sponsorDTO.setId(jsonObject.get("id").toString());
                        sponsorDTO.setName(jsonObject.get("name").toString());
                        sponsorDTO.setSchemeId(jsonObject.get("schemeId").toString());
                        sponsorDTOS.add(sponsorDTO);
                    }
                }
            }

            List<SponsorMembersCountVm> counts=new ArrayList<>();
            int  tempCount;
            for(SponsorDTO sponsors: sponsorDTOS){
                tempCount=fmcrmClient.getSponsorMemberListing(Long.valueOf(sponsors.getId()),
                        "SPONSOR",
                        Long.valueOf(sponsors.getSchemeId()),
                        0,10000000)
                        .getRows()
                        .size();

                counts.add(new SponsorMembersCountVm(sponsors.getName(),tempCount));
            }

            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(counts).build())
                    .build();

        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getCountOfSponsorsAndMembers(Long id) throws IOException, ParseException {

        FmListBooleanDto fmListBooleanDto = fmcrmClient.getSponsorsByCrmId(id);
        Config config=configRepository.getActiveConfig();

        if (fmListBooleanDto.isSuccess()) {
            List<Object> objects=fmListBooleanDto.getRows();
            List<SponsorDTO> sponsorDTOS=new ArrayList<>();
            if(!objects.isEmpty()){
                for(Object obj: objects){
                    String jsonString=new ObjectMapper().writeValueAsString(obj);
                    JSONParser jsonParser =new JSONParser();
                    JSONObject jsonObject= (JSONObject) jsonParser.parse(jsonString);
                    if(jsonObject != null){
                        SponsorDTO sponsorDTO=new SponsorDTO();
                        sponsorDTO.setId(jsonObject.get("id").toString());
                        sponsorDTO.setName(jsonObject.get("name").toString());
                        sponsorDTO.setSchemeId(jsonObject.get("schemeId").toString());
                        sponsorDTOS.add(sponsorDTO);
                    }
                }
            }

            int  tempCount=0;
            if(config.getClient().equals(Clients.ETL)){

            }
            else {
                for (SponsorDTO sponsors : sponsorDTOS) {
                    tempCount = tempCount + fmcrmClient.getSponsorMemberListing(Long.valueOf(sponsors.getId()),
                            "SPONSOR",
                            Long.valueOf(sponsors.getSchemeId()),
                            0, 10000000)
                            .getRows()
                            .size();

                }
            }
            ObjectMapper objectMapper=new ObjectMapper();
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("membersCount", tempCount)
                    .add("sponsorsCount", fmListBooleanDto.getRows().size())
                    .build();

            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(objectMapper.readValue(jsonObject.toString(),Object.class)).build())
                    .build();

        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getFMCRMUserId(String email) throws IOException, ParseException {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(fmcrmClient.getFMCRMUserId(email)).build())
                .build();
    }

    @Override
    public Response getFmUserDetailsById(long id) throws JsonProcessingException, ParseException {
        FmListBooleanDto fmListBooleanDto=fmcrmClient.getFmUserDetailsById(id);
        if(fmListBooleanDto.isSuccess()){
            List<Object> users=fmListBooleanDto.getRows();
            if(!users.isEmpty()){
                Object user=users.get(0);
                String jsonString=new ObjectMapper().writeValueAsString(user);
                JSONParser jsonParser =new JSONParser();
                JSONObject jsonObject= (JSONObject) jsonParser.parse(jsonString);
                if(jsonObject != null){
                    FMUserVM fmUserVM=new FMUserVM();
                    fmUserVM.setId(jsonObject.get("id").toString());
                    fmUserVM.setEmail(jsonObject.get("email").toString());
                    fmUserVM.setName(jsonObject.get("name").toString());
                    fmUserVM.setPhoneNumber(jsonObject.get("phoneNumber").toString());
                    fmUserVM.setProfile(jsonObject.get("profile").toString());
                    fmUserVM.setProfileId(jsonObject.get("profileId").toString());

                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(fmUserVM).build())
                            .build();
                }
                return ErrorMsg("Error parsing user object");

            }
            return ErrorMsg("User Not Found");


        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getMemberCumulativeStatement(long memberId, long schemeId) throws IOException, ParseException {
        CumulativeInterestDTO cumulativeInterestDTO=fmcrmClient.getMemberCumulativeStatement(memberId,schemeId);
        if(cumulativeInterestDTO.isSuccess()){

            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(cumulativeInterestDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }
}