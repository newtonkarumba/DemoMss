package com.systech.mss.domain;

public enum EmailTemplatesEnum {
    //A
    ACCOUNT_ACTIVATION("Account Activation","Default template for account activation",
            "name", "username", "password","portalLink"),
    ADMIN_ACCOUNT_ACTIVATION("Admin Account Activation","For new admin registered account activation",
            "name","username","password", "portalLink"),

    ADMIN_PASSWORD_RESET("Admin Password Reset", "Admin Reset Password", "name", "username","password", "portalLink" ),

    //C
    CLAIM_STATUS("Claim Status", "For sending claim status updates to member",
            "name", "benefitNumber", "change","portalLink"),
    CLAIM_INITIATED("New Pending Claim","Notify PO/CRM of new claim from member",
            "name", "memberName","portalLink"),

    //M
    MEMBER_CLAIM_INITIATED("Member Claim Initiated","Notify member benefit request has been received",
            "name", "benefitNumber","portalLink"),
    MEMBER_ACCOUNT_ACTIVATION("Member Account Activation","Notify member of account creation",
            "name", "username", "password", "portalLink"),
    MEMBER_BENEFICIARY_APPROVAL("Member Beneficiary Approval","Notify member beneficiary details have been approved",
            "name", "portalLink"),
    MEMBER_BENEFICIARY_DECLINE("Member Beneficiary Decline","Notify member beneficiary details have been declined",
            "name", "portalLink"),
    MEMBER_BIO_DATA_UPDATE_APPROVAL("Member Bio Data Approval","Notify member bio-data have been approved",
            "name","portalLink"),
    MEMBER_BIO_DATA_UPDATE_DECLINE("Member Bio Data Decline","Notify member bio-data have been declined",
            "name", "portalLink"),

    //N
    NEW_MEMBER_REGISTERED("New Member Registered","Notify new member registration success",
            "name","scheme","sponsor", "portalLink"),
    NEW_MEMBER_APPROVAL("New Member Approval","Notify new member registration request acceptance",
            "name","scheme","sponsor", "portalLink"),
    NEW_MEMBER_DECLINE("New Member Decline","Notify new member registration request declined",
            "name","scheme","sponsor", "portalLink"),

    //O
    OTP_VERIFICATION("OTP Verification","Send OTP code",
            "name", "token"),

    //P
    PASSWORD_RESET("Password Reset","Send new password to user",
            "name", "username", "password", "portalLink"),
    PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION("Principal Officer Account Activation","Notify PO of account creation",
            "name", "username", "password","portalLink"),
    PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST("Principal Officer Beneficiary Approval","Notify PO member has updated beneficiary",
            "name", "memberName", "portalLink"),
    PO_MEMBER_BIO_DATA_APPROVAL_REQUEST("Principal Officer Member Bio-Data Approval","Notify PO member has updated Bio-Data",
            "name", "memberName", "portalLink"),
    PO_PENDING_CLAIM("Principal Officer Pending Claim","Notify PO claim has been initiated",
            "name", "memberName", "portalLink"),
    PO_NEW_MEMBER_APPROVAL_REQUEST("Principal Officer New Member Approval","Notify PO a member has registered",
            "name","memberName", "scheme","sponsor", "portalLink"),

    //R
    REQUEST_PASSWORD_RESET("Forgot Password","Request password reset",
            "name", "url"),

    //T
    TICKET_RAISED("Ticket Raised","Notify user ticket raised",
            "name", "ticketNumber", "portalLink"),
    TICKET_REPLY("Ticket Reply","Notify user ticket replied",
            "name", "ticketNumber", "message", "replyBy", "timeReplied", "portalLink");


    private String name;
    private String description;
    private String[] namedKeys;

    EmailTemplatesEnum(String name, String description, String... namedKeys) {
        this.name = name;
        this.description = description;
        this.namedKeys = namedKeys;
    }

    public String[] getNamedKeys() {
        return namedKeys;
    }

    public void setNamedKeys(String[] namedKeys) {
        this.namedKeys = namedKeys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param value eg Forgot Password
     * @return EmailTemplatesEnum
     */
    public static EmailTemplatesEnum getEmailTemplatesEnum(String value) {
        EmailTemplatesEnum[] emailTemplatesEnums = EmailTemplatesEnum.values();
        for (EmailTemplatesEnum emailTemplatesEnum : emailTemplatesEnums) {
            if (emailTemplatesEnum.getName().equalsIgnoreCase(value)) {
                return emailTemplatesEnum;
            }
        }
        return null;
    }

    /**
     *
     * @param category eg REQUEST_PASSWORD_RESET
     * @return EmailTemplatesEnum
     */
    public static EmailTemplatesEnum from(String category) {
        EmailTemplatesEnum[] emailTemplatesEnums = EmailTemplatesEnum.values();
        for (EmailTemplatesEnum emailTemplatesEnum : emailTemplatesEnums) {
            if (emailTemplatesEnum.name().equalsIgnoreCase(category)) {
                return emailTemplatesEnum;
            }
        }
        return null;
    }

}
