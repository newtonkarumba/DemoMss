package com.systech.mss.config;

public final class Constants {

    public final static String BEARER_PREFIX = "Bearer ";

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    
    //Regex for acceptable emails
    public static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    
    public static final String EMAIL_REGEX_MESSAGE = "{invalid.email}";

    public static final String INVALID_PASSWORD_TYPE = "Invalid password";
    
    public static final String EMAIL_ALREADY_USED_TYPE = "Email already used, kindly activate your account or use another email address";
    
    public static final String LOGIN_ALREADY_USED_TYPE = "Username already used, kindly activate your account, or choose another username";
    
    public static final String EMAIL_NOT_FOUND_TYPE = "Email not found";
    public static final String EMAIL_CONFIGS_NOT_SET = "Email configurations not set";

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final String PERSISTENCE_UNIT_NAME = "pu";

    public static final long serialVersionUID = 1L;
    public static final String MYSQL_PERSISTENCE_UNIT = "MYSQL.MSS";
    public static final String ML = "MEMBER_LOGIN";
    public static final String AL = "ADMIN_LOGIN";
    public static final String USER = "USERNAME";
    public static final String UID = "USER_ID";
    public static final String PROFILE_ID = "PROFILE_ID";
    public static final String LOGIN = "LOGIN";
    public static final String U_PROFILE = "USER_PROFILE";
    public static final String MEMBER_PROFILE = "MEMBER";
    public static final String CHILD_SPONSOR = "CHILD_SPONSOR";

    public static final String ADMIN_PROFILE = "ADMINISTRATOR";
    public static final String PRINCIPAL_OFFICER = "PRINCIPAL_OFFICER";
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String PAGE_SIGN_IN = "SIGN_IN";
    public static final String PAGE_LOGIN = "LOGIN";
    public static final String SCHEME_NAME = "SCHEME_NAME";
    public static final String SCHEME_TYPE = "SCHEME_TYPE";
    public static final String PAGE_HOME = "HOME";
    public static final String PAGE_FAQ = "FAQ";
    public static final String PAGE_INTEREST_RATES = "INTEREST_RATES";
    public static final String PAGE_UNIT_PRICES = "UNIT_PRICES";


    public static final String PAGE_WHAT_IF_ANALYSIS = "WHAT_IF_ANALYSIS";
    public static final String PAGE_ANNUITY_QUOTATION = "ANNUITY_QUOTATION";
    public static final String PAGE_BENEFIT_PROJECTION = "PAGE_BENEFIT_PROJECTION";


    public static final String PAGE_POTENTIAL_MEMBER = "POTENTIAL_MEMBER";
    public static final String PAGE_POTENTIAL_SPONSOR = "POTENTIAL_SPONSOR";
    public static final String PAGE_CONTACT_US = "CONTACT_US";
    public static final String PAGE_REGISTER = "REGISTRATION";
    public static final String SCHEME_ID = "SCHEME_ID";
    public static final String SPONSOR_ID = "SPONSOR_ID";
    public static final String REQUEST_SPONSOR_ID = "REQUEST_SPONSOR_ID";

    public static final String MANAGER_PROFILE = "MANAGER_PROFILE";
    public static final String MANAGER = "MANAGER";
    public static final String AGENT_PROFILE = "AGENT";
    public static final String SPONSOR = "SPONSOR";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";
    public static final String NUMBER_ZER0 = "0.00";
    public static final String EMAIL = "EMAIL";
    public static final String SMS = "SMS";
    public static final String ALL = "ALL";
    public static final String CUSTOMER_RELATIONSHIP_EXECUTIVE = "CUSTOMER_RELATIONSHIP_EXECUTIVE";
    public static final String CUSTOMER_RELATIONSHIP_MANAGER = "CUSTOMER_RELATIONSHIP_MANAGER";
    public static final String TRUSTEE = "TRUSTEE";
    public static final String FUND_MANAGER = "FUND_MANAGER";
    public static final String PENSIONER = "PENSIONER";
    public static final String CUSTODIAN = "CUSTODIAN";
    public static final String DEFINED_BENEFIT= "Defined Benefit";
    public static int RECORD_COUNT = 0;
    public static String BASE_URL;


    public static final String MMM_d_yyyy = "MMM d, yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final int TIMEOUT = 300 * 1000;
    public static final String ROWS = "rows";
    public static final String BOTH = "BOTH";
    public static final String MSS = "PORTAL";
    public static final String XI = "FUNDMASTER";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";
    public static final String HTTPS = "https";
    public static final String BANK = "bankId";

    public static final String SEND_SUPPORT_MESSAGE = "SEND_SUPPORT_MESSAGE";
    public static final String SEND_ACTIVATION_EMAIL3 = "SEND_ACTIVATION_EMAIL3";
    public static final String SEND_CLAIM_CHANGES_MAIL = "SEND_CLAIM_CHANGES_MAIL";
    public static final String SEND_PLAIN_MESSAGE = "SEND_PLAIN_MESSAGE";

    private Constants() {
    }
}
