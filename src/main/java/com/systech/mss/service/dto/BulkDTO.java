package com.systech.mss.service.dto;

import com.systech.mss.util.Ignore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString()
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkDTO {
    @Ignore
    private String message;

    @Ignore
    private String downloadUrl;

    @Ignore
    private boolean success;

}
