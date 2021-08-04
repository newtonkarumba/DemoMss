package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreateAdminVM implements Serializable {
    @Ignore
    private long id;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String email;
    private String cellPhone;
    private String login;
}
