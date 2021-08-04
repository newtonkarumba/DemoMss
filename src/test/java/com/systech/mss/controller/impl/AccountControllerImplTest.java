package com.systech.mss.controller.impl;

import com.systech.mss.controller.vm.ManagedUserVM;
import com.systech.mss.domain.*;
import com.systech.mss.repository.ProfileRepository;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.MailService;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.UserService;
import com.systech.mss.service.dto.MemberDTO;
import com.systech.mss.service.dto.PensionerDTO;
import com.systech.mss.service.dto.UserDTO;
import com.systech.mss.seurity.SecurityHelper;
import com.systech.mss.vm.KeyAndPasswordVM;
import com.systech.mss.vm.PasswordChangeVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private MailService mockMailService;
    @Mock
    private SecurityHelper mockSecurityHelper;
    @Mock
    private ProfileRepository mockProfileRepository;
    @Mock
    private FundMasterClient mockFundMasterClient;
    @Mock
    private ProfileService mockProfileService;

    @InjectMocks
    private AccountControllerImpl accountControllerImplUnderTest;

    @Test
    public void testRegisterAccount() {
        // Setup
        final ManagedUserVM managedUserVM = new ManagedUserVM(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);

        // Configure UserRepository.findOneByLogin(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(user);

        // Configure UserRepository.findOneByEmail(...).
        final User user3 = new User();
        user3.setId(0L);
        user3.setLogin("login");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setPassword("password");
        user3.setEmail("email");
        user3.setActivated(false);
        user3.setActivationKey("activationKey");
        user3.setSecurityCode("securityCode");
        user3.setResetKey("resetKey");
        final Optional<User> user2 = Optional.of(user3);
        when(mockUserRepository.findOneByEmail("email")).thenReturn(user2);

        // Configure ProfileRepository.findById(...).
        final Profile profile1 = new Profile();
        profile1.setId(0L);
        profile1.setName("name");
        profile1.setLoginIdentifier(LoginIdentifier.PHONE);
        final User user4 = new User();
        user4.setId(0L);
        user4.setLogin("login");
        user4.setFirstName("firstName");
        user4.setLastName("lastName");
        user4.setPassword("password");
        user4.setEmail("email");
        user4.setActivated(false);
        user4.setActivationKey("activationKey");
        user4.setSecurityCode("securityCode");
        user4.setResetKey("resetKey");
        profile1.setUsers(new HashSet<>(Arrays.asList(user4)));
        final Permission permission = new Permission();
        permission.setId(0L);
        permission.setName("name");
        permission.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        profile1.setPermissions(new HashSet<>(Arrays.asList(permission)));
        final TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setId(0L);
        ticketCategory.setName("name");
        ticketCategory.setDescription("description");
        ticketCategory.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        ticketCategory.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        profile1.setTicketCategories(new HashSet<>(Arrays.asList(ticketCategory)));
        final Optional<Profile> profile = Optional.of(profile1);
        when(mockProfileRepository.findById(0L)).thenReturn(profile);

        // Configure FundMasterClient.checkMemberIfExists(...).
        final MemberDTO memberDTO = new MemberDTO("mbshipStatus", "nationalPenNo", "pensionerId", false, "profile",
                0L, 0L, "name", 0L, "sponsorRefNo", "email", "cellPhone", "message", "staffNo", "accountStatus", 0L,0L,"","");
        when(mockFundMasterClient.checkMemberIfExists("identifierValue", "email", "profile")).thenReturn(memberDTO);

        // Configure UserService.createUser(...).
        final User user5 = new User();
        user5.setId(0L);
        user5.setLogin("login");
        user5.setFirstName("firstName");
        user5.setLastName("lastName");
        user5.setPassword("password");
        user5.setEmail("email");
        user5.setActivated(false);
        user5.setActivationKey("activationKey");
        user5.setSecurityCode("securityCode");
        user5.setResetKey("resetKey");
        when(mockUserService.createUser(eq("login"), eq("password"), eq("email"), eq("langKey"), eq("fmIdentifier"), eq(0L), any(MemberDTO.class))).thenReturn(user5);

        // Configure UserRepository.edit(...).
        final User user6 = new User();
        user6.setId(0L);
        user6.setLogin("login");
        user6.setFirstName("firstName");
        user6.setLastName("lastName");
        user6.setPassword("password");
        user6.setEmail("email");
        user6.setActivated(false);
        user6.setActivationKey("activationKey");
        user6.setSecurityCode("securityCode");
        user6.setResetKey("resetKey");
        when(mockUserRepository.edit(any(User.class))).thenReturn(user6);

        // Configure FundMasterClient.getPensionerDetails(...).
        final PensionerDTO pensionerDTO = new PensionerDTO(0L, false, Arrays.asList("value"));
        when(mockFundMasterClient.getPensionerDetails(0L)).thenReturn(pensionerDTO);

        // Run the test
        final Response result = accountControllerImplUnderTest.registerAccount(managedUserVM);

        // Verify the results
        verify(mockMailService).sendActivationEmail(any(User.class));
    }

    @Test
    public void testRegisterAccount_UserRepositoryFindOneByLoginReturnsAbsent() {
        // Setup
        final ManagedUserVM managedUserVM = new ManagedUserVM(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(Optional.empty());

        // Configure UserRepository.findOneByEmail(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByEmail("email")).thenReturn(user);

        // Configure ProfileRepository.findById(...).
        final Profile profile1 = new Profile();
        profile1.setId(0L);
        profile1.setName("name");
        profile1.setLoginIdentifier(LoginIdentifier.PHONE);
        final User user2 = new User();
        user2.setId(0L);
        user2.setLogin("login");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setPassword("password");
        user2.setEmail("email");
        user2.setActivated(false);
        user2.setActivationKey("activationKey");
        user2.setSecurityCode("securityCode");
        user2.setResetKey("resetKey");
        profile1.setUsers(new HashSet<>(Arrays.asList(user2)));
        final Permission permission = new Permission();
        permission.setId(0L);
        permission.setName("name");
        permission.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        profile1.setPermissions(new HashSet<>(Arrays.asList(permission)));
        final TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setId(0L);
        ticketCategory.setName("name");
        ticketCategory.setDescription("description");
        ticketCategory.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        ticketCategory.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        profile1.setTicketCategories(new HashSet<>(Arrays.asList(ticketCategory)));
        final Optional<Profile> profile = Optional.of(profile1);
        when(mockProfileRepository.findById(0L)).thenReturn(profile);

        // Configure FundMasterClient.checkMemberIfExists(...).
        final MemberDTO memberDTO = new MemberDTO("mbshipStatus", "nationalPenNo", "pensionerId", false, "profile",
                0L, 0L, "name", 0L, "sponsorRefNo", "email", "cellPhone", "message", "staffNo", "accountStatus", 0L,0L,"","");
        when(mockFundMasterClient.checkMemberIfExists("identifierValue", "email", "profile")).thenReturn(memberDTO);

        // Configure UserService.createUser(...).
        final User user3 = new User();
        user3.setId(0L);
        user3.setLogin("login");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setPassword("password");
        user3.setEmail("email");
        user3.setActivated(false);
        user3.setActivationKey("activationKey");
        user3.setSecurityCode("securityCode");
        user3.setResetKey("resetKey");
        when(mockUserService.createUser(eq("login"), eq("password"), eq("email"), eq("langKey"), eq("fmIdentifier"), eq(0L), any(MemberDTO.class))).thenReturn(user3);

        // Configure UserRepository.edit(...).
        final User user4 = new User();
        user4.setId(0L);
        user4.setLogin("login");
        user4.setFirstName("firstName");
        user4.setLastName("lastName");
        user4.setPassword("password");
        user4.setEmail("email");
        user4.setActivated(false);
        user4.setActivationKey("activationKey");
        user4.setSecurityCode("securityCode");
        user4.setResetKey("resetKey");
        when(mockUserRepository.edit(any(User.class))).thenReturn(user4);

        // Configure FundMasterClient.getPensionerDetails(...).
        final PensionerDTO pensionerDTO = new PensionerDTO(0L, false, Arrays.asList("value"));
        when(mockFundMasterClient.getPensionerDetails(0L)).thenReturn(pensionerDTO);

        // Run the test
        final Response result = accountControllerImplUnderTest.registerAccount(managedUserVM);

        // Verify the results
        verify(mockMailService).sendActivationEmail(any(User.class));
    }

    @Test
    public void testRegisterAccount_UserRepositoryFindOneByEmailReturnsAbsent() {
        // Setup
        final ManagedUserVM managedUserVM = new ManagedUserVM(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);

        // Configure UserRepository.findOneByLogin(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(user);

        when(mockUserRepository.findOneByEmail("email")).thenReturn(Optional.empty());

        // Configure ProfileRepository.findById(...).
        final Profile profile1 = new Profile();
        profile1.setId(0L);
        profile1.setName("name");
        profile1.setLoginIdentifier(LoginIdentifier.PHONE);
        final User user2 = new User();
        user2.setId(0L);
        user2.setLogin("login");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setPassword("password");
        user2.setEmail("email");
        user2.setActivated(false);
        user2.setActivationKey("activationKey");
        user2.setSecurityCode("securityCode");
        user2.setResetKey("resetKey");
        profile1.setUsers(new HashSet<>(Arrays.asList(user2)));
        final Permission permission = new Permission();
        permission.setId(0L);
        permission.setName("name");
        permission.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        profile1.setPermissions(new HashSet<>(Arrays.asList(permission)));
        final TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setId(0L);
        ticketCategory.setName("name");
        ticketCategory.setDescription("description");
        ticketCategory.setProfiles(new HashSet<>(Arrays.asList(new Profile())));
        ticketCategory.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        profile1.setTicketCategories(new HashSet<>(Arrays.asList(ticketCategory)));
        final Optional<Profile> profile = Optional.of(profile1);
        when(mockProfileRepository.findById(0L)).thenReturn(profile);

        // Configure FundMasterClient.checkMemberIfExists(...).
        final MemberDTO memberDTO = new MemberDTO("mbshipStatus", "nationalPenNo", "pensionerId", false, "profile",
                0L, 0L, "name", 0L, "sponsorRefNo", "email", "cellPhone", "message", "staffNo", "accountStatus", 0L,0L,"","");
        when(mockFundMasterClient.checkMemberIfExists("identifierValue", "email", "profile")).thenReturn(memberDTO);

        // Configure UserService.createUser(...).
        final User user3 = new User();
        user3.setId(0L);
        user3.setLogin("login");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setPassword("password");
        user3.setEmail("email");
        user3.setActivated(false);
        user3.setActivationKey("activationKey");
        user3.setSecurityCode("securityCode");
        user3.setResetKey("resetKey");
        when(mockUserService.createUser(eq("login"), eq("password"), eq("email"), eq("langKey"), eq("fmIdentifier"), eq(0L), any(MemberDTO.class))).thenReturn(user3);

        // Configure UserRepository.edit(...).
        final User user4 = new User();
        user4.setId(0L);
        user4.setLogin("login");
        user4.setFirstName("firstName");
        user4.setLastName("lastName");
        user4.setPassword("password");
        user4.setEmail("email");
        user4.setActivated(false);
        user4.setActivationKey("activationKey");
        user4.setSecurityCode("securityCode");
        user4.setResetKey("resetKey");
        when(mockUserRepository.edit(any(User.class))).thenReturn(user4);

        // Configure FundMasterClient.getPensionerDetails(...).
        final PensionerDTO pensionerDTO = new PensionerDTO(0L, false, Arrays.asList("value"));
        when(mockFundMasterClient.getPensionerDetails(0L)).thenReturn(pensionerDTO);

        // Run the test
        final Response result = accountControllerImplUnderTest.registerAccount(managedUserVM);

        // Verify the results
        verify(mockMailService).sendActivationEmail(any(User.class));
    }

    @Test
    public void testRegisterAccount_ProfileRepositoryReturnsAbsent() {
        // Setup
        final ManagedUserVM managedUserVM = new ManagedUserVM(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);

        // Configure UserRepository.findOneByLogin(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(user);

        // Configure UserRepository.findOneByEmail(...).
        final User user3 = new User();
        user3.setId(0L);
        user3.setLogin("login");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setPassword("password");
        user3.setEmail("email");
        user3.setActivated(false);
        user3.setActivationKey("activationKey");
        user3.setSecurityCode("securityCode");
        user3.setResetKey("resetKey");
        final Optional<User> user2 = Optional.of(user3);
        when(mockUserRepository.findOneByEmail("email")).thenReturn(user2);

        when(mockProfileRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure FundMasterClient.checkMemberIfExists(...).
        final MemberDTO memberDTO = new MemberDTO("mbshipStatus", "nationalPenNo", "pensionerId", false, "profile",
                0L, 0L, "name", 0L, "sponsorRefNo", "email", "cellPhone", "message", "staffNo", "accountStatus", 0L,0L,"","");
        when(mockFundMasterClient.checkMemberIfExists("identifierValue", "email", "profile")).thenReturn(memberDTO);

        // Configure UserService.createUser(...).
        final User user4 = new User();
        user4.setId(0L);
        user4.setLogin("login");
        user4.setFirstName("firstName");
        user4.setLastName("lastName");
        user4.setPassword("password");
        user4.setEmail("email");
        user4.setActivated(false);
        user4.setActivationKey("activationKey");
        user4.setSecurityCode("securityCode");
        user4.setResetKey("resetKey");
        when(mockUserService.createUser(eq("login"), eq("password"), eq("email"), eq("langKey"), eq("fmIdentifier"), eq(0L), any(MemberDTO.class))).thenReturn(user4);

        // Configure UserRepository.edit(...).
        final User user5 = new User();
        user5.setId(0L);
        user5.setLogin("login");
        user5.setFirstName("firstName");
        user5.setLastName("lastName");
        user5.setPassword("password");
        user5.setEmail("email");
        user5.setActivated(false);
        user5.setActivationKey("activationKey");
        user5.setSecurityCode("securityCode");
        user5.setResetKey("resetKey");
        when(mockUserRepository.edit(any(User.class))).thenReturn(user5);

        // Configure FundMasterClient.getPensionerDetails(...).
        final PensionerDTO pensionerDTO = new PensionerDTO(0L, false, Arrays.asList("value"));
        when(mockFundMasterClient.getPensionerDetails(0L)).thenReturn(pensionerDTO);

        // Run the test
        final Response result = accountControllerImplUnderTest.registerAccount(managedUserVM);

        // Verify the results
        verify(mockMailService).sendActivationEmail(any(User.class));
    }

    @Test
    public void testActivateAccount() {
        // Setup

        // Configure UserService.activateRegistration(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserService.activateRegistration("key")).thenReturn(user);

        // Run the test
        final Response result = accountControllerImplUnderTest.activateAccount("key");

        // Verify the results
    }

    @Test
    public void testActivateAccount_UserServiceReturnsAbsent() {
        // Setup
        when(mockUserService.activateRegistration("key")).thenReturn(Optional.empty());

        // Run the test
        final Response result = accountControllerImplUnderTest.activateAccount("key");

        // Verify the results
    }

    @Test
    public void testIsAuthenticated() {
        // Setup

        // Run the test
        final String result = accountControllerImplUnderTest.isAuthenticated();

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testGetAccount() {
        // Setup

        // Run the test
        final Response result = accountControllerImplUnderTest.getAccount();

        // Verify the results
    }

    @Test
    public void testSaveAccount() {
        // Setup
        final UserDTO userDTO = new UserDTO(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);
        when(mockSecurityHelper.getCurrentUserLogin()).thenReturn("result");

        // Configure UserRepository.findOneByEmail(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByEmail("email")).thenReturn(user);

        // Configure UserRepository.findOneByLogin(...).
        final User user3 = new User();
        user3.setId(0L);
        user3.setLogin("login");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setPassword("password");
        user3.setEmail("email");
        user3.setActivated(false);
        user3.setActivationKey("activationKey");
        user3.setSecurityCode("securityCode");
        user3.setResetKey("resetKey");
        final Optional<User> user2 = Optional.of(user3);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(user2);

        // Run the test
        final Response result = accountControllerImplUnderTest.saveAccount(userDTO);

        // Verify the results
        verify(mockUserService).updateUser("fmIdentifier", "email", "langKey");
    }

    @Test
    public void testSaveAccount_UserRepositoryFindOneByEmailReturnsAbsent() {
        // Setup
        final UserDTO userDTO = new UserDTO(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);
        when(mockSecurityHelper.getCurrentUserLogin()).thenReturn("result");
        when(mockUserRepository.findOneByEmail("email")).thenReturn(Optional.empty());

        // Configure UserRepository.findOneByLogin(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByLogin("login")).thenReturn(user);

        // Run the test
        final Response result = accountControllerImplUnderTest.saveAccount(userDTO);

        // Verify the results
        verify(mockUserService).updateUser("fmIdentifier", "email", "langKey");
    }

    @Test
    public void testSaveAccount_UserRepositoryFindOneByLoginReturnsAbsent() {
        // Setup
        final UserDTO userDTO = new UserDTO(0L, "login", "email", false, "langKey", "fmIdentifier", 0L);
        when(mockSecurityHelper.getCurrentUserLogin()).thenReturn("result");

        // Configure UserRepository.findOneByEmail(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findOneByEmail("email")).thenReturn(user);

        when(mockUserRepository.findOneByLogin("login")).thenReturn(Optional.empty());

        // Run the test
        final Response result = accountControllerImplUnderTest.saveAccount(userDTO);

        // Verify the results
        verify(mockUserService).updateUser("fmIdentifier", "email", "langKey");
    }

    @Test
    public void testChangePassword() {
        // Setup
        final PasswordChangeVM passwordChangeVM = new PasswordChangeVM("login", "currentPassword", "newPassword", "confirmPassword");

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

        // Run the test
        final Response result = accountControllerImplUnderTest.changePassword(passwordChangeVM);

        // Verify the results
        verify(mockUserService).changePassword("login", "currentPassword", "newPassword", true);
    }

    @Test
    public void testRequestPasswordReset() {
        // Setup

        // Configure UserService.requestPasswordReset(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserService.requestPasswordReset("mail")).thenReturn(user);

        // Run the test
        final Response result = accountControllerImplUnderTest.requestPasswordReset("mail");

        // Verify the results
        verify(mockMailService).sendPasswordResetMail(any(User.class));
    }

    @Test
    public void testRequestPasswordReset_UserServiceReturnsAbsent() {
        // Setup
        when(mockUserService.requestPasswordReset("mail")).thenReturn(Optional.empty());

        // Run the test
        final Response result = accountControllerImplUnderTest.requestPasswordReset("mail");

        // Verify the results
        verify(mockMailService).sendPasswordResetMail(any(User.class));
    }

    @Test
    public void testFinishPasswordReset() {
        // Setup
        final KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("key");
        keyAndPassword.setNewPassword("newPassword");

        // Configure UserService.completePasswordReset(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setActivated(false);
        user1.setActivationKey("activationKey");
        user1.setSecurityCode("securityCode");
        user1.setResetKey("resetKey");
        final Optional<User> user = Optional.of(user1);
        when(mockUserService.completePasswordReset("newPassword", "key")).thenReturn(user);

        // Run the test
        final Response result = accountControllerImplUnderTest.finishPasswordReset(keyAndPassword);

        // Verify the results
    }

    @Test
    public void testFinishPasswordReset_UserServiceReturnsAbsent() {
        // Setup
        final KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("key");
        keyAndPassword.setNewPassword("newPassword");

        when(mockUserService.completePasswordReset("newPassword", "key")).thenReturn(Optional.empty());

        // Run the test
        final Response result = accountControllerImplUnderTest.finishPasswordReset(keyAndPassword);

        // Verify the results
    }

    @Test
    public void testCheckIfMemberExists() {
        // Setup

        // Configure FundMasterClient.checkMemberIfExists(...).
        final MemberDTO memberDTO = new MemberDTO("mbshipStatus", "nationalPenNo", "pensionerId", false, "profile",
                0L, 0L, "name", 0L, "sponsorRefNo", "email", "cellPhone", "message", "staffNo", "accountStatus", 0L,0L,"","");
        when(mockFundMasterClient.checkMemberIfExists("identifierValue", "email", "profile")).thenReturn(memberDTO);

        // Run the test
        final Response result = accountControllerImplUnderTest.checkIfMemberExists("identifierValue", "email", "profile");

        // Verify the results
    }

    @Test
    public void testTestEmail() {
        // Setup

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

        // Run the test
        final Response result = accountControllerImplUnderTest.testEmail(0L, "action");

        // Verify the results
        verify(mockMailService).sendActivationEmail(any(User.class));
    }
}
