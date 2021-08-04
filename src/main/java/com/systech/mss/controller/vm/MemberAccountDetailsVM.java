package com.systech.mss.controller.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MemberAccountDetailsVM implements Serializable {
    public String contributions;
    public String returns;
    public String balances;
    public String withdrawals;
    public String dateOfLastContribution;
    public String lastContribution;
}
