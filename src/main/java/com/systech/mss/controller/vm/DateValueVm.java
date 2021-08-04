package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class DateValueVm implements Serializable {
    @Ignore
    private long dateNumber;
}
