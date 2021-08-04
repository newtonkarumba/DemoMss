package com.systech.mss.repository;

import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.User;

import java.util.List;

public interface ActivityTrailRepository extends AbstractRepository<ActivityTrail, Long> {

    ActivityTrail getActivityTrail(long userId, String msg);

    List<ActivityTrail> getByUserId(User user);

    //List<ActivityTrail> getAdminByStaffNo(String StaffNo);

    List<ActivityTrail> getActivityOfDate(String date);

    List<ActivityTrail> searchActivityTrail(long mssuserid,String name);

    List<ActivityTrail> searchActivityTrailMultiUser(long mssuserid,String name);

    List<ActivityTrail> getActivityLogOfUserOnDate(long userId, String date);


    List<ActivityTrail> filterActivityByDate(long mssuserid,String dateFrom, String dateTo);

    List<ActivityTrail> filterActivityByDateMultiUser(long mssuserid,String dateFrom, String dateTo);

    List<ActivityTrail> getAllActivityLogs();
}
