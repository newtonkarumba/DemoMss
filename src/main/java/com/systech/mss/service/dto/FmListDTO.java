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
public class FmListDTO extends BaseDTO { //DO NOT REMOVE BaseDTO

//    @Ignore
//    private Object totalCount;

    @Ignore
    private Object totalcount;  //Object not int, can be String or int

    private boolean success;

    @Ignore
    private List<Object> rows;

    @Ignore
    private String message;

    @Ignore
    private String fileName;//DO not Remove.Tony using this

    @Ignore
    private int status;
}
