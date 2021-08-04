package com.systech.mss.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillExceptionDTO {
    private List<ContributionBillingDTO> rows;


}
