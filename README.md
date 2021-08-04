# MSS

#Software.
~~~
MYSQL
WILDFLY
Java 11
~~~
##steps
~~~
1. Install mysql and start it as a service
2. Connect to the mysql instance (mysql -u username -p password)
3. Create a database eg mssvision
4. Install wildfly
5. Configure wildfly Java -opts in (/bin/standalone.conf)
6. Configure datasource with jndi name : java:/MySqlDS and database as step 5 above
7. Start wildfly as a service
~~~
#Deployments 

BACKEND - `mssvisionjava`

1. Pull java code using ssh or http github url
   ``https://support.systechafrica.com:8000/root/mssvisionjava.git``
2. Switch to develop branch
3. Run ``mvn clean compile``
4. cd src/main/webapp
5. Create folder `mssvision`

FRONTEND

Requirements
- Install Sencha Cmd v7.3.1.27 or upgrade using sencha upgrade
- Download Sencha Ext packages i.e ext-7.3.1

1. Pull Extjs code using ssh or http github url
2. Checkout develop
3. Run command ``sencha app install ~/pathToExtFramework``
4. Run command ``sencha app build production``
5. Check creation of folder build ie ls
6. Cd ``build/production/MssPhoenix/``

UNDER ONE ROOF PROCEED FROM ABOVE

7. Copy frontend contents to webapp ``cp ./* pathToBackendWebAppFolder/mssvision/ -R``
8. cd java backend folder and run ``mvn clean compile wildfly:deploy``

VOILA!! YOU DONE DEPLOYMENT

##THEMING
<blockquote>
Open File packages/local/coworkee/sass/var/all.scss and locate clients section. Edit or uncomment the color 
variables, save file and build sencha app.<br/>

Open File src/main/webapp/app.css and edit line 35

</blockquote>


##POST-INSTALLATION SQLS
```sql
INSERT INTO config (id, businessImage, businessName, client, country, countryCode, created_date, currencyName, currencyShortName, emailFrom, emailHost, emailPassword, emailPort, emailUsername, two_fa_auth, fmBasePath, fmPassword, fmUsername, middlewarePassword, middlewareUsername, mpesaMiddleWarePath, numTrials, registrationDeclaration, reportServerUrl, statusConfig, allowEmailNotification, allowSmsNotification) VALUES (1, null, 'Systech MSS', 'OTHERS', 'Kenya', '254', '2021-03-01 22:35:56', 'Kenya Shilling', 'Ksh', 'support@mss.com', 'smtp.gmail.com', 'Admin@123', 587, 'support@mss.com', false, 'http://129.159.250.225:8082/Xe/api/', 'Admin@123', 'mssuser', '', '', '', 3, 'I hereby declare that the information provided is true and correct. I also understand that any willful dishonesty may render for refusal of this application.', 'http://129.159.250.225:8888/jinfonet/tryView.jsp?jrs.report=/Xe', 'ACTIVE', true, false);

INSERT INTO landingpagecontent (id, building, businessHours, country, email, fixedPhone, lat, lng, postalAddress, road, secondaryPhone, town, created_date, loginImage, logo, mapLoc, memberIcon, memberMessage, pensionerIcon, pensionerImage, pensionerMessage, statusConfig, welcomeMessage, whySaveMessage) VALUES (1, 'Mayfair', 'Monday - Friday <br/>
7.30AM - 7PM<br/>
Saturday<br/>
8AM-2PM<br/>
Sunday & Holidays<br/>
Closed', 'Kenya', 'test@gma.com', '0752635153', 0, 0, 'Nairobi', 'Parklends', '0831538323', 'Nairobi', '2021-04-22 12:47:52', 5664, 3, '<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d63821.30692629097!2d36.74357818841834!3d-1.2742329120678817!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f171680370193%3A0xf5bbb42d28fa1a88!2sSystech%20Limited!5e0!3m2!1sen!2ske!4v1626264175898!5m2!1sen!2ske" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>', 0, 'Being a member, means you can now access your pension contribution history', 0, 6, 'Being a member, means you can now access your pension contribution history', 'ACTIVE', 'We are the leader with 25 years of experience
in the Pension Administration market!', 'Do not become a burden to your young ones. Save Now, Live free after retirement.');


INSERT INTO profile (id, loginIdentifier, name) VALUES (2, 'EMAIL', 'MEMBER');
INSERT INTO profile (id, loginIdentifier, name) VALUES (3, 'EMAIL', 'SPONSOR');
INSERT INTO profile (id, loginIdentifier, name) VALUES (4, 'PHONE', 'PENSIONER');
INSERT INTO profile (id, loginIdentifier, name) VALUES (5, 'EMAIL', 'CRM');
INSERT INTO profile (id, loginIdentifier, name) VALUES (6, 'EMAIL', 'ADMIN');
INSERT INTO profile (id, loginIdentifier, name) VALUES (7, 'NSSN', 'CRE');
INSERT INTO profile (id, loginIdentifier, name) VALUES (8, 'EMAIL', 'PRINCIPAL OFFICER');

INSERT INTO securityconfig (id, created_date, issuer, tokenValidityMillis, tokenValidityMillisForRememberMe) VALUES (1, '2021-03-03 08:27:18.000000', 'com.systech', 86400, 1314000);

INSERT INTO mailconfig (id, smtp_base_utl, enableTLS, active, smtp_email_from, smtp_host, mailType, smtp_password, smtp_port, supportEmail, smtp_username) VALUES (2, 'http://127.0.0.1:8080/mss', true, false, 'bursting.reports@gmail.com', 'smtp.gmail.com', 'TLS', 'Bursting@123', 587, 'aviatoryona67@gmail.com', 'bursting.reports@gmail.com');
INSERT INTO mailconfig (id, smtp_base_utl, enableTLS, active, smtp_email_from, smtp_host, mailType, smtp_password, smtp_port, supportEmail, smtp_username) VALUES (6969, 'http://localhost:8080', false, true, 'aviatoryona@gmail.com', 'smtp.gmail.com', 'TLS', '19051995Yk', 587, 'aviatoryona@gmail.com', 'aviatoryona@gmail.com');

INSERT INTO mobilemoneyconfig (id, accountReference, callbackUrl, isLive, minAmount, mobileMoneyProcedure, mpesaAppKey, mpesaAppSecret, mpesaPassKey, mpesaPaybill, status, timeoutUrl) VALUES (1, 'Systech Scheme', 'http://129.159.250.225:8085/mss/resources/api/mpesaCallBack', false, 1, '1.Go to the M-pesa Menu.<br>                                            2.Select Pay Bill.<br>                                            3.Enter Business No. <span><b>174379</b></span>.<br>                                            4.Enter Account No.<span><b>XXXXXX</b></span> (Where XXXXXX is  <b>Member number</b>)<br>                                            5.Enter the Amount.<br>                                            6.Enter your M-Pesa PIN then send. ', 'UrKWUKv4UXEsGdlM7szaJvcDAZ1OrEVN', 'EmAahKmMXZtkKX20', 'bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919', '174379', 'ACTIVE', 'http://129.159.250.225:8085/mss/resources/api/mpesaTimeoutCallBack');

INSERT INTO users (id, activated, activation_key, email, first_name, fm_identifier, lang_key, last_name, login, password_hash, reset_date, reset_key, security_code, accountStatus, cellPhone, mbshipStatus, memberId, name, nationalPenNo, pensionerId, profile, schemeId, sponsorId, sponsorRefNo, staffNo, userId, profile_id, lockedStatus, numTrials, flg_firsttime, deactivatedByAdmin, created_date, photo, authenticationTrials, approvedByCrm, userStatus, parentSponsorEmail) VALUES (1, true, '63577997585887771305', 'admin@mss.com', 'mssuser', 'ADMIN', 'en', 'mssuser', 'admin', '0e7517141fb53f21ee439b355b5a1d0a', null, null, null, 'Active', '0790909090', '0', 0, 'mssuser mssuser', '', null, 'ADMINISTRATOR', 6603, 0, '0', '', 28272, 6, false, 0, 'false', false, '2021-06-08 13:19:34.000000', 'File_20210702144356rsz_sample_logo.png', 0, false, 'ACTIVE', null);

INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (1, '2021-05-12 16:22:09', 'name,username,password,portalLink', '<p>Dear <b>#name</b>,<br>
<br>
<br>
We have the pleasure to inform you that we have created Online Portal Credentials to enable you access both individual
Member Details for your <b>Pension Account and Scheme Wide information</b> for the whole membership.<br>
<br>
Your credentials are as follows:<br>
<br>
User ID: <b>#username</b><br>
Password: <b>#password</b><br>
<br>
The portal can be accessed on the following website:<br>
<br><b>
#portalLink</b><br>
<br>
User Guides are available once you log in to help you in navigating the Portal for both Individual Member Details
(Pension Fund Tab) and the whole membership (Principal Officer Tab).<br>
<br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'ACCOUNT_ACTIVATION', 'Mss Account Activation', 'Dear #name,
We have the pleasure to inform you that we have created Online Portal Credentials to enable you access both individual Member Details for your Pension Account and Scheme Wide information for the whole membership.
Your credentials are as follows:
User ID: #username
Password: #password
The portal can be accessed on the following website:
#portalLink');
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (13647, '2021-05-21 08:45:20.280000', 'name,benefitNumber,change,portalLink,', '<p>Dear <b>#name</b></p><p>Your recently initiated claim of id <b>#benefitNumber</b> has been <b>#change</b>.&nbsp; You will be further notified on any other changes.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'CLAIM_STATUS', 'Your claim has an update', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (13690, '2021-05-21 10:23:01.436000', 'name,ticketNumber,portalLink,', '<p>Dear <b>#name</b></p><p>Your Ticket has been successfully raised. You can follow up you ticket through ticket number <b>#ticketNumber</b>. </p><p>You will be notified on any actions on your ticket via email.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'TICKET_RAISED', 'Ticket Raised', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (13691, '2021-05-21 10:28:48.073000', 'name,ticketNumber,message,replyBy,timeReplied,portalLink,', '
                                                    <p>Dear #name</p><p>Your Ticket of ticket number&nbsp;&nbsp;<span style="font-weight: bolder;">#ticketNumber </span>has <span style="color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 700; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;">&nbsp;</span><span style="color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;">a new reply from <b>#replyBy</b> at <b>#timeReplied</b> .</span></p><blockquote class="blockquote"><h3><span style="orphans: 2; text-align: left; text-indent: 0px; widows: 2; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;"><span style="color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; text-transform: none; white-space: normal; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);">The reply:&nbsp;<b>&nbsp;</b></span><b>#message</b><span style="color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; text-transform: none; white-space: normal; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);"></span></span></h3></blockquote><p>You will be notified on any actions on your ticket via email.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>
                                                ', 'TICKET_REPLY', 'Your Ticket has a new reply', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (18681, '2021-06-02 11:53:37.975000', 'name,username,password,portalLink,', '<p>
                                                    </p><p>Dear&nbsp;<span style="font-weight: bolder;">#name</span>,</p>We have the pleasure to inform you that we have created Online Portal Credentials to enable you access both individual Member Details for your&nbsp;<span style="font-weight: bolder;">Pension Account and Scheme Wide information</span>&nbsp;for the whole membership.<br><br>Your credentials are as follows:<br><br>User ID:&nbsp;<span style="font-weight: bolder;">#username</span><br>Password:&nbsp;<span style="font-weight: bolder;">#password</span><br><br>The portal can be accessed on the following website:<br><br><span style="font-weight: bolder;">#portalLink</span><p></p><p><span style="font-weight: bolder;"></span>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span>
                                                </p>', 'MEMBER_ACCOUNT_ACTIVATION', 'Account Activation', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22755, '2021-06-21 06:22:03.589000', 'name,memberName,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>A member has requested to join <b>#scheme</b> under <b>#sponsor</b>.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'PO_NEW_MEMBER_APPROVAL_REQUEST', 'Pending New Member', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22756, '2021-06-21 06:23:27.511000', 'name,portalLink,', '<p><p>Dear&nbsp;<span style="font-weight: bolder;">#name</span></p>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style="color: rgb(41, 82, 24);">APPROVED</span></p><p><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'NEW_MEMBER_APPROVAL', 'New Member Request Approval', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22757, '2021-06-21 06:23:50.157000', 'name,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style="color: rgb(156, 0, 0);">DECLINED</span></p><p><span style="color: rgb(156, 0, 0);"></span>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'NEW_MEMBER_DECLINE', 'New Member Request ', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22758, '2021-06-21 06:24:45.132000', 'name,portalLink,', '<p>Dear&nbsp;<span style="font-weight: bolder;">#name</span></p><p>Your Request to update bio data has been approved.</p><p>#portalLink</p><p>If you have any queries, please contact us on&nbsp;<a href="http://nlcustomerportal@nico-life.com/" target="_blank">nlcustomerportal@nico-life.com</a>&nbsp;or call 265 1 822 699 to get assistance</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'MEMBER_BIO_DATA_UPDATE_APPROVAL', 'Bio Data Update', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22863, '2021-06-21 21:22:35.256000', 'name,url,', '<p>Dear <b>#name;</b></p><p>You recently requested for password reset for your Self Service Account, please click on the URL below to reset it or ignore if it wasn''t you.</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>#url</b><br></p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842', 'REQUEST_PASSWORD_RESET', 'Forgot Password', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (22864, '2021-06-21 21:25:59.678000', 'name,username,password,portalLink,', '<p><p>Dear <b>#name;</b></p><p><b><br></b>You recently requested for 
password reset for your Self Service Account, please click on the URL 
below to reset it or ignore if it wasn''t you.</p><p>Username : &nbsp;&nbsp;&nbsp;&nbsp;<b>#username<br></b>password :&nbsp;&nbsp;&nbsp;&nbsp; <b>#password<br></b>Visit <b><small id="namedKeys"><b>#portalLink</b></small></b><br></p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com/" target="_blank">https://systechafrica.com/</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'PASSWORD_RESET', 'Password Reset', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (24252, '2021-07-03 22:58:45.721000', 'name,username,password,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>
Your credentials are as follows:<br>
<br>
User ID: <b>#username</b><br>
Password: <b>#password</b><br>
<br>
The portal can be accessed on the following website:<br>
<br><b>
#portalLink<br></b><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p><p><br></p>', 'ADMIN_ACCOUNT_ACTIVATION', 'Admin Account Activation', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (24253, '2021-07-03 22:59:15.898000', 'name,username,password,portalLink,', 'Dear <b>#name</b>,<br>
<br>
Your credentials are as follows:<br>
<br>
User ID: <b>#username</b><br>
Password: <b>#password</b><br>
<br>
The portal can be accessed on the following website:<br>
<br><b>
#portalLink<br></b><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span>', 'PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION', 'Principal Officer Account Activation', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (27422, '2021-07-26 01:39:21.930000', 'name,token,', '<h3>OTP : <span style="font-weight: bold;">#token</span></h3><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'OTP_VERIFICATION', 'OTP Verification', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29023, '2021-07-30 06:04:55.767000', 'name,memberName,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>New Claim brought by <b><span style="font-family: &quot;Arial&quot;;">#memberName</span></b><br>
<br>
The portal can be accessed on the following website:<br>
<br>
#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'CLAIM_INITIATED', 'New Pending Claim', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29024, '2021-07-30 06:05:21.661000', 'name,benefitNumber,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Claim Initiated<br>
<br>
Tracking ID : <b>#benefitNumber</b><br><br>
The portal can be accessed on the following website:<br>
<br>
#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'MEMBER_CLAIM_INITIATED', 'Member Claim Initiated', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29025, '2021-07-30 06:06:14.074000', 'name,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Beneficiary details <b>approved</b><br>
<br>
The portal can be accessed on the following website:<br>
<br>
#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'MEMBER_BENEFICIARY_APPROVAL', 'Member Beneficiary Approval', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29026, '2021-07-30 06:06:41.340000', 'name,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Beneficiary details <b>DECLINED</b><br>
<br>
The portal can be accessed on the following website:<br>
<br>
#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'MEMBER_BENEFICIARY_DECLINE', 'Member Beneficiary Decline', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29027, '2021-07-30 06:07:20.040000', 'name,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Your details have been&nbsp; <b>DECLINED</b><br>
<br>
The portal can be accessed on the following website:<br>
<br>
#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: <span><span class="LrzXr zdqRlf kno-fv"><span data-dtype="d3ifr" data-local-attribute="d3ph"><span>0723 847842</span></span></span></span></p>', 'MEMBER_BIO_DATA_UPDATE_DECLINE', 'Member Bio Data Decline', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29028, '2021-07-30 06:07:42.020000', 'name,scheme,sponsor,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style="color: rgb(0, 0, 255);"><b>RECEIVED<br></b></span><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'NEW_MEMBER_REGISTERED', 'New Member Registered', 'Dear #name,
Your request to join #scheme under #sponsor has been  RECEIVED');
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29029, '2021-07-30 06:33:35.415000', 'name,memberName,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>A member <span style="font-weight: bold;">#memberName</span> has&nbsp; requested to update beneficiary details.</p><p>#portalLink</p><p><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST', 'Principal Officer Beneficiary Approval', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29030, '2021-07-30 06:37:54.647000', 'name,memberName,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>A member <span style="font-weight: bold;">#memberName</span> has&nbsp; requested to update&nbsp; details.</p><p>#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'PO_MEMBER_BIO_DATA_APPROVAL_REQUEST', 'Principal Officer Member Bio-Data Approval', null);
INSERT INTO emailtemplate (id, createdAt, namedKeys, template, templatesType, title, smsTemplate) VALUES (29033, '2021-07-30 06:38:23.496000', 'name,memberName,portalLink,', '<p>Dear <b>#name</b>,<br>
<br>A member <span style="font-weight: bold;">#memberName</span> has initiated a claim.</p><p>#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href="https://systechafrica.com" target="_blank">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>', 'PO_PENDING_CLAIM', 'Principal Officer Pending Claim', null);
```
##MORE

http://localhost:8080/mss/resources/api/authenticate
headers:{
    ContetType:'application/json'
}

{
  "username": "user",
  "password":"user",
  "rememberMe":false
}

http://localhost:8080/mss/resources/api/users
headers:{
    ContetType:'application/json',
    Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzYmU3YWUzZi0wYmM0LTRkNTMtYWM2My1iNjNlYzAzYjVlZjYiLCJzdWIiOiJ1c2VyIiwiaXNzIjoiY29tLnN5c3RlY2gubXNzIiwiaWF0IjoxNjE0NzA4OTkyLCJleHAiOjE2MTQ3OTUzOTJ9.4sFkXjHa5Q-WQc8ZTgaW7NfjCWgpqVdV8yhGjiLZdpA
}

{
  "password": "",
  "id": 0,
  "login": "",
  "email": "",
  "activated": false,
  "langKey": "",
  "fmIdentifier": "",
  "profileId": 1
}