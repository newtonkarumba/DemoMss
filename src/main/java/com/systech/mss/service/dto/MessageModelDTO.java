package com.systech.mss.service.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.*;

import java.io.IOException;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageModelDTO extends BaseDTO{
    boolean success;
    String message;
    @Ignore
    BigDecimal amount;
    @Ignore
    String code;
    @Ignore
    int year;
    @Ignore
    String month;

    public static MessageModelDTO from(String s) {
        try {
           return new ObjectMapper()
                    .readValue(s,MessageModelDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
