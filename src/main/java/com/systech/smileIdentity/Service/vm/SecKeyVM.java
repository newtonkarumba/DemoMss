package com.systech.smileIdentity.Service.vm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecKeyVM {
    String signature;
    long timestamp;
    String Environment;
    String partnerId;
}
