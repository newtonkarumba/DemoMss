package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyClassVM implements Serializable {
    @Ignore
    private String sponsorProdNo;
    @Ignore
    private String sponsorName;
    @Ignore
    private String companyName;
    @Ignore
    private String companyCode;
    @Ignore
    private String companyId;

    public static CompanyClassVM from(Object companyClassDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(companyClassDetails);
            return objectMapper.readValue(jsonString, CompanyClassVM.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

