package com.systech.mss.service.enums;

public enum MembershipStatus {

    /** These are members who are actively contributing to the {@link Scheme} */
    ACTIVE("Active"),
    /**
     * These are members who have stopped contributing to the {@link Scheme} for one reason or another
     */
    INACTIVE("Inactive"), // Dormant
    /** These are members who are notice of exit.. An exit has been initiated but not yet paid */
    NOTIFIED("On Notice of Exit"),
    /**
     * These are members who have exited the {@link Scheme} on grounds of {@link ExitCategory} of type
     * Retirement (Early, Normal or Late
     */
    RETIRED("Retired"),
    /**
     * These are members who have exited the {@link Scheme} on grounds of retirement due to ill-health
     */
    RETIRED_ILL_HEALTH("Retired(Ill Health)"),
    /** These are members who have exited the {@link Scheme} on grounds of death during retirement */
    DEATH_IN_RETIREMENT("Died In Retirement"),
    /**
     * These are members who have exited the {@link Scheme} on grounds voluntary withdrawal and are
     * not vested
     */
    WITHDRAWN("Withdrawn"),
    /**
     * These are members who have exited the {@link Scheme} on grounds of {@link ExitCategory} of type
     * DEATH while still at work
     */
    DEATH_IN_SERVICE("Died In Service"),
    /** These are members who have exited the {@link Scheme} on grounds interdiction */
    INTERDICTION("On Interdiction"),
    /**
     * These are members who have exited the {@link Scheme} on grounds being suspended from work place
     */
    SUSPENSION("On Suspension"),
    /**
     * These are members who are still members of the {@link Scheme} but are on a break of service
     * that is not paid
     */
    UNPAID_LEAVE("Unpaid Leave Of Absence"),
    /**
     * These are members who are active in a {@link Scheme} but they current employer is not their
     * original. They were seconded by a previous employer. Usually happens when a company splits or
     * create independent subsidiaries
     */
    SECONDMENT("Secondment"),
    /**
     * These are members who have withdrawn from the {@link Scheme} but they still have some deferred
     * balances of the ER portion
     */
    DEFFERED("Deferred"),
    /** These are members who have exited the {@link Scheme} by transferring to another scheme */
    TRANSFERED("Transfered"),
    /** These are members who have been deleted from the System */
    DELETED("Deleted"),

    POTENTIAL_ANNUITANT("Potential Annuitant"),
    /**
     * These are members who have stopped contributing to the {@link Scheme} for one reason or another
     */
    @Deprecated
    DORMANT("Dormant"),

    RETIRED_AND_UNPAID_PENSION_CAPITALIZED("Retired and Unpaid Pension Capitalized"),

    RETIRED_AND_TRIVIAL_PENSION_PAID("Retired and Trivial Pension Paid"),

    DEATH_IN_DEFERMENT("Died In Deferment"),

    TRANSFERRED_INTRA_INTRA("Transferred Intra-Intra"),

    TRANSFERRED_INTRA_OUT("Transferred Intra-Out"),

    PARTIAL_WITHDRAWAL_PAID("Partial Withdrawal Paid");

    private String name;

    /** The default constructor of the {@link MembershipStatus} {@link Enum} */
    MembershipStatus(String name) {
        this.name = name;
    }

    /**
     * This method receives a {@link String} and returns the {@link MembershipStatus} equivalent of
     * the {@link String}. If not found, it will return a null
     */
    public static MembershipStatus fromString(String text) {
        System.out.println("text : " + text);
        if (text != null) {
            for (MembershipStatus b : MembershipStatus.values()) {
                System.out.println("text : " + b.name());
                if (text.equalsIgnoreCase(b.name()) || text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }

    /** This method returns the name of the {@link MembershipStatus} */
    public String getName() {
        return name;
    }

    /** This method sets the name of the {@link MembershipStatus} */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
