package com.systech.mss.controller.vm;


import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SearchMemberDetailsVm implements Serializable {
    @Ignore
    String sponsorRefNo;
    @Ignore
    long sponsorId;
    @Ignore
    long schemeId;
    @Ignore
    String login;  //See User entity
    @Ignore
    long memberId;
    @Ignore
    String phone;
}
