package com.systech.mss.controller.vm;

import com.systech.mss.service.dto.UserDTO;
import com.systech.mss.util.Ignore;

import javax.validation.constraints.Size;

import static com.systech.mss.config.Constants.PASSWORD_MAX_LENGTH;
import static com.systech.mss.config.Constants.PASSWORD_MIN_LENGTH;

public class ManagedUserVM extends UserDTO {

    @Ignore
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
    }

    public ManagedUserVM(
            long id,
            String login,
            String email,
            boolean activated,
            String langKey,
            String fmIdentifier,
            long profileId
    ) {

        super(id, login, email, activated, langKey, fmIdentifier, profileId);

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM : " + super.toString();
    }
}