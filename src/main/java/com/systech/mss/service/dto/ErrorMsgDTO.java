package com.systech.mss.service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMsgDTO extends BaseDTO {
    Boolean success;
    String message;
}
