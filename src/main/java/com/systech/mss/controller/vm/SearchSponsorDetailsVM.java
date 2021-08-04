package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SearchSponsorDetailsVM implements Serializable {
    @Ignore
    String email;
    @Ignore
    long schemeId;
    @Ignore
    String phone;
}
