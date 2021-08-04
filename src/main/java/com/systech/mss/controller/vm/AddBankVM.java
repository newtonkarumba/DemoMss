package com.systech.mss.controller.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

import static com.systech.mss.config.Constants.PASSWORD_MAX_LENGTH;
import static com.systech.mss.config.Constants.PASSWORD_MIN_LENGTH;

@Getter
@Setter
public class AddBankVM {
    private long bankDetailsId;

    private long memberId;

    private long bankCode;

    private long bankBranchCode;

    private String accountName;

    private String accountNumber;

    private String defaultPoint;


}
