package com.systech.smileIdentity.Service.vm;

import com.systech.mss.util.Ignore;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SelfieSource;
import com.systech.smileIdentity.model.SelfieStatus;
import com.systech.smileIdentity.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserImagesVm {

    private long id;

    private Long userId;

    private Long cycleId;

    private String jobId;

    private String livenessCheck;

    private String confidenceValue;

    private String image;

    private SelfieSource source;

    private UserType userType;

    private SelfieAction selfieAction;

    private SelfieStatus status;

    private Date createdDate;

    private Date createdDateSource;

    @Ignore
    private String createdDateShort;

    @Ignore
    private String createdDateSourceShort;

}
