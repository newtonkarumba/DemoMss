package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberClassesVm {
    @Ignore
    private String sponsorProdNo;
    @Ignore
    private String sponsorName;
    @Ignore
    private String memberClassName;
    @Ignore
    private String memberClasCode;
    @Ignore
    private String memberClassId;

    public static MemberClassesVm from(Object memberClassesDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(memberClassesDetails);
            return objectMapper.readValue(jsonString, MemberClassesVm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
