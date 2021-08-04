package com.systech.mss.controller.vm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OtpInitVM implements Serializable {
   private String otpIdentifier;
   private String value;//user email or phone
}
