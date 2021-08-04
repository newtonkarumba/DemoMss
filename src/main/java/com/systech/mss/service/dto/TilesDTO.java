package com.systech.mss.service.dto;

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
public class TilesDTO extends BaseDTO  {

    @Ignore
    private Object totalcount;

    @Ignore
    private boolean success;

    @Ignore
    private List<Object> rows;

}
