package com.systech.mss.controller.impl;

import com.systech.mss.controller.FmPrincipalOfficerController;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FMPrincipalOfficerClient;
import com.systech.mss.service.dto.FmListBooleanDto;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class FmPrincipalOfficerControllerImpl extends BaseController  implements FmPrincipalOfficerController {

    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    private FMPrincipalOfficerClient fmPrincipalOfficerClient;

    @Override
    public Response getPrincipalOfficerDetails(long mssUserid, long principalOfficerId) {

        activityTrailService.logActivityTrail(mssUserid, "fetched principal officer details of user id "+principalOfficerId);

        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPrincipalOfficerDetails(principalOfficerId);
        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
        }
        else {
            return ErrorMsg("Mss Api call failed");
        }
    }

    @Override
    public Response getPrincipalOfficerSchemes(long mssUserid, long principalOfficerId) {

        activityTrailService.logActivityTrail(mssUserid, "fetched principal officer schemes of user id "+principalOfficerId);

        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPrincipalOfficerSchemes(principalOfficerId);
        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
        }
        else {
            return ErrorMsg("Mss Api call failed");
        }
    }

    @Override
    public Response getPrincipalOfficerEmployers(long mssUserid, long principalOfficerId, long schemeId) {

        activityTrailService.logActivityTrail(mssUserid, "fetched principal officer employees of user id "+principalOfficerId +" in scheme id"+schemeId);


        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPrincipalOfficerEmployers(principalOfficerId,schemeId);
        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
        }
        else {
            return ErrorMsg("Mss Api call failed");
        }
    }

    @Override
    public Response getPrincipalOfficerCostCenters(long mssUserid, long principalOfficerId, long schemeId, long sponsorId) {

        activityTrailService.logActivityTrail(mssUserid, "fetched principal officer cost centers of user id "+principalOfficerId +" in scheme id"+schemeId + " and sponsor id "+sponsorId);

        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPrincipalOfficerCostCenters(principalOfficerId,schemeId,sponsorId);
        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
        }
        else {
            return ErrorMsg("Mss Api call failed");
        }
    }

    @Override
    public Response getPaidClaims(long mssUserid, long schemeId, long sponsorId, String dateFrom, String dateTo, int start, int size) {
        activityTrailService.logActivityTrail(mssUserid,"Fetched paid claims for scheme Id "+schemeId+" and sponsor id "+sponsorId);
        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPaidClaims(schemeId,sponsorId,dateFrom,dateTo,start,size);
//        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
//        }
//        else {
//            return ErrorMsg("Mss Api call failed");
//        }
    }

    @Override
    public Response getAllClaims(long mssUserid, long schemeId, long sponsorId, String dateFrom, String dateTo, int start, int size) {
        activityTrailService.logActivityTrail(mssUserid,"Fetched all claims for scheme Id "+schemeId+" and sponsor id "+sponsorId);
        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getAllClaims(schemeId,sponsorId,dateFrom,dateTo,start,size);
//        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
//        }
//        else {
//            return ErrorMsg("Mss Api call failed");
//        }
    }

    @Override
    public Response getPaidClaims(long mssUserid, long schemeId, long sponsorId, int start, int size) {
        activityTrailService.logActivityTrail(mssUserid,"Fetched paid claims for scheme Id "+schemeId+" and sponsor id "+sponsorId);
        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getPaidClaims(schemeId,sponsorId,start,size);
//        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
//        }
//        else {
//            return ErrorMsg("Mss Api call failed");
//        }
    }

    @Override
    public Response getAllClaims(long mssUserid, long schemeId, long sponsorId,  int start, int size) {
        activityTrailService.logActivityTrail(mssUserid,"Fetched all claims for scheme Id "+schemeId+" and sponsor id "+sponsorId);
        FmListBooleanDto fmListDTO=fmPrincipalOfficerClient.getAllClaims(schemeId,sponsorId,start,size);
//        if(fmListDTO.isSuccess()){
            return SuccessMsg("success",fmListDTO.getRows());
//        }
//        else {
//            return ErrorMsg("Mss Api call failed");
//        }
    }

}
