package com.systech.smileIdentity.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReEnrollUserDto {
    private String user_id;
    private boolean allow_re_enroll=true;
    private String partner_id="";
    private long timestamp;
    private String sec_key;
    private String email;
    private String environment;
}
