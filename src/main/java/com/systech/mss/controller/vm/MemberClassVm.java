package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberClassVm implements Serializable {
    @Ignore
    private String code;
    @Ignore
    private String name;
    @Ignore
    private long id;
    @Ignore
    private String sponsorProdNo;
    @Ignore
    private String memberClassId;
    @Ignore
    private String sponsorName;
    @Ignore
    private String memberClassName;
    @Ignore
    private String memberClasCode;

    public static MemberClassVm from(JSONObject jsonObject) {
        try {
            String s = jsonObject.toString();
            MemberClassVm memberClassVm = new ObjectMapper().readValue(s, MemberClassVm.class);
            if (memberClassVm.getCode() == null)
                memberClassVm.setCode(memberClassVm.getMemberClasCode());
            if (memberClassVm.getName() == null)
                memberClassVm.setName(memberClassVm.getMemberClassName());
            if (memberClassVm.getMemberClassId() != null)
                memberClassVm.setId(Long.parseLong(memberClassVm.getMemberClassId()));
            return memberClassVm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MemberClassVm fromObject(Object memberClassDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(memberClassDetails);
            return objectMapper.readValue(jsonString, MemberClassVm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
