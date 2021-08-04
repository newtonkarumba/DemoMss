package com.systech.smileIdentity.DTO;

import com.systech.mss.util.Ignore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SelfieListDto {
    @Ignore
    String message;
    @Ignore
    String msg;
    @Ignore
    String status;
    boolean success;
    @Ignore
    Object data;
    @Ignore
    Object rows;
    @Ignore
    int count;
    @Ignore
    int totalcount;
}
