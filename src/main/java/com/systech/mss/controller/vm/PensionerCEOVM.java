
package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PensionerCEOVM {

    
    private String cycleStartDate;
    
    private String cycleStopDate;
    
    private String dateReceived;
    
    private String memberNo;
    
    private String name;
    
    private String pension;
    
    private String pensionerNo;
    
    private String status;

    @Ignore
    private String daysRemaining;

    @Ignore
    private String cycle;

    @Ignore
    private String center;

    @Ignore
    private String startDate;

    @Ignore
    private String stopDate;

    @Ignore
    private long cycleId;



}
