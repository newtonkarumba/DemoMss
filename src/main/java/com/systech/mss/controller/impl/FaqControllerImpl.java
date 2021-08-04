package com.systech.mss.controller.impl;

import com.systech.mss.controller.FaqController;
import com.systech.mss.controller.vm.FaqVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.FAQ;
import com.systech.mss.domain.Profile;
import com.systech.mss.repository.FaqControllerRepository;
import com.systech.mss.repository.ProfileRepository;
import com.systech.mss.seurity.DateUtils;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class FaqControllerImpl extends BaseController implements FaqController {

    @Inject
    FaqControllerRepository faqControllerRepository;

    @Inject
    ProfileRepository profileRepository;

    @Override
    public Response getAll() {
        List<FAQ> faqs = faqControllerRepository.findAll();
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(setExtraDetails(faqs)).build())
                .build();
    }

    @Override
    public Response getAllByProfile(long profileId) {
        List<FAQ> faqs = faqControllerRepository.getAllByProfile(profileId);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(setExtraDetails(faqs)).build())
                .build();
    }

    @Override
    public Response addEditFAQ(FaqVM faqVM) {
        FAQ faq;
        Profile profile = profileRepository.find(faqVM.getProfileId());
        if (profile == null)
            return ErrorMsg("Profile not found");

        if (faqVM.getId() != 0L) {
            faq = faqControllerRepository.find(faqVM.getId());
            if (faq == null)
                return ErrorMsg("Record not found");
        }

        faq = faqControllerRepository.addEditFAQ(FAQ.getFAQ(faqVM, profile));
        if (faq != null)
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Successful").build())
                    .build();
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response delete(long mssUserId,long id) {
        FAQ faq=faqControllerRepository.find(id);
        if (faq!=null) {
            faqControllerRepository.remove(faq);
            logActivityTrail(mssUserId,"Deleted FAQ");
            return SuccessMsg("Done",null);
        }
        return ErrorMsg("Failed to remove FAQ");
    }

    List<FAQ> setExtraDetails(List<FAQ> faqs) {
        for (FAQ faq : faqs) {
            faq.setIdProfile(faq.getProfile().getId());
            faq.setProfileName(faq.getProfile().getName());
            faq.setShortDate(DateUtils.shortDate(faq.getCreatedAt()));
            faq.setCreatedAt(null);
            faq.setProfile(null);
        }
        return faqs;
    }
}
