package com.systech.smileIdentity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateUserDto {
    private String user_id;
    private boolean active=false;
    private String partner_id="";
    private long timestamp;
    private String sec_key;
    private String email;
    private String environment;
}
