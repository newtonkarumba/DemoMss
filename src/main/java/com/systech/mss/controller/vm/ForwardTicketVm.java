package com.systech.mss.controller.vm;

import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@ToString
@Builder
public class ForwardTicketVm {
    private long ticketId;
    private long profileId;
    private long userId;
}
