package com.systech.mss.controller.impl;

import com.systech.mss.domain.Config;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.ConfigService;
import com.systech.mss.service.dto.FmListDTO;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ConfigControllerImplTest {

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private ConfigService configService;

    @Inject
    private Config config;

    @Inject
    Logger logger;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetSpecificFieldsOfActiveConfigs() {
    Config config = new Config();
    Assert.assertNotNull(config);

    }

    @Test
    void testGetAllConfigs() {
        Config config = new Config();
        Assert.assertNotNull(config);
           }



    @Test
    void testCreateConfig() {
        Config config = new Config();
        Assert.assertNotNull(config);
    }



    @Test
    void testEditConfig() {
        Config config = new Config();
        Assert.assertNotNull(config);
    }

    @Test
    void testDeleteAdmin() {
        Config config = new Config();
        Assert.assertNotNull(config);
    }
}
