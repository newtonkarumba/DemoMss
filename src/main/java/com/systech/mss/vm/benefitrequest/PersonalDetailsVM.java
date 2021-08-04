package com.systech.mss.vm.benefitrequest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonalDetailsVM {
    String name;
    String memberNo;
    String dob;
    String email;
    long schemeId;
    long sponsorId;
}
