package com.systech.mss.repository;

import com.systech.mss.domain.LandingPageContent;

public interface LandingPageContentRepository extends AbstractRepository<LandingPageContent, Long> {
    LandingPageContent getActiveLandingPageContent();

    LandingPageContent createLandingPageContent(LandingPageContent landingPageContent);
}
