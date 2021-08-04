
package com.systech.smileIdentity.DTO;

import com.systech.mss.util.Ignore;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SmileIdentityResponseDTO {

    private String code;


    @Ignore
    private List<History> history;

    @Ignore
    private ImageLinks image_links;


    private boolean job_complete;

    private boolean job_success;

    private Result result;

   
    private String signature;
   
    private String timestamp;

}
