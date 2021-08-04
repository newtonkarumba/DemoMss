package com.systech.mss.service.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class MiniSponsorDto {
    public int id;
    public String schemeId;
    public String name;
    public String refNo;
    public String fixedPhone;
    public String email;
    public String employerRefNo;
    public String status;
    public String sponsorNo;

    public static MiniSponsorDto from(Object object) {
        try {
            String json = StringUtil.toJsonString(object);
            return new ObjectMapper()
                    .readValue(json, MiniSponsorDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
