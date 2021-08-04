package com.systech.mss.controller.impl;

import com.systech.mss.controller.LandingPageContentController;
import com.systech.mss.controller.vm.AddressVM;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.*;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.DocumentRepository;
import com.systech.mss.repository.LandingPageContentRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.vm.LandingPageContentVM;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

public class LandingPageContentImpl extends BaseController implements LandingPageContentController {
    @Inject
    LandingPageContentRepository landingPageContentRepository;

    @Inject
    DocumentRepository documentRepository;

    @Inject
    ActivityTrailService activityTrailService;


    @Override
    public Response getLandingPageContentDetailsAll() {
        try {
            LandingPageContent landingPageContents = landingPageContentRepository.getActiveLandingPageContent();
            if (landingPageContents != null) {
                landingPageContents.setShortDate(DateUtils.shortDate(landingPageContents.getCreatedDate()));
                landingPageContents.setCreatedDate(null);

                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data(landingPageContents)
                                .build()
                        ).build();
            }
        } catch (Exception ignored) {
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("No data found").build())
                .build();
    }

    @Override
    public Response postLandingPageContent(@Valid LandingPageContent landingPageContent) {
        LandingPageContent c = landingPageContentRepository.createLandingPageContent(landingPageContent);
        if (c != null) {
            return Response.ok()
                    .entity(SuccessVM
                            .builder()
                            .success(true)
                            .build()
                    ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response updateLogo(long documentId) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setLogo(documentId);

                landingPageContentRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Logo updated successfully")
                                .build()
                        ).build();

            }
        } else {
            LandingPageContent landingPageContent = new LandingPageContent();
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                landingPageContent.setLogo(documentId);
                landingPageContent.setStatusConfig(StatusConfig.ACTIVE);
                landingPageContentRepository.create(landingPageContent);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Logo updated successfully")
                                .build()
                        ).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response pensionerImage(long documentId) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setPensionerImage(documentId);


                landingPageContentRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Pensioner Image updated successfully")
                                .build()
                        ).build();
            }
        } else {
            LandingPageContent landingPageContent = new LandingPageContent();
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                landingPageContent.setPensionerImage(documentId);
                landingPageContent.setStatusConfig(StatusConfig.ACTIVE);
                landingPageContentRepository.create(landingPageContent);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Pensioner Image updated successfully")
                                .build()
                        ).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response loginImage(long documentId) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setLoginImage(documentId);
                landingPageContentRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Login Image updated successfully")
                                .build()
                        ).build();
            }
        } else {
            LandingPageContent landingPageContent = new LandingPageContent();
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                landingPageContent.setLoginImage(documentId);
                landingPageContent.setStatusConfig(StatusConfig.ACTIVE);
                landingPageContentRepository.create(landingPageContent);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Login Image updated successfully")
                                .build()
                        ).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response memberIcon(long documentId) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setMemberIcon(documentId);


                landingPageContentRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Member Icon updated successfully")
                                .build()
                        ).build();
            }
        } else {
            LandingPageContent landingPageContent = new LandingPageContent();
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                landingPageContent.setMemberIcon(documentId);
                landingPageContent.setStatusConfig(StatusConfig.ACTIVE);
                landingPageContentRepository.create(landingPageContent);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Member Icon updated successfully")
                                .build()
                        ).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response pensionerIcon(long documentId) {

        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setPensionerIcon(documentId);
                current.setAddress(landingPageContentRepository.getActiveLandingPageContent().getAddress());
                landingPageContentRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Pensioner Icon updated successfully")
                                .build()
                        ).build();
            }
        } else {
            LandingPageContent landingPageContent = new LandingPageContent();
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                landingPageContent.setPensionerIcon(documentId);
                landingPageContent.setStatusConfig(StatusConfig.ACTIVE);
                landingPageContentRepository.create(landingPageContent);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("Pensioner Icon updated successfully")
                                .build()
                        ).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response address(@Valid AddressVM addressVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {

            Geo geo = new Geo();
            geo.setLat(addressVM.getLat());
            geo.setLng(addressVM.getLng());

            Address address = new Address();
            address.setGeo(geo);

            address.setBuilding(addressVM.getBuilding());
            address.setCountry(addressVM.getCountry());
            address.setEmail(addressVM.getEmail());
            address.setFixedPhone(addressVM.getFixedPhone());
            address.setPostalAddress(addressVM.getPostalAddress());
            address.setRoad(addressVM.getRoad());
            address.setSecondaryPhone(addressVM.getSecondaryPhone());
            address.setTown(addressVM.getTown());
            address.setBusinessHours(addressVM.getBusinessHours());

            current.setAddress(address);
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            Geo geo = new Geo();
            geo.setLat(addressVM.getLat());
            geo.setLng(addressVM.getLng());

            Address address = new Address();
            address.setGeo(geo);

            address.setBuilding(addressVM.getBuilding());
            address.setCountry(addressVM.getCountry());
            address.setEmail(addressVM.getEmail());
            address.setFixedPhone(addressVM.getFixedPhone());
            address.setPostalAddress(addressVM.getPostalAddress());
            address.setRoad(addressVM.getRoad());
            address.setSecondaryPhone(addressVM.getSecondaryPhone());
            address.setTown(addressVM.getTown());

            current.setAddress(address);
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);

        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("Address updated successfully")
                        .build()
                ).build();

    }

    @Override
    public Response welcomeStatement(@Valid LandingPageContentVM landingPageContentVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            current.setWelcomeMessage(landingPageContentVM.getWelcomeMessage());
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            current.setWelcomeMessage(landingPageContentVM.getWelcomeMessage());
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);
        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("welcomeMessage updated successfully")
                        .build()
                ).build();
    }

    @Override
    public Response memberMessage(@Valid LandingPageContentVM landingPageContentVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            current.setMemberMessage(landingPageContentVM.getMemberMessage());
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            current.setMemberMessage(landingPageContentVM.getMemberMessage());
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);
        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("Member Message updated successfully")
                        .build()
                ).build();
    }

    @Override
    public Response pensionerMessage(@Valid LandingPageContentVM landingPageContentVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            current.setPensionerMessage(landingPageContentVM.getPensionerMessage());
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            current.setPensionerMessage(landingPageContentVM.getPensionerMessage());
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);
        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("Pensioner Message updated successfully")
                        .build()
                ).build();
    }

    @Override
    public Response whySaveMessage(@Valid LandingPageContentVM landingPageContentVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            current.setWhySaveMessage(landingPageContentVM.getWhySaveMessage());
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            current.setWhySaveMessage(landingPageContentVM.getWhySaveMessage());
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);
        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("WhySave Message updated successfully")
                        .build()
                ).build();
    }

    @Override
    public Response mapLoc(@Valid LandingPageContentVM landingPageContentVM) {
        LandingPageContent current = landingPageContentRepository.getActiveLandingPageContent();
        if (current != null) {
            current.setMapLoc(landingPageContentVM.getMapLoc());
            landingPageContentRepository.edit(current);

        } else {
            current = new LandingPageContent();
            current.setMapLoc(landingPageContentVM.getMapLoc());
            current.setStatusConfig(StatusConfig.ACTIVE);
            landingPageContentRepository.create(current);
        }
        return Response.ok()
                .entity(SuccessVM
                        .builder()
                        .success(true)
                        .data("MapLoc Message updated successfully")
                        .build()
                ).build();
    }

    @Override
    public Response favicon(long mssuserid, MultipartFormDataInput input) throws ParseException {
        List<FileModel> fileModels = upload(input, "favicon.png");
        if (fileModels == null) {
            return ErrorMsg("Failed to upload documents");
        }
        logActivityTrail(mssuserid, "Updated favicon");
        return SuccessMsg("Done", null);
    }

    @Override
    public Response setLoginImage(long mssuserid, MultipartFormDataInput input) throws ParseException {
        List<FileModel> fileModels = upload(input, "bgImage.png");
        if (fileModels == null) {
            return ErrorMsg("Failed to upload documents");
        }
        logActivityTrail(mssuserid, "Updated image");
        return SuccessMsg("Done", null);
    }

}
