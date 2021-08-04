package com.systech.mss.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundMasterBillExceptionDTO {
    private long billId;
    private long schemeId;
    private long sponsorId;
    private BigDecimal validatedEE;
    private BigDecimal validateER;
    private BigDecimal validatedAVC;
    private List<ContributionEntryDTO> rows;
    

}
