package com.systech.mss.service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BenefitReasonDTO {
    boolean success;
    String reason;
}
