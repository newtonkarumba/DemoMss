
package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SponsorPensionerCountVM {

   
    private String dueForRetirement;
   
    private String memberCount;

    @Ignore
    private String leavers;

    private String name;
   
    private String newMembers;
   
    private String pensioners;
   
    private String sponsorRefNo;

}
