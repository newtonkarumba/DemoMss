package com.systech.mss.vm;

import com.systech.mss.util.Ignore;

public class PasswordChangeVM {

    long userId;

    @Ignore
    private String login; // email or [login] value

    @Ignore
    private String currentPassword;

    private String newPassword;
    private String confirmPassword;

    public PasswordChangeVM() {
    }

    public PasswordChangeVM(String login, String currentPassword, String newPassword, String confirmPassword) {
        this.login = login;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public PasswordChangeVM(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
