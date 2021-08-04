package com.systech.mss.vm;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutgoingSMSVM {
    private String recipient;
    private String msg;
    private String type; //member, pensioner ...etc
    private boolean status; //true or false
}
