package com.systech.mss.controller.vm;

import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@ToString
@Builder
public class BenefitDeclineVM {
    long id;
    long userId;
    String reason;
}
