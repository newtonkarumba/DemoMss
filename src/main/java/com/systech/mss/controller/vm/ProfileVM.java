package com.systech.mss.controller.vm;


import com.systech.mss.domain.LoginIdentifier;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProfileVM implements Serializable {

    @Ignore
    private long id = -1L;

    @Ignore
    private String name;

    @Ignore
    private String loginIdentifierName;

    @Ignore
    private LoginIdentifier loginIdentifier;

    public LoginIdentifier getLoginIdentifier() {
        LoginIdentifier[] loginIdentifiers = LoginIdentifier.values();
        for (LoginIdentifier identifier : loginIdentifiers) {
            if (identifier.getName().equalsIgnoreCase(getLoginIdentifierName()))
                return identifier;
        }
        return LoginIdentifier.EMAIL;
    }
}
