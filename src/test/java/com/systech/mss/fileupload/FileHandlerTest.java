package com.systech.mss.fileupload;

import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;
import com.systech.mss.repository.DocumentRepository;
import com.systech.mss.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileHandlerTest {

    @Mock
    private DocumentRepository mockDocumentRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private FileHandler fileHandlerUnderTest;

    @Test
    public void testInit() throws Exception {
        // Setup

        // Run the test
        fileHandlerUnderTest.init();

        // Verify the results
    }

    @Test
    public void testInit_ThrowsServletException() {
        // Setup

        // Run the test
        assertThrows(ServletException.class, () -> fileHandlerUnderTest.init());
    }

    @Test
    public void testGetServletInfo() {
        // Setup

        // Run the test
        final String result = fileHandlerUnderTest.getServletInfo();

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testDoGet() throws Exception {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure DocumentRepository.find(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.find(0L)).thenReturn(documents);

        // Run the test
        fileHandlerUnderTest.doGet(mockRequest, mockResponse);

        // Verify the results
    }

    @Test
    public void testDoGet_ThrowsServletException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure DocumentRepository.find(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.find(0L)).thenReturn(documents);

        // Run the test
        assertThrows(NullPointerException.class, () -> fileHandlerUnderTest.doGet(mockRequest, mockResponse));
    }

    @Test
    public void testDoGet_ThrowsIOException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure DocumentRepository.find(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.find(0L)).thenReturn(documents);

        // Run the test
        assertThrows(NullPointerException.class, () -> fileHandlerUnderTest.doGet(mockRequest, mockResponse));
    }

    @Test
    public void testDoPost() throws Exception {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        fileHandlerUnderTest.doPost(mockRequest, mockResponse);

        // Verify the results
    }

    @Test
    public void testDoPost_ThrowsServletException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        assertThrows(ServletException.class, () -> fileHandlerUnderTest.doPost(mockRequest, mockResponse));
    }

    @Test
    public void testDoPost_ThrowsIOException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        assertThrows(IOException.class, () -> fileHandlerUnderTest.doPost(mockRequest, mockResponse));
    }

    @Test
    public void testUploadSingleFile() throws Exception {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        fileHandlerUnderTest.uploadSingleFile(mockRequest, mockResponse);

        // Verify the results
    }

    @Test
    public void testUploadSingleFile_ThrowsServletException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        assertThrows(NullPointerException.class, () -> fileHandlerUnderTest.uploadSingleFile(mockRequest, mockResponse));
    }

    @Test
    public void testUploadSingleFile_ThrowsIOException() {
        // Setup
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        // Configure UserRepository.find(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserRepository.find(0L)).thenReturn(user);

        // Configure DocumentRepository.create(...).
        final Documents documents = new Documents();
        documents.setFromUserId(0L);
        documents.setId(0L);
        documents.setToUserId(0L);
        documents.setFileName("fileName");
        documents.setOriginalFileName("originalFileName");
        documents.setFilePath("filePath");
        documents.setForProfileId(0L);
        documents.setComments("comments");
        documents.setFileSize(0L);
        documents.setExpiryDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockDocumentRepository.create(any(Documents.class))).thenReturn(documents);

        // Run the test
        assertThrows(NullPointerException.class, () -> fileHandlerUnderTest.uploadSingleFile(mockRequest, mockResponse));
    }
}
