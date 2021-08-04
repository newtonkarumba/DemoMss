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
public class CostCenterVm implements Serializable {
    @Ignore
    private String code;
    @Ignore
    private String name;
    @Ignore
    private long id;
    @Ignore
    private String companyCode;
    @Ignore
    private String companyId;
    @Ignore
    private String sponsorProdNo;
    @Ignore
    private String companyName;
    @Ignore
    private String sponsorName;

    public static CostCenterVm from(JSONObject jsonObject) {
        try {
            String s = jsonObject.toString();
            CostCenterVm costCenterVm = new ObjectMapper().readValue(s, CostCenterVm.class);
            if (costCenterVm.getCode() == null)
                costCenterVm.setCode(costCenterVm.getCompanyCode());
            if (costCenterVm.getName() == null)
                costCenterVm.setName(costCenterVm.getCompanyName());
            if (costCenterVm.getCompanyId() != null)
                costCenterVm.setId(Long.parseLong(costCenterVm.getCompanyId()));
            return costCenterVm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
