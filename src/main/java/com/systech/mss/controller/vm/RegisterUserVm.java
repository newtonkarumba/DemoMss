package com.systech.mss.controller.vm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserVm {
    private long userId;
    private String email;
    private long profileId;
}
