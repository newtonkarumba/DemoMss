package com.systech.mss.service.dto;

import com.systech.mss.controller.vm.BillVM;
import com.systech.mss.util.Ignore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(exclude = {
        "rows"
})
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SponsorContributionDTO extends BaseDTO {

    private String totalCount;

    private List<Object> rows;

    @Ignore
    private List<BillVM> billVMS;

    @Ignore
    private boolean success;
    @Ignore
    private String message;
}
