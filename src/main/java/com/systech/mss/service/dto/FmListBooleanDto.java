package com.systech.mss.service.dto;

import com.systech.mss.util.Ignore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FmListBooleanDto {
    private long totalCount;
    @Ignore
    private boolean success;
    private List<Object> rows;
}
