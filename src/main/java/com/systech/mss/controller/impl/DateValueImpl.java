package com.systech.mss.controller.impl;

import com.systech.mss.controller.DateValue;
import com.systech.mss.controller.vm.DateValueVm;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.DateValues;
import com.systech.mss.domain.Session;
import com.systech.mss.repository.DateRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

public class DateValueImpl  extends BaseController implements DateValue {
    @Inject
    DateRepository dateRepository;
    @Override
    public Response getAll() {
        List<DateValues> dateValues=dateRepository.findAll();
        return dateValues!=null?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(dateValues)

                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response updateDate( long billDate) {
        if(billDate != 0 && billDate <=31){
            dateRepository.update(billDate);
        }
        else{
            return ErrorMsg("Invalid date");
        }

        return Response.ok()
                .entity(
                        SuccessVM.builder().success(true)
                                .msg("Done")
                                .build()
                ).build();
    }
}
