
package com.systech.smileIdentity.Service.vm;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SelfieSource;
import com.systech.smileIdentity.model.UserType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SelfieResultVM {
    private Long userId;

    private String jobId;

    private String image;

    private SelfieSource source;

    private Date createdDateSource;

    private UserType userType;

    private SelfieAction selfieAction;

    private String livenessCheck;

    private String confidenceValue;

    private long cycleId;

    private long amountPaid;
}
