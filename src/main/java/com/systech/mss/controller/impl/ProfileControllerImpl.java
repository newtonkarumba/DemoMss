package com.systech.mss.controller.impl;

import com.systech.mss.controller.ProfileController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.ProfileVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.Profile;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ProfileRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileControllerImpl extends BaseController implements ProfileController {

    @Inject
    ProfileRepository profileRepository;

    @Override
    public Response selfRegisterProfiles() {
        Config config = configRepository.getActiveConfig();

        List<String> allowed = new ArrayList<>();
        allowed.add("MEMBER");

        if (config != null) {
            Clients clients = config.getClient();
            switch (clients) {
                case ETL:
                    allowed.add("SPONSOR");
                    allowed.add("CRM");
                    break;

                case BENCON:
                    allowed.add("SPONSOR");
                    allowed.add("CRM");
                    allowed.add("CRE");
                    break;

                case NICO:
                    allowed.add("PRINCIPAL OFFICER");
                    break;

                default:
                    allowed.add("SPONSOR");
                    allowed.add("CRM");
                    allowed.add("PRINCIPAL OFFICER");
                    allowed.add("PENSIONER");
            }
        } else {
            allowed.add("SPONSOR");
            allowed.add("CRM");
            allowed.add("PRINCIPAL OFFICER");
            allowed.add("PENSIONER");
        }
        List<Profile> list = profileRepository.findAll();
        list.removeIf(profile -> !allowed.contains(profile.getName()));
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true)
                        .data(setExtras(list))
                        .build()
                ).build();
    }

    @Override
    public Response getAll() {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true)
                        .data(setExtras(profileRepository.findAll()))
                        .build()
                ).build();
    }


    @Override
    public Response getById(long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        profile.ifPresent(value -> Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true)
                        .data(value)
                        .build()
                ).build());
        return Response.status(Response.Status.OK)
                .entity(ErrorVM.builder().success(false)
                        .build()
                ).build();
    }

    @Override
    public Response edit(long mssUserId, ProfileVM profileVM) {
        logActivityTrail(mssUserId, "Editing profile " + profileVM.getName());
        Profile p = null;
        if (profileVM.getId() != -1L) {
            Optional<Profile> profile = profileRepository.findById(profileVM.getId());
            if (profile.isPresent()) {
                p = profile.get();
            }
        } else {
            Profile profile = profileRepository.findByName(profileVM.getName());
            if (profile != null) {
                p = profile;
            }
        }

        if (p != null) {
            p.setLoginIdentifier(profileVM.getLoginIdentifier());
            profileRepository.edit(p);
            return SuccessMsg("Done", null);
        }
        return ErrorMsg("No such profile");
    }

    private List<Profile> setExtras(List<Profile> list) {
        if (list == null) return new ArrayList<>();
        for (Profile profile : list) {
            profile.setLoginIdentifierName(
                    profile.getLoginIdentifier().getName()
            );
        }
        return list;
    }
}
