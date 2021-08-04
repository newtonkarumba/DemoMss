
package com.systech.smileIdentity.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result {



    private Actions Actions;

    private String ConfidenceValue;

    private String IsFinalResult;

    private String IsMachineResult;

    private String JSONVersion;

    private PartnerParams PartnerParams;

    private String ResultCode;

    private String ResultText;

    private String ResultType;

    private String SmileJobID;

    private String Source;

}
