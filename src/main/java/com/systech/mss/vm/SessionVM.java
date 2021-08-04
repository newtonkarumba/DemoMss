package com.systech.mss.vm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionVM  {
    private WeekdaysEnum days;
    private int count;



}
