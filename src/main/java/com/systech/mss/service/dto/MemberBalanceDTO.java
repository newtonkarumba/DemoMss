package com.systech.mss.service.dto;

import com.systech.mss.controller.vm.MemberBalanceVM;
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
public class MemberBalanceDTO extends BaseDTO{

    @Ignore
    private long totalcount;

    private boolean success;

    @Ignore
    private List<MemberBalanceVM> rows;

    @Ignore
    private String message;
}
