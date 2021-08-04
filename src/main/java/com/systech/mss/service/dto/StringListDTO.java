package com.systech.mss.service.dto;

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
public class StringListDTO {
    private String totalCount;
    private String success;
    private List<Object> rows;
}
