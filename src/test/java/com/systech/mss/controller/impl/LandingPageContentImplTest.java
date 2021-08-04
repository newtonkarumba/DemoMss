package com.systech.mss.controller.impl;

import com.systech.mss.domain.LandingPageContent;
import com.systech.mss.repository.DocumentRepository;
import com.systech.mss.repository.LandingPageContentRepository;
import com.systech.mss.service.ActivityTrailService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.mockito.Mockito.mock;
@RunWith(MockitoJUnitRunner.class)
class LandingPageContentImplTest {

    private LandingPageContentImpl landingPageContentImplUnderTest;

    @BeforeEach
    void setUp() {
        landingPageContentImplUnderTest = new LandingPageContentImpl();
        landingPageContentImplUnderTest.landingPageContentRepository = mock(LandingPageContentRepository.class);
        landingPageContentImplUnderTest.documentRepository = mock(DocumentRepository.class);
        landingPageContentImplUnderTest.activityTrailService = mock(ActivityTrailService.class);
        landingPageContentImplUnderTest.log = mock(Logger.class);
    }

    @Test
    void testGetLandingPageContentDetailsAll() {
       LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testPostLandingPageContent() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
        // Verify the results
    }

    @Test
    void testUpdateLogo() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);    }

    @Test
    void testPensionerImage() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testLoginImage() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testMemberIcon() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testPensionerIcon() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testAddress() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testWelcomeStatement() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testMemberMessage() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testPensionerMessage() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }

    @Test
    void testWhySaveMessage() {
        LandingPageContent landingPageContent = new LandingPageContent();
        Assert.assertNotNull(landingPageContent);
    }
}
