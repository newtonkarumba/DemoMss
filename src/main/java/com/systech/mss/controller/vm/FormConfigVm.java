package com.systech.mss.controller.vm;

import com.systech.mss.domain.BeneficiaryFormConfig;
import com.systech.mss.domain.MemberFormConfigs;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormConfigVm {
    private ConfigFormName configFormName;

    @Ignore
    private MemberFormConfigs memberFormConfigs;

    @Ignore
    private BeneficiaryFormConfig beneficiaryFormConfig;
}
