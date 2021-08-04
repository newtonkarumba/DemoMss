package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class FaqVM implements Serializable {

    @Ignore
    long id = 0L;

    long profileId;

    String title;

    @Ignore
    String subtitle;

    String body;
}
