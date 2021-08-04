package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberSponsorVM implements Serializable {
    public String id;
    public String phoneNumber;
    public String emailAddress;
    public String pin;
    public String employerRefNo;
    public String name;

    public static MemberSponsorVM map(String s) {
        try {
            return new ObjectMapper().readValue(s, MemberSponsorVM.class);
        } catch (IOException e) {
            return null;
        }
    }
}
