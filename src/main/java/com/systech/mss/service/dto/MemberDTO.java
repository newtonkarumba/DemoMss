package com.systech.mss.service.dto;

import com.systech.mss.util.Ignore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

//     "mbshipStatus": "0",
//             "nationalPenNo": "",
//             "success": true,
//             "profile": "SPONSOR",
//             "sponsorId": 6607,
//             "name": "SYSTECH DEFINED CONTRIBUTION SCHEME Sponsor",
//             "schemeId": 6603,
//             "sponsorRefNo": "5212",
//             "email": "officialtonyngeno123@gmail.com",
//             "cellPhone": "",
//             "memberId": 0

    @Ignore
    @Column
    private String mbshipStatus;

    @Ignore
    @Column
    private String nationalPenNo;

    @Ignore
    @Column
    private String pensionerId;

    @Ignore
    @Transient
    private boolean success;

    @Ignore
    @Column
    private String profile;

    @Ignore
    @Column
    private long sponsorId= 0L;

    @Ignore
    @Column
    private long userId= 0L;

    @Ignore
    @Column
    private String name;

    @Ignore
    @Column
    private long schemeId= 0L;

    @Ignore
    @Column
    private String sponsorRefNo;

    /**
     * Marked transient to avoid repeated column exception
     */
    @Ignore
    @Transient
    private String email;

    @Ignore
    @Column
    private String cellPhone;

    @Ignore
    @Transient
    private String message;

    @Ignore
    @Column
    private String staffNo;

    @Ignore
    @Column
    private String accountStatus;

    @Ignore
    @Column
    private long memberId= 0L;

    @Ignore
    @Transient
    private long beneficiaryId= 0L;

    @Ignore
    @Column
    private String schemeName;

    @Ignore
    @Column
    private String sponsorName;

}
