package com.systech.smileIdentity.DTO;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessSelfieDto {

    private String userId;

    @Ignore
    private String jobId;

    private int jobType;

    private int imageType=2;

    private String image;

    @Ignore
    private String idFirstName="";
    @Ignore
    private String idMiddleName="";
    @Ignore
    private String idLastName="";
    @Ignore
    private String idCountry="KE";
    @Ignore
    private String idType="NATIONAL_ID";
    @Ignore
    private String idNumber="";
    @Ignore
    private String idDob="";
    @Ignore
    private String idPhoneNumber="";
    @Ignore
    private String idConfirmed="true";
    @Ignore
    private String userType;
}
