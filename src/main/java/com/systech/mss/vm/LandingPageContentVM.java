package com.systech.mss.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LandingPageContentVM {
    @Ignore
    private String welcomeMessage;

    @Ignore
    private long logo=0l; //comes from Documents id

    @Ignore
    private long pensionerImage=0l; //comes from Documents id

    @Ignore
    private long loginImage=0l;

    @Ignore
    private long memberIcon=0l;

    @Ignore
    private long pensionerIcon=0l;

    @Ignore
    private String whySaveMessage;


    @Ignore
    private String memberMessage;

    @Ignore
    private String pensionerMessage;

    @Ignore
    private String mapLoc;

}
