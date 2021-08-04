package com.systech.mss.controller.impl;

import com.systech.mss.config.Fields;
import com.systech.mss.controller.WhatIfAnalysis;
import com.systech.mss.controller.vm.ProjectionsForMemberVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.controller.vm.WhatIfAnalysisVM;
import com.systech.mss.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class WhatIfAnalysisImp extends BaseController implements WhatIfAnalysis {

    @Override
    public Response whatIfAnalysis(long mssUserId, @Valid WhatIfAnalysisVM whatIfAnalysisVM) {
        logActivityTrail(mssUserId, "Requested recent closing balances");
        Map<String, Object> o = calculate(whatIfAnalysisVM);
        if (Boolean.parseBoolean(o.get("success").toString())) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(o.get("data")).build())
                    .build();
        }
        return ErrorMsg(o.get("data").toString());
    }

    @Override
    public Response getProjectionsForMember(ProjectionsForMemberVM projectionsForMemberVM) {

        try {
            JSONObject jsonObject = fmMemberClient.getProjectionsForMember(projectionsForMemberVM);
            if (jsonObject != null)
                if (jsonObject.has("success")) {
                    if (Boolean.parseBoolean(
                            StringUtil.toString(jsonObject.get("success"))
                    )) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        JSONObject res=jsonArray.getJSONObject(0);
                        BigDecimal grossMonthlyPension = toBigDecimal(res.getString("monthlyPension"));
                        BigDecimal taxonMonthlyPension = toBigDecimal(res.getString("taxOnMonthlyPension"));
                        BigDecimal netMonthlyPension = grossMonthlyPension.subtract(taxonMonthlyPension);
                        log.info("netMonthlyPension*** " + netMonthlyPension);
                        res.put("netMonthlyPension", netMonthlyPension.toString());
                        return SuccessMsg("Done", res.toString());
                    } else {
                        return ErrorMsg(jsonObject.getString("message"));
                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered, please try again");
    }

    @Override
    public Response benefitsprojectioncalculation(ProjectionsForMemberVM projectionsForMemberVM) {
        return null;
    }

    Map<String, Object> calculate(WhatIfAnalysisVM whatIfAnalysisVM) {

        Map<String, Object> map = new HashMap<>();

        String ageAtExit = String.valueOf(whatIfAnalysisVM.getAgeAtExit());
        String memberId = String.valueOf(whatIfAnalysisVM.getMemberId());
        String schemeId = String.valueOf(whatIfAnalysisVM.getSchemeId());
        String rateOfReturn = String.valueOf(whatIfAnalysisVM.getReturnRate());
        String inflationRate = String.valueOf(whatIfAnalysisVM.getInflationRate());
        String salaryEscalationRate = String.valueOf(whatIfAnalysisVM.getSalaryEscalationRate());
        if (salaryEscalationRate.isEmpty())
            salaryEscalationRate = "0";
        String avcReceiptOption = "DEFAULT";
        String projectAVC = String.valueOf(whatIfAnalysisVM.getProjectedAvc());
        if (projectAVC.isEmpty())
            projectAVC = "0";
        String targetMonthlyPensionStr = String.valueOf(whatIfAnalysisVM.getTargetPension());

        BigDecimal targetMonthlyPension = new BigDecimal(targetMonthlyPensionStr);
        BigDecimal projectedMonthlyPension;
        BigDecimal targetLumpsumPayable;
        BigDecimal percentageNeeded = BigDecimal.ZERO;
        BigDecimal projectedMonthlyContribution;
        BigDecimal remainderAmount = BigDecimal.ZERO;
        String label;

        try {
            // PROJECTION API !!!!!!!!!!!!!!!!!!!!
            JSONObject jsonObject = fmMemberClient.calculateWhatIfAnalysis_(schemeId, memberId, avcReceiptOption, ageAtExit, rateOfReturn, salaryEscalationRate, projectAVC, inflationRate);

//            log.error("calculateWhatIfAnalysis_ " + jsonObject.toString());
            if (jsonObject != null) {
                if (jsonObject.getBoolean(Fields.SUCCESS)) {
                    JSONObject getContributionRateAndSalary = fmMemberClient.getCurrentMonthlyContributionAndBasicSalary(memberId, schemeId);

//                log.error("getCurrentMonthlyContributionAndBasicSalary "+getContributionRateAndSalary.toString());
                    BigDecimal monthlyAvc = BigDecimal.ZERO;
                    BigDecimal monthlyEE = BigDecimal.ZERO;
                    BigDecimal monthlyER = BigDecimal.ZERO;
                    if (getContributionRateAndSalary != null) {

                        try {
                            monthlyAvc = toBigDecimal(getContributionRateAndSalary.get("monthlyAvc"));
                            monthlyEE = toBigDecimal(getContributionRateAndSalary.get("monthlyEeContr"));
                            monthlyER = toBigDecimal(getContributionRateAndSalary.get("monthlyErContr"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    BigDecimal totalContribution = monthlyAvc.add(monthlyEE).add(monthlyER);
                    JSONObject object = jsonObject.getJSONArray("rows").getJSONObject(0);
                    projectedMonthlyPension = new BigDecimal(object.getString("projectedGrossMonthlyPension"));
//                BigDecimal projectedLumpsumPayable = new BigDecimal(object.getString("projectedGrossLumpsumPayable"));
                    projectedMonthlyContribution = new BigDecimal(object.getString("projectedMonthlyTotalBal"));

                    BigDecimal targetMonthlyContribution;
                    if (targetMonthlyPension.compareTo(projectedMonthlyPension) > 0) {
                        // base on formula
/*
                    (x*2/3) * (12500/1400000)= grossMonthlyPension
                    x being the lumpsum
                     reversed the formula is  = x= (grossMonthlypension* 336/2)/3
*/
                        targetLumpsumPayable = ((targetMonthlyPension.multiply(new BigDecimal(336))).divide(new BigDecimal(2), 2, RoundingMode.HALF_UP)).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);
                        targetMonthlyContribution = targetMonthlyPension.multiply(projectedMonthlyContribution).divide(projectedMonthlyPension, 2, RoundingMode.HALF_UP);

                        remainderAmount = targetMonthlyContribution.subtract(projectedMonthlyContribution);
                        percentageNeeded = remainderAmount.multiply(new BigDecimal(100)).divide(projectedMonthlyContribution, 2, RoundingMode.HALF_UP);
                        label = "To attain monthly pension target:";
                        JSONObject jsonObject1 = fmMemberClient.calculateTaxOnGrossLumpsumAndGrossPension(schemeId, memberId, ageAtExit, targetMonthlyPension.toString(), targetLumpsumPayable.toString());
                        if (jsonObject1 != null && jsonObject1.getBoolean(Fields.SUCCESS)) {
                            JSONObject returnValue = jsonObject1.getJSONArray("rows").getJSONObject(0);
                            object.put("taxOnProjectedMonthlyPension", returnValue.getString("taxOnProjectedMonthlyPension"));
                            object.put("projectedNetMonthlyPension", returnValue.getString("projectedNetMonthlyPension"));
                            object.put("projectedGrossMonthlyPension", returnValue.getString("projectedGrossMonthlyPension"));
                            object.put("projectedGrossLumpsumPayable", returnValue.getString("projectedGrossLumpsumPayable"));
                            object.put("projectedTaxOnLumpsum", returnValue.getString("projectedTaxOnLumpsum"));
                            object.put("projectedNetLumpusmPayable", returnValue.getString("projectedNetLumpusmPayable"));
                            //results.setProjectedNetMonthlyPension(new BigDecimal(returnValue.getString("projectedNetMonthlyPension")));
                        }
                    } else {
                        label = "With given financial assumption, you will surpass the target:";
                    }
                    object.put("currentMonthlyContribution", totalContribution);
                    object.put("percentageNeeded", percentageNeeded);
                    object.put("amountNeeded", remainderAmount);
                    object.put("targetLabel", label);

                    map.put("success", true);
                    map.put("data", object.toString());
                    return map;
                } else {
                    map.put("data", jsonObject.getString("message"));
                }
            } else {
                map.put("data", "Error encountered");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            map.put("data", "Error encountered");
        }
        map.put("success", false);
        return map;
    }

    public BigDecimal toBigDecimal(Object o) {
        try {
            String obj = String.valueOf(o);
            return new BigDecimal(obj.replaceAll(",", ""));
        } catch (NullPointerException | NumberFormatException npe) {
            return BigDecimal.ZERO;
        }
    }
}
