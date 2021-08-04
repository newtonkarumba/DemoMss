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
public class PensionerDTO {
    private long totalCount;
    private boolean success;
    private List<Object> rows;
}