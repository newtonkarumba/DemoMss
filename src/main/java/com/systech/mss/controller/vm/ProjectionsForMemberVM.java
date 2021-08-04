package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectionsForMemberVM {
    long mssUserId;
    long memberId;
    int age;
    long reasonId;

    @Ignore
    String projectionType = "RetirementsUnreduced";
}
