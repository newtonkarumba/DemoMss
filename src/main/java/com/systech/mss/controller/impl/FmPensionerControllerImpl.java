package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.FmPensionerController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.*;
import com.systech.mss.vm.benefitrequest.AddBeneficiaryVM;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FmPensionerControllerImpl implements FmPensionerController {

    @Inject
    private ActivityTrailRepository trailRepository;


    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    FundMasterClient fundMasterClient;

    @Inject
    Logger logger;

    @Override
    public Response getPensionerDetails(long pensionerId) {
        PensionerDTO pensionerDTO = fundMasterClient.getPensionerDetails(pensionerId);
        if (pensionerDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(pensionerDTO.getRows().get(0)).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPenionerPersonalInfo(long pensionerId) {
        PensionerDTO pensionerDTO = fundMasterClient.getPensionerDetails(pensionerId);
        if (pensionerDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(pensionerDTO.getRows().get(0)).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPensionerEmploymentDetails() {
        return null;
    }

    @Override
    public Response getPensionerPayrolls(String pensionerNo, Long schemeId) {
        PensionerPayrollDTO pensionerPayrollDTO = fundMasterClient.getPensionerPayrolls(pensionerNo, schemeId);
        if (pensionerPayrollDTO != null)
            if (pensionerPayrollDTO.getSuccess().equalsIgnoreCase("true")) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(pensionerPayrollDTO.getRows()).build())
                        .build();
            }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response filterPayrolls(String pensionerNo, long schemeId, String month, String year) throws IOException {
        PensionerPayrollDTO pensionerPayrollDTO = fundMasterClient.getPensionerPayrolls(pensionerNo, schemeId);
        List<PensionerPayrollVM> payrollVMList=new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (pensionerPayrollDTO.getSuccess().equalsIgnoreCase("true")){
            List<Object> payrolls=pensionerPayrollDTO.getRows();
            if (!payrolls.isEmpty()) {
                for(Object payroll:payrolls){
                    PensionerPayrollVM pensionerPayrollVM=new PensionerPayrollVM();
                    String jsonString = objectMapper.writeValueAsString(payroll);
                    pensionerPayrollVM= objectMapper.readValue(jsonString, PensionerPayrollVM.class);
                    payrollVMList.add(pensionerPayrollVM);
                }
                //to do filter
                List<PensionerPayrollVM> filteredPayrolls=new ArrayList<>();
                if(month != null && year != null){

                    for(PensionerPayrollVM p:payrollVMList){
                        if(p.getMonth().equals(month) && p.getYear().equals(year)){
                            filteredPayrolls.add(p);
                        }
                    }
//
//                        filteredPayrolls=payrollVMList.stream()
//                                .filter(p -> p.getMonth().equals(month) &&
//                                        p.getYear().equals(year))
//                                .collect(Collectors.toList());
                }else if(month != null ){

                    for(PensionerPayrollVM p:payrollVMList){
                        if(p.getMonth().equals(month) ){
                            filteredPayrolls.add(p);
                        }
                    }
//                    filteredPayrolls=payrollVMList.stream()
//                            .filter(p -> p.getMonth().equals(month))
//                            .collect(Collectors.toList());
                }else if(year != null ){

                    for(PensionerPayrollVM p:payrollVMList){
                        if(p.getYear().equals(year)){
                            filteredPayrolls.add(p);
                        }
                    }
//                    filteredPayrolls=payrollVMList.stream()
//                            .filter(p -> p.getYear().equals(year))
//                            .collect(Collectors.toList());
                }
                if(!filteredPayrolls.isEmpty()) {
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(filteredPayrolls).build())
                            .build();
                }
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ErrorVM.builder().success(false).msg(
                                "No payrolls found"
                        ).build())
                        .build();
            }

        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPensionerPayrollDeductions(String pensionerNo, long schemeId) {
        PensionerDTO pensionerDTO = fundMasterClient.getPensionerPayrollDeductions(pensionerNo, schemeId);
        if (pensionerDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(pensionerDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPensionerPayrollYears() {
        FmListDTO fmListDTO = fundMasterClient.getPensionerPayrollYears();
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }


    @Override
    public Response getPensionerAdvice(String schemeId,String pensionerId,String year) {
        FmListDTO fmListDTO = fundMasterClient.getPensionAdvice(schemeId,pensionerId,year);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPensionerBeneficiaries(long memberId) {
        FmListDTO fmListDTO = fundMasterClient.getPensionerBeneficiaries(memberId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response addPensionerBeneficiary(@Valid AddBeneficiaryVM addBeneficiaryVM) {
        PensionerDTO pensionerDTO = fundMasterClient.addPensionerBeneficiary(addBeneficiaryVM);
        if (pensionerDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(pensionerDTO.isSuccess()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }


    private Response ErrorMsg(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    @Override
    public Response getPensionerCOE(long mssUserId, String pensionerNo) throws IOException {

        activityTrailService.logActivityTrail(mssUserId, "fetched pensioner Certificate of existence");

        FmListBooleanDto fmListBooleanDto=fundMasterClient.getPensionerCOE(pensionerNo);
        if(fmListBooleanDto.isSuccess()) {
            List<Object> statements = fmListBooleanDto.getRows();
            if (!statements.isEmpty()) {
                Object statement = statements.get(0);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(statement);
                PensionerCEOVM pensionerCEOVM = objectMapper.readValue(jsonString, PensionerCEOVM.class);
                String start=(pensionerCEOVM.getCycleStartDate()).substring(0,10);
                String stop=(pensionerCEOVM.getCycleStopDate()).substring(0,10);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(start, formatter);
                LocalDate today = LocalDate.now();
                LocalDate stopDate = LocalDate.parse(stop, formatter);
                long daysBetween=(ChronoUnit.DAYS.between(today, stopDate));
                if(daysBetween<0){
                    pensionerCEOVM.setDaysRemaining(0+" day(s) remaining");
                    if(pensionerCEOVM.getStatus().equals("PENDING")) {
                        pensionerCEOVM.setStatus("OVERDUE");
                    }
                }else {
                    pensionerCEOVM.setDaysRemaining((ChronoUnit.DAYS.between(startDate, stopDate))+" day(s) remaining");
                }
                pensionerCEOVM.setCycle(String.valueOf(startDate.getYear()));
                pensionerCEOVM.setCenter("N/A");
                pensionerCEOVM.setStartDate(start);
                pensionerCEOVM.setStopDate(stop);

                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(pensionerCEOVM).build())
                        .build();
            }
        }
        return ErrorMsg("Mss Api call failed");
    }
}
