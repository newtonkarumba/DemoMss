package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ActivityTrailVM {
    @Ignore
    public long id;

    @Ignore
    public  long userId;

    @Ignore
    public LocalDateTime createdDate;

    @Ignore
    public String description;

    @Ignore
    public String userName;

    @Ignore
    public String shortDate;

    @Ignore
    public String shortDateTime;

    @Ignore
    public String name;

    @Ignore
    public String profile;
}
