package com.systech.mss.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PensionerPayrollDTO {
    private long totalCount;
    private String success;
    private List<Object> rows;
}