package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBillVm {
    @Ignore
    long batch;
    @Ignore
    String json;
}
