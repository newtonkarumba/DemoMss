package com.systech.mss.repository;

import com.systech.mss.controller.vm.DateValueVm;
import com.systech.mss.domain.DateValues;



public interface DateRepository extends AbstractRepository<DateValues, Long>{
    DateValues getDates();
    DateValues update(long billDate);
    DateValues setDefault();
}
