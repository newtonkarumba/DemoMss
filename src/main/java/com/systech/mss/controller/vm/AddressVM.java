
package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressVM {

    @Ignore
    private String building;

    @Ignore
    private String country;

    @Ignore
    private String email;

    @Ignore
    private String fixedPhone;

    @Ignore
    private double lat;

    @Ignore
    private double lng;

    @Ignore
    private String postalAddress;

    @Ignore
    private String road;

    @Ignore
    private String secondaryPhone;

    @Ignore
    private String town;

    @Ignore
    private String businessHours;
}
