package com.systech.mss.config;

public class APICall {
    public static final String SPONSOR_SAVE_OR_UPDATE_POTENTIAL_SPONSOR = "sponsor/saveorupdatepotentialsponsor";
    public static final String SPONSOR_SAVE_OR_UPDATE_POTENTIAL_SPONSOR_ETL = "sponsor/saveorupdatepotentialsponsorETL";
    public static final String SPONSOR_SAVE_OR_UPDATE_EXISTING_POTENTIAL_SPONSOR_ETL = "sponsor/saveorupdateexistingpotentialsponsorETL";
    public static final String SAVE_OR_UPDATE_MEMBER = "saveorupdatemember";
    public static final String APPROVE_POTENTIAL_MEMBER = "approveOrDissaproveSponsorPotentialMember";
    public static final String SAVE_OR_UPDATE_ETL_MEMBER = "saveorupdatememberetl";
    public static final String UPDATE_ETL_POTENTIAL_MEMBER = "updatePotentialMember";
    public static final String SAVE_POTENTIAL_MEMBER = "saveorupdatepotentialmember";
    public static final String SAVE_BATCH_POTENTIAL_MEMBER = "saveorupdateBatchPotentialMember";
    public static final String CHECK_BILL_EXCEPTIONS = "checkSalaryException/";
    public static final String SAVE_BANK_DETAILS= "saveOrUpdateBankDetails";
    public static final String PORT_ETL_MEMBER = "portetlmember";
    public static final String UPDATE_MEMBER_PHONENUMBER = "batch" + "updatememberphonenumber/";
    public static final String UPLOAD_MEMBER_DOCUMENT = "uploadmemberdocument";
    public static final String UPLOAD_BENEFIT_REQUEST_DOCUMENT = "saveorupdatebenefitdocument";
    public static final String GET_PAYROLL_YEARS = "getpayrollyears/";
    public static final String CHECK_MEMBER_EXISTS = "checkMemberExists/";
    public static final String GET_BALANCES_HISTORY = "getbalanceshistory/";
    public static final String GET_PENSION_ADVICE = "getpensioneradvice/";
    public static final String GET_MEMBER_STATEMENT = "getmemberstatement/";
    public static final String GET_MEMBER_ANNUAL_CONTRIBUTION = "getannualcontributions/";
    public static final String SAVE_OR_UPDATE_BENEFICIARY_DETAILS = "saveorupdatebeneficiarydetails";
    public static final String CHECK_MEMBER_EXISTS_IN_SCHEME = "checkMemberExistsInScheme/";
    public static final String UPLOAD = "upload/";
    public static final String SAVE_MEMBER_ACCOUNT_BY_SCHEME_AND_MEMBERSHIP_NUMBER = "savememberaccountbyschemeandmembershipnumber/";
    public static final String GET_MEMBER_SCHEMES = "getmemberschemes/";
    public static final String GET_SPONSOR_DETAILS_BY_IDENTIFIER = "sponsor/getSponsorDetailsByIdentifier/";
    public static final String GET_SPONSOR_DETAILS_BY_IDENTIFIER_ETL = "getSponsorDetailsByIdentifier/";
    public static final String GET_MEMBER_PRODUCTS = "getmemberproducts/";
    public static final String GET_MEMBER_PRODUCTS_WITH_IDENTIFIER = "getmemberproductsWithIdentifier/";
    public static final String GET_MEMBER_SPONSORS = "getmembersponsors/";
    public static final String GET_SPONSOR_DETAILS = "getsponsordetails/";
    public static final String CHECK_IF_IDNUMBER_EXISTS = "checkIdnumberExits/";
    public static final String GET_MEMBER_AVERAGE_INTEREST = "getmemberaverageinterest/";
    public static final String SCHEME_GET_SCHEME_BASE_CURRENCY = "scheme/getschemebasecurrency/";
    public static final String SCHEME_MODE = "scheme/getschememode/";
    public static final String SCHEME_GET_FUND_VALUE_AS_AT = "scheme/getfundvalueasat/";
    public static final String SCHEME_GET_FUND_VALUE = "scheme/getfundvalue/";
    public static final String GET_AGENT_COMMISSIONS = "agent/getagentcommissions/";
    public static final String GET_AGENT_CLIENTS = "agent/getagentclients/";
    public static final String GET_ACCOUNTING_PERIOD_FROM_DATE_FOR_SCHEME = "getaccountingperiodfromdateforscheme/";
    public static final String SEND_MEMBER_STATEMENT = "sendMemberStatement/";
    public static final String GET_SCHEME_ACCOUNTING_PERIODS = "getaccountingperiods/";
    public static final String GET_SCHEME_SPONSORS = "sponsor/getsponsorbyscheme/";
    public static final String GET_MEMBER_CUMMULATIVE_INTEREST = "getmembercummulativeinterest/";
    //GET_MEMBER_CUMMULATIVE_BENEFIT
    public static final String GET_MEMBER_CUMMULATIVE_BENEFIT = "getmembercummulativebenefit/";

    public static final String PROFILE_GET_PROFILES = "profile/getprofiles";
    public static final String GETMEMBERCONTRIBUTIONS = "getmembercontributions/";
    public static final String GETMEMBERCONTRIBUTIONSETL = "getmembercontributionsETL/";
    public static final String GET_CONTRIBUTIONS_BTWN_DATES = "getcontributionsbetweendates/";
    public static final String GET_MEMBER_PROJECTIONS = "getProjectionsForMember/";
    public static final String GET_DB_PROJECTIONS = "benefits/getdbbenefits/";
    public static final String SCHEME_GETTOTALSCHEMECONTRIBUTIONS = "scheme/gettotalschemecontributions/";
    public static final String SPONSOR_GETTOTALSPONSORCONTRIBUTIONS = "sponsor/gettotalsponsorcontributions/";
    public static final String DELETE_BENEFICIARY = "deleteBeneficiary/";
    public static final String GET_MEMBER_BENEFICIARIES = "getmemberbeneficiaries/";
    public static final String GET__POTENTIAL_MEMBER_BENEFICIARIES = "getPotentialmemberbeneficiaries/";
    public static final String GET_MEMBER_TOTAL_ENTITLEMENT = "getmembertotalentitlement/";
    public static final String GET_SCHEME_SPONSOR_ID = "getschemesponsorid/";
    public static final String GET_SCHEME_ID_USING_PROD_NO = "getschemeidusingprodno/";
    public static final String GET_COMPANY_ID_USING_PROD_NO = "getcompanyidusingprodno/";
    public static final String GET_SPONSOR_ID_USING_PROD_NO = "getsponsoridusingprodno/";
    public static final String GET_AGENT_SPONSOR_ID = "getagentsponsorid/";
    public static final String GET_MEMBER_ID = "getmemberid/";
    public static final String GET_SPONSOR_DASHBOARD_MENU_STATISTICS = "getSponsorDashboardMenuStatistics/";
    public static final String GET_SPONSOR_POTENTIAL_MEMBERS= "getSponsorPotentialMembers/";
    public static final String GET_SPONSOR_TPFA= "getMemberTpfa/";

    public static final String NEW_MEMBER_LISTING_WITHIN_YEAR = "newMemberListingWithinYear/";
    public static final String SCHEME_GET_SCHEME_RECEIPTS_BETWEEN_DATES = "scheme/getschemereceiptsbetweendates/";
    public static final String SCHEME_GETS_CHEME_BENEFIT_PAYMENTS_BETWEEN_DATES = "scheme/getschemebenefitpaymentsbetweendates/";
    public static final String GET_MEMBERS_DUE_FOR_RETIREMENT = "getmembersdueforretirement/";
    public static final String GET_MEMBERS_DUE_FOR_RETIREMENT_PER_SPONSOR = "getmembersdueforretirementpersponsor/";
    public static final String SCHEME_GET_SCHEME_BY_NAME = "scheme/getschemebyname/";
    public static final String SCHEME_GET_SCHEME_BY_ID = "scheme/getscheme/";
    public static final String NOTIFICATION_PUSH = "notification/push";
    public static final String COMMISSIONS_GET = "commissions/get/";
    public static final String SCHEME_GET_SCHEME_BENEFITS_WITHIN_YEAR = "scheme/getschemebenefitswithinyear/";
    public static final String SCHEME_GET_SCHEME_BENEFITS_WITHIN_YEAR_PER_SPONSOR = "scheme/getschemebenefitswithinyearpersponsor/";
    public static final String SCHEME_GET_REASONS_FOR_EXIT = "scheme/getreasonsforexit/";
    public static final String SEARCH_FOR_MEMBER_DETAILS = "searchForMemberDetails/";
    public static final String SEARCH_FOR_SPONSOR_MEMBER_DETAILS = "searchForSponsorMemberDetails/";
    public static final String GET_MEMBER_DETAILS = "getmemberdetails/";
    public static final String GET_POTENTIAL_MEMBER_DETAILS = "getpotentialmemberdetails/";
    public static final String GET_UNAPPROVED_MEMBER_DETAILS = "getunapprovedmemberdetails/";
    public static final String GET_UNAPPROVED_BEN_DETAILS = "getunapprovedbeneficiarydetails/";
    public static final String GET_MEMBER_DETAILS_BY_SCHEME_AND_EMAIL = "getmemberdetailsbyschemeandemail/";
    public static final String GET_MEMBER_DETAILS_BY_SPONSOR_AND_EMAIL = "getmemberdetailsbysponsorandemail/";
    public static final String GET_MEMBER_DETAILS_BY_SCHEME_AND_PHONE = "getmemberdetailsbyschemeandphone/";
    public static final String GET_MEMBER_DETAILS_BY_SCHEME_AND_SPONSOR_AND_PHONE = "getmemberdetailsbyschemeandsponsorandphone/";
    public static final String GET_MEMBER_DETAILS_BY_SPONSOR_AND_PHONE = "getmemberdetailsbysponsorandphone/";
    public static final String GET_PENSIONER_DETAILS = "getpensionerdetails/";
    public static final String GET_LAST_PAYROLL_PER_PENSIONER = "getLastPayrollByPensioner/";
    public static final String GET_PENSIONER_DEDUCTIONS = "getPensionerDeductions/";
    public static final String GET_MEMBER_ID_FROM_MAIL = "getmemberIdfromMail/";
    public static final String GET_MEMBER_LISTING = "getmemberlisting/";
    public static final String GET_POTENTIAL_MEMBERS_PER_SPONSOR = "getSponsorPotentialMembers/";
    public static final String GET_POTENTIAL_MEMBERS_DETAILS = "getPotentialMemberDetails/";
    public static final String GET_POTENTIAL_MEMBERS_BENEFICIARY_DETAILS = "getPotentialMemberBeneficiaries/";
    public static final String GET_UNAPPROVED_MEMBERS_INFO = "getUnapprovedMemberInformation/";
    public static final String GET_UNAPPROVED_BENEFICIARY_INFO = "getUnapprovedBeneficiaryInformation/";
    public static final String MEMBER_STATISTICS_STATUS_DISTRIBUTION = "member/statistics/statusdistribution/";
    public static final String MEMBER_STATISTICS_STATUS_DISTRIBUTION_TPFA = "getSponsorDashboardMenuStatistics/";
    public static final String ADMIN_DASHBOARD_STATISTICS = "getAdminDashboardMenuStatistics/";
    public static final String SCHEME_GET_SCHEME_RECEIPTS = "scheme/getschemereceipts/";
    public static final String SCHEME_GET_SPONSOR_RECEIPTS = "scheme/getsponsorschemereceipts/";
    public static final String GET_REPORT_DETAILS = "getreportdetails/";
    public static final String SCHEME_GET_SCHEME_BENEFIT_PAYMENTS = "scheme/getschemebenefitpayments/";
    public static final String MEMBER_GET_MEMBER_BENEFIT_PAYMENTS = "getmemberbenefitpayments/";
    public static final String SCHEME_GET_SCHEME_BENEFIT_PAYMENTS_PER_SPONSOR = "scheme/getschemebenefitpaymentspersponsor/";
    public static final String GET_DC_MEMBER_BALANCES = "getmemberbalances/";
    public static final String GET_DB_MEMBER_BALANCES = "getmemberaccruedpension/";
    public static final String SCHEME_GET_SCHEME_INTEREST_RATES = "scheme/getschemeinterestrates/";
    public static final String SCHEME_GET_SCHEME_UNIT_PRICES = "scheme/getschemeunitprices/";
    public static final String GET_CURRENT_UNIT_PRICE = "scheme/getcurrentunitprice/";
    public static final String GET_MEMBER_LATEST_CONTRIBUTION = "getmemberlatestcontribution/";
    //GET_MEMBER_TOTAL_UNITS
    public static final String GET_MEMBER_TOTAL_UNITS = "getmembertotalunits/";

    public static final String GET_MEMBER_CLOSING_BALANCES = "getClosingBalances/";
    public static final String GET_MEMBER_CONTRIBUTION_TOTALS = "getMemberContributionTotals/";
    public static final String CHECK_MEMBER_EXISTS_1 = "checkMemberExists1/"; //returns memberId unlike checkMemberExists/
    public static final String GET_MEMBER_LOANS = "getMemberLoans/";
    public static final String GET_MEMBER_VESTINGS = "getMemberVestings/";


    //GET_CURRENT_UNIT_PRICE
    public static final String GET_SPONSOR_INTEREST_RATES = "sponsor/getsponsorinterestrates/";
    public static final String WHAT_IF_ANALYSIS = "whatifanalysis/";
    public static final String SCHEME_GET_SCHEME_BY_SCHEME_MODE_AND_PLAN_TYPE = "scheme/getschemebyschememodeandplantype/";
    public static final String SCHEME_GET_SCHEME_BY_SCHEME_MODE = "scheme/getschemebyschememode/";
    public static final String ANNUITY_QUOTE_GET_ANNUITY_PRODUCTS = "annuity_quote/getannuityproducts";
    public static final String SCHEME_GET_SCHEME_BY_PLAN_TYPE = "scheme/getschemebyplantype/";
    public static final String ANNUITY_QUOTE_GETQUOTE = "annuity_quote/getquote";
    public static final String SCHEME_GETSCHEMES = "scheme/getschemes";
    public static final String GET_EXITS_BENEFITS = "getexitbenefits/";
    public static final String SAVESMS = "savesms/";
    public static final String SENDSMS = "sendSMS/";
    public static final String MSS_ACCOUNT_OPERATION = "mssAccountOperation/saveOrUpdate/";

    //unitized statement apis
    public static final String GET_TRANSACTION_HISTORY = "transactionHistory/";
    public static final String GET_CORPORATE_TRANSACTION_HISTORY = "corporatetransactionhistory/";
    public static final String GET_CONTRIBUTION_SUMMARY = "contributionsummary/";
    public static final String GET_TOTAL_BENEFITS_PAID = "totalbenefitspaid/";
    public static final String GET_MEMBER_DETS = "memberdetails/";
    public static final String GET_COMPANY_DETAILSS = "sponsordetails/";
    public static final String GET_HEADER_DETS = "headerdetails/";
    public static final String GET_SCHEME_HEADER_DETAILS = "schemeheaderdetails/";
    public static final String GET_HEADER_DETSWITHSCHEMEID = "headerdetailsWithSchemeId/";

    //memberlistings
    public static final String GET_MEMBER_LISTINGS = "getMemberListings/";
    public static final String MEMBER_CERTIFICATE = "getMemberCertificate/";
    public static final String SAVE_OR_UPDATE_EPP = "epp/saveorupdateEPP";
    public static final String SAVE_OR_UPDATE_ETPPEC = "etppec/saveorupdateEtppec";

    ///
    public static final String GET_CONTRIBUTION_BILLS_PER_SPONSOR_OLD = "getContributionBillsPerSponsor/";
    public static final String GET_CONTRIBUTION_BILLS_PER_SPONSOR = "getUpdatedContributionBillsPerSponsor/";
    public static final String FILTER_CONTRIBUTION_BILLS_PER_SPONSOR = "filterUpdatedContributionBillsPerSponsor/";
    public static final String SEARCH_CONTRIBUTION_BILLS_PER_SPONSOR = "searchContributionBillsPerSponsor/";
    public static final String BOOK_CONTRIBTION_BILLS = "generateContributionBills/";
    public static final String BOOK_CONTRIBUTION_BILLS_OLD = "bookContributionBills/";
    public static final String GET_MEMBER_BY_SCHEME_MEMBERSHIP = "getMemberBySchemeAndMembershipIdTo/";
    public static final String FINDCONTRIBUTIONRATEBYMEMBERCLASSID = "findContributionRateByMemberClassId/";
    public static final String GETEXPECTEDCONTROLAMOUNT = "getExpectedContrAmount/";
    public static final String SAVE_BILL_EXCEPTION = "saveContributionBillsValidation/";
    public static final String SAVE_BILL_EXCEPTION_ETL = "saveBillException";
    public static final String CANCEL_BILL = "cancelContributionBills";
    public static final String PRESERVE_ACCOUNT = "preserveAccount/";
    public static final String PRINT_BULK_MEMBER_STATEMENT = "printBulkMemberStatement/";
    public static final String GET_MEMBER_TPFA = "getMemberTpfa/";


    public static final String GET_MEMBER_USSD_BENEFIT_PAYMENT_REQUEST = "getMemberUssdBenefitPaymentRequest/";
    public static final String GET_SPONSOR_USSD_BENEFIT_PAYMENT_REQUEST = "getSponsorUssdBenefitPaymentRequest/";
    public static final String UPDATE_USSD_BENEFIT_PAYMENT_STATUS = "updateUssdBenefitPaymentStatus/";
    public static final String UPDATE_BANK_DEFAULT_POINT = "updateBankDefaultPoint/";
    public static final String APPROVE_OR_DISSAPROVE_POTENTIAL_MEMBER = "approveOrDissaproveSponsorPotentialMember/";
    public static final String APPROVE_OR_DISSAPROVE_MEMBER_DETAILS = "approveOrDissaproveMemberDetails/";
    public static final String APPROVE_OR_DISSAPROVE_BENEFICIARY_DETAILS = "approveOrDissaproveBeneficiaryDetails/";
    public static final String UPDATE_POTENTIAL_MEMBER = "updatePotentialMember/";
    public static final String GET_ALL_BANKS = "scheme/getAllBanks/";
    public static final String GET_BANK_BRANCHES_PER_BANK = "scheme/getBankBranchesPerBank/";
    public static final String GET_BANK_NAME_PER_BANK = "scheme/getBankNamePerBank/";
    public static final String GET_REASON_FOR_EXIT_BY_ID = "scheme/getReasonForExitById/";
    public static final String SPONSOR_RESIGNING_MEMBER = "sponsorResignMember/";
    public static final String VALIDATE_PAYMENT_CODE = "validateContributionPaymentCode/";

    public static final String GET_ALL_DISTRICTS = "getAllDistricts/";
    public static final String GET_TRADITIONAL_AUTHOROTIES = "getTraditionalAuthorities/";
    public static final String GET_VILLAGES = "getVillages/";


    public static final String GET_RETIREMENT_AGES = "getRetirementAges/";
    public static final String GET_VESTING_SCALES = "getVestingScales/";
    public static final String GET_MEMBER_ACCOUNT_DETAILS = "getMemberAccountDetails/";
    public static final String GET_CONTRIBUTION_RATE_SALARY = "getCurrentMonthlyContributionAndBasicSalary/";
    public static final String GET_BALANCES_AS_AT_TODAY = "getBalancesToday/";
    public static final String GET_BALANCES_TODAY = "getBalancesAsAtToday/"; //getBalancesAsAtToday

    public static final String CALCULATE_WHATIF_ANALYSIS = "calculateWhatIfAnalysis/";
    public static final String MAKE_CONTRIBUTION_PAYMENT_VIA_PORTAL = "initiate-stk-push";
    public static final String POST_CONTRIBUTION_TO_MEMBERAC = "postContributionFromMssSTKPushToAccount";
    public static final String CONFIRM_CONTRIBUTION_PAYMENT_VIA_PORTAL = "get-stk-response";
    public static final String CALCULATE_TAX = "calculateTaxOnGrossLumpsumAndGrossPension/";

    public static final String GET_MISSING_DOCUMENTS = "getmissingdocuments/";
    public static final String GET_SUBMITTED_DOCUMENTS = "getsubmitteddocuments/";

    public static final String EMAIL_STATEMENT = "emailMemberStatement/";
    public static final String SEND_NOTIFICATION_TO_RLTSHIP_MANAGER = "sendNotificationToRelationshipManager";


    public static final String GET_ADMIN_DETAILS = "getadmindetails";
    public static final String GET_USER_DETAILS = "getuserdetails";
    public static final String GET_SESSION_DETAILS = "getsessiondetails";
    public static final String CHECK_IF_MEMBER_IS_DUE_FOR_KYC_UPDATE = "checkIfMemberIsDueForKycUpdate/";
    public static final String UPDATE_KYC_MEMBER = "updateMemberKyc/";
    public static final String CHECK_IF_MEMBER_HAS_GENERIC_DOB = "checkIfMemberHasGenericDoB/";

    public static final String SAVE_OR_UPDATE_BENEFIT = "saveorupdatebenefit";
    public static final String GET_SPONSOR_BY_CRM_ID = "sponsor/getSponsorsByCrm/";
    public static final String GET_SPONSOR_MEMBER_SCHEMES = "/sponsor/getSchemesBySponsorId/";
    public static final String GET_USER_BY_PROFILE_NAME = "user/getUsersByProfileName/";
    public static final String GET_USER_BY_FMId = "users/getUserDetails/";
    public static final String GET_SPONSOR_CONTRIBUTIONS_RECEIPT = "sponsor/getSponsorContributionReceipts/";
    public static final String GET_SPONSOR_CONTRIBUTIONS_RECEIPT_ETL = "getSponsorReceipts/";
    public static final String GET_MEMBER_CUMULATIVE_STATEMENT = "getcumulativestatement/";
    public static final String GET_CONFIGS = "getconfigs/";
    public static final String GET_PENSIONER_COE = "getPensionerCOE/";
    public static final String GET_MEMBERCLASS_INFO = "ccmc/memberclass/";
    public static final String GET_COMPANY_INFO = "ccmc/costcenter/";
    public static final String GET_SPONSOR_MEMBER_CLASSES = "ccmc/memberclassess";
    public static final String GET_SPONSOR_COST_CENTRES = "ccmc/costcenters/";
    public static final String GET_SPONSOR_MEMBER_AND_PENSIONERS_COUNT = "sponsor/getSponsorMemberAndPensionerCount/";
    public static final String GET_MEMBER_BANK_DETAILS = "getMemberBankDetails/";
    public static final String GET_EMPLOYER_AND_MEMBER_BENEFITS_FM = "getMemberAndEmployerBenefitPayments/";
    public static final String PRINT_UNITIZED_STATEMENT = "printBulkMemberStatement/";

    public static final String GET_ALL_SCHEME = "scheme/getschemes/";
    public static final String GET_SCHEME_BY_NAME = "scheme/getschemebyname/";
    public static final String GET_SPONSORS_BY_SCHEME_ID = "getSchemeSponsors/";


    public static final String GET_PRINCIPAL_OFFICER_DETAILS = "getPrincipalOfficerDetails/";
    public static final String GET_PRINCIPAL_OFFICER_SCHEMES = "getPrincipalOfficerAssignedSchemes/";
    public static final String GET_PRINCIPAL_OFFICER_EMPLOYERS = "getPrincipalOfficerAssignedEmployers/";
    public static final String GET_PRINCIPAL_OFFICER_COST_CENTERS = "getPrincipalOfficerAssignedCostCenters/";
    public static final String GET_PAID_CLAIMS = "getPaidClaims/";
    public static final String GET_ALL_CLAIMS = "getAllClaims/";
    public static final String GET_DOCUMENTS_REQUIRED_PER_CLAIM = "getDocumentsRequiredPerClaim/";

    public static final String PROCESS_SELFIE = "processSelfie";
    public static final String CHECK_IF_SELFIE_IS_REGISTERED = "checkIfSelfieIsRegistered/";
    public static final String CHECK_IF_PENSIONER_COE_IS_SUBMITTED = "checkPensionerCoeIsSubmitted/";
    public static final String LOAD_USER_IMAGES = "loadUserImages/";
    public static final String LOAD_USER_REGISTRATION_IMAGE = "loadUserRegistrationImage/";
    public static final String LOAD_SELFIE_REGISTRATION_DETAILS = "loadSelfieRegistrationDetails/";

    public static final String GET_SCHEME_BY_ID = "scheme/getscheme/";
    public static final String CALCULATEPROVISIONALBALANCE = "calculateProvisionalBalance/";
}