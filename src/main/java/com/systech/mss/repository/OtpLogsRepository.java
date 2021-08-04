package com.systech.mss.repository;

import com.systech.mss.domain.OtpLogs;

import java.util.List;

public interface OtpLogsRepository extends AbstractRepository<OtpLogs,Long>{
    List<OtpLogs> findByLogin(String username);

    OtpLogs findLatestByUsername(String username);

    OtpLogs findUnExpiredOtp(String login);

    OtpLogs findExpiredToken(int days, String username);

    OtpLogs checkIfOtpValid(String username);
}
