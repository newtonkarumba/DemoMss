
package com.systech.mss.controller.vm;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.systech.mss.util.Ignore;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Row {

    @Ignore
    private BigDecimal avc;
    @Ignore
    private BigDecimal ee;
    @Ignore
    private BigDecimal er;
    @Ignore
    private String exception;
    @Ignore
    private Long id;
    @Ignore
    private String memberName;
    @Ignore
    private String membershipNo;
    @Ignore
    private String ssnitNumber;
    @Ignore
    private BigDecimal salary;
    @Ignore
    private BigDecimal eeValidated;
    @Ignore
    private BigDecimal erValidated;
    @Ignore
    private BigDecimal avcValidated;
    @Ignore
    private BigDecimal salaryValidated;
    @Ignore
    private BigDecimal penaltyPayment;
    @Ignore
    private BigDecimal schemeId;
    @Ignore
    private BigDecimal sponsorId;



}
