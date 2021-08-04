
package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TPFAVM {

    @Ignore
    private String avc;
    @Ignore
    private String avcer;
    @Ignore
    private String ee;
    @Ignore
    private String er;
    @Ignore
    private String exception;
    @Ignore
    private String id;
    @Ignore
    private String memberId;
    @Ignore
    private String membershipNo;
    @Ignore
    private String name;
    @Ignore
    private String receiptStatus;
    @Ignore
    private String receivedDate;
    @Ignore
    private String requestedDate;
}
