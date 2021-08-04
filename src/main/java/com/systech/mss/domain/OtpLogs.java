package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systech.mss.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Getter
@Setter
@Table(name="otplogs")
@ToString
public class OtpLogs  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String token;

    @Column
    @Enumerated(EnumType.STRING)
    private OtpIdentifier otpIdentifier;

    @Column
    @JsonIgnore
    private Date expiry=new Date();

    @Column
    @JsonIgnore
    private Date nextCheck=new Date();

    @Column(updatable = false)
    @JsonIgnore
    private Date createdAt=new Date();

    public static OtpLogs newInstance(String login, String otpIdentifier) {
        OtpLogs otpLogs=new OtpLogs();
        Calendar calendar=Calendar.getInstance();
        int hr=calendar.get(Calendar.HOUR)+1;
        calendar.set(Calendar.HOUR,hr);
        otpLogs.setExpiry(calendar.getTime());
        otpLogs.setToken(StringUtil.generateRandomCode(6));
        otpLogs.setUsername(login);
        otpLogs.setOtpIdentifier(OtpIdentifier.from(otpIdentifier));
        return otpLogs;
    }

    public static OtpLogs from(OtpLogs otpLogs) {
        Calendar calendar=Calendar.getInstance();
        int hr=calendar.get(Calendar.HOUR)+1;
        calendar.set(Calendar.HOUR,hr);
        otpLogs.setExpiry(calendar.getTime());
        return otpLogs;
    }

    public static boolean isExpired(OtpLogs otpLogs) {
        Date now = Calendar.getInstance().getTime();
        long diffInMillies = Math.abs(now.getTime() - otpLogs.getExpiry().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff > 0;
    }
}
