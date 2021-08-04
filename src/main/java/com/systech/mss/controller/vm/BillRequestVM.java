package com.systech.mss.controller.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillRequestVM {

    private int schemeId;
    private int sponsorId;
    private String bookingDate;
    private String dueDate;
    private int year;
    private MonthAbrv month;
    private String finalPath;



}

