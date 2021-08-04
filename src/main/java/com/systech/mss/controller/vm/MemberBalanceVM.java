package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.util.Ignore;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@ToString
public class MemberBalanceVM implements Serializable {
    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    @JsonProperty("memberno")
    public String getMemberno() {
        return this.memberno;
    }

    public void setMemberno(String memberno) {
        this.memberno = memberno;
    }

    String memberno;

    @JsonProperty("memberName")
    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    String memberName;

    @JsonProperty("employeebal")
    public String getEmployeebal() {
        return this.employeebal;
    }

    public void setEmployeebal(String employeebal) {
        this.employeebal = employeebal;
    }

    String employeebal;

    @JsonProperty("employerbal")
    public String getEmployerbal() {
        return this.employerbal;
    }

    public void setEmployerbal(String employerbal) {
        this.employerbal = employerbal;
    }

    String employerbal;

    @JsonProperty("savings")
    public String getSavings() {
        return this.savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    String savings;

    @JsonProperty("avcbal")
    public String getAvcbal() {
        return this.avcbal;
    }

    public void setAvcbal(String avcbal) {
        this.avcbal = avcbal;
    }

    String avcbal;

    @JsonProperty("avcerbal")
    public String getAvcerbal() {
        return this.avcerbal;
    }

    public void setAvcerbal(String avcerbal) {
        this.avcerbal = avcerbal;
    }

    String avcerbal;

    @JsonProperty("eeIntr")
    public String getEeIntr() {
        return this.eeIntr;
    }

    public void setEeIntr(String eeIntr) {
        this.eeIntr = eeIntr;
    }

    String eeIntr;

    @JsonProperty("erIntr")
    public String getErIntr() {
        return this.erIntr;
    }

    public void setErIntr(String erIntr) {
        this.erIntr = erIntr;
    }

    String erIntr;

    @JsonProperty("avcIntr")
    public String getAvcIntr() {
        return this.avcIntr;
    }

    public void setAvcIntr(String avcIntr) {
        this.avcIntr = avcIntr;
    }

    String avcIntr;

    @JsonProperty("avcerIntr")
    public String getAvcerIntr() {
        return this.avcerIntr;
    }

    public void setAvcerIntr(String avcerIntr) {
        this.avcerIntr = avcerIntr;
    }

    String avcerIntr;

    @JsonProperty("prevEEIntr")
    public String getPrevEEIntr() {
        return this.prevEEIntr;
    }

    public void setPrevEEIntr(String prevEEIntr) {
        this.prevEEIntr = prevEEIntr;
    }

    String prevEEIntr;

    @JsonProperty("prevERIntr")
    public String getPrevERIntr() {
        return this.prevERIntr;
    }

    public void setPrevERIntr(String prevERIntr) {
        this.prevERIntr = prevERIntr;
    }

    String prevERIntr;

    @JsonProperty("totalOpeningBalances")
    public String getTotalOpeningBalances() {
        return this.totalOpeningBalances;
    }

    public void setTotalOpeningBalances(String totalOpeningBalances) {
        this.totalOpeningBalances = totalOpeningBalances;
    }

    String totalOpeningBalances;

    @JsonProperty("prevAVCIntr")
    public String getPrevAVCIntr() {
        return this.prevAVCIntr;
    }

    public void setPrevAVCIntr(String prevAVCIntr) {
        this.prevAVCIntr = prevAVCIntr;
    }

    String prevAVCIntr;

    @JsonProperty("prevAVCERIntr")
    public String getPrevAVCERIntr() {
        return this.prevAVCERIntr;
    }

    public void setPrevAVCERIntr(String prevAVCERIntr) {
        this.prevAVCERIntr = prevAVCERIntr;
    }

    String prevAVCERIntr;

    @JsonProperty("eeContr")
    public String getEeContr() {
        return this.eeContr;
    }

    public void setEeContr(String eeContr) {
        this.eeContr = eeContr;
    }

    String eeContr;

    @JsonProperty("erContr")
    public String getErContr() {
        return this.erContr;
    }

    public void setErContr(String erContr) {
        this.erContr = erContr;
    }

    String erContr;

    @JsonProperty("avcContr")
    public String getAvcContr() {
        return this.avcContr;
    }

    public void setAvcContr(String avcContr) {
        this.avcContr = avcContr;
    }

    String avcContr;

    @JsonProperty("avcerContr")
    public String getAvcerContr() {
        return this.avcerContr;
    }

    public void setAvcerContr(String avcerContr) {
        this.avcerContr = avcerContr;
    }

    String avcerContr;

    @JsonProperty("totalInterestOnContribution")
    public String getTotalInterestOnContribution() {
        return this.totalInterestOnContribution;
    }

    public void setTotalInterestOnContribution(String totalInterestOnContribution) {
        this.totalInterestOnContribution = totalInterestOnContribution;
    }

    String totalInterestOnContribution;

    @JsonProperty("totalInterestOnOpeningBalances")
    public String getTotalInterestOnOpeningBalances() {
        return this.totalInterestOnOpeningBalances;
    }

    public void setTotalInterestOnOpeningBalances(String totalInterestOnOpeningBalances) {
        this.totalInterestOnOpeningBalances = totalInterestOnOpeningBalances;
    }

    String totalInterestOnOpeningBalances;

    @JsonProperty("totalInterest")
    public String getTotalInterest() {
        return this.totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    String totalInterest;

    @JsonProperty("preemployeebal")
    public String getPreemployeebal() {
        return this.preemployeebal;
    }

    public void setPreemployeebal(String preemployeebal) {
        this.preemployeebal = preemployeebal;
    }

    String preemployeebal;

    @JsonProperty("preemployerbal")
    public String getPreemployerbal() {
        return this.preemployerbal;
    }

    public void setPreemployerbal(String preemployerbal) {
        this.preemployerbal = preemployerbal;
    }

    String preemployerbal;

    @JsonProperty("preavc")
    public String getPreavc() {
        return this.preavc;
    }

    public void setPreavc(String preavc) {
        this.preavc = preavc;
    }

    String preavc;

    @JsonProperty("deficitbal")
    public String getDeficitbal() {
        return this.deficitbal;
    }

    public void setDeficitbal(String deficitbal) {
        this.deficitbal = deficitbal;
    }

    String deficitbal;

    @JsonProperty("transfer")
    public String getTransfer() {
        return this.transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    String transfer;

    @JsonProperty("lockedin")
    public String getLockedin() {
        return this.lockedin;
    }

    public void setLockedin(String lockedin) {
        this.lockedin = lockedin;
    }

    String lockedin;

    @JsonProperty("unlockdate")
    public String getUnlockdate() {
        return this.unlockdate;
    }

    public void setUnlockdate(String unlockdate) {
        this.unlockdate = unlockdate;
    }

    String unlockdate;

    @JsonProperty("totalEE")
    public String getTotalEE() {
        return this.totalEE;
    }

    public void setTotalEE(String totalEE) {
        this.totalEE = totalEE;
    }

    String totalEE;

    @JsonProperty("totalER")
    public String getTotalER() {
        return this.totalER;
    }

    public void setTotalER(String totalER) {
        this.totalER = totalER;
    }

    String totalER;

    @JsonProperty("tax")
    public String getTax() {
        return this.tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    String tax;

    @JsonProperty("eeBalOnly")
    public String getEeBalOnly() {
        return this.eeBalOnly;
    }

    public void setEeBalOnly(String eeBalOnly) {
        this.eeBalOnly = eeBalOnly;
    }

    String eeBalOnly;

    @JsonProperty("erBalOnly")
    public String getErBalOnly() {
        return this.erBalOnly;
    }

    public void setErBalOnly(String erBalOnly) {
        this.erBalOnly = erBalOnly;
    }

    String erBalOnly;

    @JsonProperty("avcBalOnly")
    public String getAvcBalOnly() {
        return this.avcBalOnly;
    }

    public void setAvcBalOnly(String avcBalOnly) {
        this.avcBalOnly = avcBalOnly;
    }

    String avcBalOnly;

    @JsonProperty("eeTransferBalOnly")
    public String getEeTransferBalOnly() {
        return this.eeTransferBalOnly;
    }

    public void setEeTransferBalOnly(String eeTransferBalOnly) {
        this.eeTransferBalOnly = eeTransferBalOnly;
    }

    String eeTransferBalOnly;

    @JsonProperty("erTransferBalOnly")
    public String getErTransferBalOnly() {
        return this.erTransferBalOnly;
    }

    public void setErTransferBalOnly(String erTransferBalOnly) {
        this.erTransferBalOnly = erTransferBalOnly;
    }

    String erTransferBalOnly;

    @JsonProperty("avcTransferBalOnly")
    public String getAvcTransferBalOnly() {
        return this.avcTransferBalOnly;
    }

    public void setAvcTransferBalOnly(String avcTransferBalOnly) {
        this.avcTransferBalOnly = avcTransferBalOnly;
    }

    String avcTransferBalOnly;

    @JsonProperty("avcerTransferBalOnly")
    public String getAvcerTransferBalOnly() {
        return this.avcerTransferBalOnly;
    }

    public void setAvcerTransferBalOnly(String avcerTransferBalOnly) {
        this.avcerTransferBalOnly = avcerTransferBalOnly;
    }

    String avcerTransferBalOnly;

    @JsonProperty("eePayments")
    public String getEePayments() {
        return this.eePayments;
    }

    public void setEePayments(String eePayments) {
        this.eePayments = eePayments;
    }

    String eePayments;

    @JsonProperty("erPayments")
    public String getErPayments() {
        return this.erPayments;
    }

    public void setErPayments(String erPayments) {
        this.erPayments = erPayments;
    }

    String erPayments;

    @JsonProperty("avcPayments")
    public String getAvcPayments() {
        return this.avcPayments;
    }

    public void setAvcPayments(String avcPayments) {
        this.avcPayments = avcPayments;
    }

    String avcPayments;

    @JsonProperty("avcerPayments")
    public String getAvcerPayments() {
        return this.avcerPayments;
    }

    public void setAvcerPayments(String avcerPayments) {
        this.avcerPayments = avcerPayments;
    }

    String avcerPayments;

    @JsonProperty("paymentsTotal")
    public String getPaymentsTotal() {
        return this.paymentsTotal;
    }

    public void setPaymentsTotal(String paymentsTotal) {
        this.paymentsTotal = paymentsTotal;
    }

    String paymentsTotal;

    @JsonProperty("eePaymentsIntr")
    public String getEePaymentsIntr() {
        return this.eePaymentsIntr;
    }

    public void setEePaymentsIntr(String eePaymentsIntr) {
        this.eePaymentsIntr = eePaymentsIntr;
    }

    String eePaymentsIntr;

    @JsonProperty("erPaymentsIntr")
    public String getErPaymentsIntr() {
        return this.erPaymentsIntr;
    }

    public void setErPaymentsIntr(String erPaymentsIntr) {
        this.erPaymentsIntr = erPaymentsIntr;
    }

    String erPaymentsIntr;

    @JsonProperty("paymentsIntrTotal")
    public String getPaymentsIntrTotal() {
        return this.paymentsIntrTotal;
    }

    public void setPaymentsIntrTotal(String paymentsIntrTotal) {
        this.paymentsIntrTotal = paymentsIntrTotal;
    }

    String paymentsIntrTotal;

    @JsonProperty("transferEeBal")
    public String getTransferEeBal() {
        return this.transferEeBal;
    }

    public void setTransferEeBal(String transferEeBal) {
        this.transferEeBal = transferEeBal;
    }

    String transferEeBal;

    @JsonProperty("transferErBal")
    public String getTransferErBal() {
        return this.transferErBal;
    }

    public void setTransferErBal(String transferErBal) {
        this.transferErBal = transferErBal;
    }

    String transferErBal;

    @JsonProperty("transferAvcBal")
    public String getTransferAvcBal() {
        return this.transferAvcBal;
    }

    public void setTransferAvcBal(String transferAvcBal) {
        this.transferAvcBal = transferAvcBal;
    }

    String transferAvcBal;

    @JsonProperty("transferAvcerBal")
    public String getTransferAvcerBal() {
        return this.transferAvcerBal;
    }

    public void setTransferAvcerBal(String transferAvcerBal) {
        this.transferAvcerBal = transferAvcerBal;
    }

    String transferAvcerBal;

    @JsonProperty("transferBalTotal")
    public String getTransferBalTotal() {
        return this.transferBalTotal;
    }

    public void setTransferBalTotal(String transferBalTotal) {
        this.transferBalTotal = transferBalTotal;
    }

    String transferBalTotal;

    @JsonProperty("transferEeBalIntr")
    public String getTransferEeBalIntr() {
        return this.transferEeBalIntr;
    }

    public void setTransferEeBalIntr(String transferEeBalIntr) {
        this.transferEeBalIntr = transferEeBalIntr;
    }

    String transferEeBalIntr;

    @JsonProperty("transferErBalIntr")
    public String getTransferErBalIntr() {
        return this.transferErBalIntr;
    }

    public void setTransferErBalIntr(String transferErBalIntr) {
        this.transferErBalIntr = transferErBalIntr;
    }

    String transferErBalIntr;

    @JsonProperty("transferAvcBalIntr")
    public String getTransferAvcBalIntr() {
        return this.transferAvcBalIntr;
    }

    public void setTransferAvcBalIntr(String transferAvcBalIntr) {
        this.transferAvcBalIntr = transferAvcBalIntr;
    }

    String transferAvcBalIntr;

    @JsonProperty("transferAvcerBalIntr")
    public String getTransferAvcerBalIntr() {
        return this.transferAvcerBalIntr;
    }

    public void setTransferAvcerBalIntr(String transferAvcerBalIntr) {
        this.transferAvcerBalIntr = transferAvcerBalIntr;
    }

    String transferAvcerBalIntr;

    @JsonProperty("transferBalIntrTotal")
    public String getTransferBalIntrTotal() {
        return this.transferBalIntrTotal;
    }

    public void setTransferBalIntrTotal(String transferBalIntrTotal) {
        this.transferBalIntrTotal = transferBalIntrTotal;
    }

    String transferBalIntrTotal;

    @JsonProperty("transferEe")
    public String getTransferEe() {
        return this.transferEe;
    }

    public void setTransferEe(String transferEe) {
        this.transferEe = transferEe;
    }

    String transferEe;

    @JsonProperty("transferEr")
    public String getTransferEr() {
        return this.transferEr;
    }

    public void setTransferEr(String transferEr) {
        this.transferEr = transferEr;
    }

    String transferEr;

    @JsonProperty("transferAvc")
    public String getTransferAvc() {
        return this.transferAvc;
    }

    public void setTransferAvc(String transferAvc) {
        this.transferAvc = transferAvc;
    }

    String transferAvc;

    @JsonProperty("transferAvcer")
    public String getTransferAvcer() {
        return this.transferAvcer;
    }

    public void setTransferAvcer(String transferAvcer) {
        this.transferAvcer = transferAvcer;
    }

    String transferAvcer;

    @JsonProperty("transferContrTotal")
    public String getTransferContrTotal() {
        return this.transferContrTotal;
    }

    public void setTransferContrTotal(String transferContrTotal) {
        this.transferContrTotal = transferContrTotal;
    }

    String transferContrTotal;

    @JsonProperty("transferEeIntr")
    public String getTransferEeIntr() {
        return this.transferEeIntr;
    }

    public void setTransferEeIntr(String transferEeIntr) {
        this.transferEeIntr = transferEeIntr;
    }

    String transferEeIntr;

    @JsonProperty("transferErIntr")
    public String getTransferErIntr() {
        return this.transferErIntr;
    }

    public void setTransferErIntr(String transferErIntr) {
        this.transferErIntr = transferErIntr;
    }

    String transferErIntr;

    @JsonProperty("transferAvcIntr")
    public String getTransferAvcIntr() {
        return this.transferAvcIntr;
    }

    public void setTransferAvcIntr(String transferAvcIntr) {
        this.transferAvcIntr = transferAvcIntr;
    }

    String transferAvcIntr;

    @JsonProperty("transferAvcerIntr")
    public String getTransferAvcerIntr() {
        return this.transferAvcerIntr;
    }

    public void setTransferAvcerIntr(String transferAvcerIntr) {
        this.transferAvcerIntr = transferAvcerIntr;
    }

    String transferAvcerIntr;

    @JsonProperty("transferContrIntrTotal")
    public String getTransferContrIntrTotal() {
        return this.transferContrIntrTotal;
    }

    public void setTransferContrIntrTotal(String transferContrIntrTotal) {
        this.transferContrIntrTotal = transferContrIntrTotal;
    }

    String transferContrIntrTotal;

    @JsonProperty("total")
    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String total;

    @JsonProperty("nssf_bal")
    public String getNssf_bal() {
        return this.nssf_bal;
    }

    public void setNssf_bal(String nssf_bal) {
        this.nssf_bal = nssf_bal;
    }

    String nssf_bal;

    @JsonProperty("avcerBalOnly")
    public String getAvcerBalOnly() {
        return this.avcerBalOnly;
    }

    public void setAvcerBalOnly(String avcerBalOnly) {
        this.avcerBalOnly = avcerBalOnly;
    }

    String avcerBalOnly;

    @JsonProperty("accntprd")
    public String getAccntprd() {
        return this.accntprd;
    }

    public void setAccntprd(String accntprd) {
        this.accntprd = accntprd;
    }

    String accntprd;

    @JsonProperty("apId")
    public String getApId() {
        return this.apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    String apId;

    @JsonProperty("memberId")
    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    String memberId;

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    @JsonProperty("date")
    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    @JsonProperty("asAt")
    public String getAsAt() {
        return this.asAt;
    }

    public void setAsAt(String asAt) {
        this.asAt = asAt;
    }

    String asAt;

    @JsonProperty("rate")
    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    String rate;

    @JsonProperty("units")
    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    String units;

    @JsonProperty("unitprice")
    public String getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    String unitprice;

    @JsonProperty("reserveIncome")
    public String getReserveIncome() {
        return this.reserveIncome;
    }

    public void setReserveIncome(String reserveIncome) {
        this.reserveIncome = reserveIncome;
    }

    String reserveIncome;

    @JsonProperty("premiums")
    public String getPremiums() {
        return this.premiums;
    }

    public void setPremiums(String premiums) {
        this.premiums = premiums;
    }

    String premiums;

    @JsonProperty("user")
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String user;

    @JsonProperty("exception")
    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    String exception;

    @JsonProperty("totalEeBal")
    public String getTotalEeBal() {
        return this.totalEeBal;
    }

    public void setTotalEeBal(String totalEeBal) {
        this.totalEeBal = totalEeBal;
    }

    String totalEeBal;

    @JsonProperty("totalErBal")
    public String getTotalErBal() {
        return this.totalErBal;
    }

    public void setTotalErBal(String totalErBal) {
        this.totalErBal = totalErBal;
    }

    String totalErBal;

    @JsonProperty("shareOfReserve")
    public String getShareOfReserve() {
        return this.shareOfReserve;
    }

    public void setShareOfReserve(String shareOfReserve) {
        this.shareOfReserve = shareOfReserve;
    }

    String shareOfReserve;

    @JsonProperty("deffered")
    public String getDeffered() {
        return this.deffered;
    }

    public void setDeffered(String deffered) {
        this.deffered = deffered;
    }

    String deffered;

    @Ignore
    String yearEnding;

    public String getYearEnding() {
        return yearEnding;
    }

    public void setYearEnding(String yearEnding) {
        this.yearEnding = yearEnding;
    }
}

