package com.systech.mss.service.dto;

import com.systech.mss.controller.vm.OtpVM;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.systech.mss.config.Constants.PASSWORD_MAX_LENGTH;
import static com.systech.mss.config.Constants.PASSWORD_MIN_LENGTH;
@Getter
@Setter
@ToString
public class LoginDTO {

//    @Pattern(regexp = LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private boolean rememberMe = true;

    public static LoginDTO from(OtpVM otpVM) {
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUsername(otpVM.getUsername());
        loginDTO.setPassword(otpVM.getPwd());
        return loginDTO;
    }
}