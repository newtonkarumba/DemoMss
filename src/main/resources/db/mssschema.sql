-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: mss
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Documents`
--

DROP TABLE IF EXISTS `Documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Documents` (
  `id` bigint NOT NULL,
  `comments` longtext,
  `createdAt` datetime(6) DEFAULT NULL,
  `expiryDate` datetime(6) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` longtext,
  `fileSize` bigint DEFAULT NULL,
  `forProfileId` bigint DEFAULT NULL,
  `originalFileName` varchar(255) DEFAULT NULL,
  `toUserId` bigint DEFAULT NULL,
  `fromUserId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfx6imhs3g54h9a0u9h2bg7ulp` (`fromUserId`),
  CONSTRAINT `FKfx6imhs3g54h9a0u9h2bg7ulp` FOREIGN KEY (`fromUserId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Documents`
--

LOCK TABLES `Documents` WRITE;
/*!40000 ALTER TABLE `Documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `Documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MemberSubmittedDocs`
--

DROP TABLE IF EXISTS `MemberSubmittedDocs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MemberSubmittedDocs` (
  `id` bigint NOT NULL,
  `docName` varchar(255) DEFAULT NULL,
  `docNotes` varchar(255) DEFAULT NULL,
  `docNum` varchar(255) DEFAULT NULL,
  `docTypeId` varchar(255) DEFAULT NULL,
  `isApproved` bit(1) DEFAULT NULL,
  `memberId` bigint DEFAULT NULL,
  `documentId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkongms08lpdsj7ne8p57ded2b` (`documentId`),
  CONSTRAINT `FKkongms08lpdsj7ne8p57ded2b` FOREIGN KEY (`documentId`) REFERENCES `Documents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MemberSubmittedDocs`
--

LOCK TABLES `MemberSubmittedDocs` WRITE;
/*!40000 ALTER TABLE `MemberSubmittedDocs` DISABLE KEYS */;
/*!40000 ALTER TABLE `MemberSubmittedDocs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SelfiePayment`
--

DROP TABLE IF EXISTS `SelfiePayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SelfiePayment` (
  `id` bigint NOT NULL,
  `MerchantRequestID` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `mpesaPhoneNumber` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `paybill` varchar(255) DEFAULT NULL,
  `paymentStatus` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `requestId` varchar(255) DEFAULT NULL,
  `resultMessage` longtext,
  `timestamp` varchar(255) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SelfiePayment`
--

LOCK TABLES `SelfiePayment` WRITE;
/*!40000 ALTER TABLE `SelfiePayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `SelfiePayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activitytrail`
--

DROP TABLE IF EXISTS `activitytrail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activitytrail` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `description` longtext,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitytrail`
--

LOCK TABLES `activitytrail` WRITE;
/*!40000 ALTER TABLE `activitytrail` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitytrail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beneficiaryFormConfig`
--

DROP TABLE IF EXISTS `beneficiaryFormConfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `beneficiaryFormConfig` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bankNameHidden` bit(1) DEFAULT NULL,
  `bankNameReadOnly` bit(1) DEFAULT NULL,
  `benAccountNameHidden` bit(1) DEFAULT NULL,
  `benAccountNameReadOnly` bit(1) DEFAULT NULL,
  `benAccountNoHidden` bit(1) DEFAULT NULL,
  `benAccountNoReadOnly` bit(1) DEFAULT NULL,
  `benAddressPostalAddressHidden` bit(1) DEFAULT NULL,
  `benAddressPostalAddressReadOnly` bit(1) DEFAULT NULL,
  `benBankBranchIdHidden` bit(1) DEFAULT NULL,
  `benBankBranchIdReadOnly` bit(1) DEFAULT NULL,
  `benCellPhoneHidden` bit(1) DEFAULT NULL,
  `benCellPhoneReadOnly` bit(1) DEFAULT NULL,
  `benDateOfAppointmentHidden` bit(1) DEFAULT NULL,
  `benDateOfAppointmentReadOnly` bit(1) DEFAULT NULL,
  `benDobHidden` bit(1) DEFAULT NULL,
  `benDobReadOnly` bit(1) DEFAULT NULL,
  `benFirstnameHidden` bit(1) DEFAULT NULL,
  `benFirstnameReadOnly` bit(1) DEFAULT NULL,
  `benGenderHidden` bit(1) DEFAULT NULL,
  `benGenderReadOnly` bit(1) DEFAULT NULL,
  `benGuardianAddrHidden` bit(1) DEFAULT NULL,
  `benGuardianAddrReadOnly` bit(1) DEFAULT NULL,
  `benGuardianCountryHidden` bit(1) DEFAULT NULL,
  `benGuardianCountryReadOnly` bit(1) DEFAULT NULL,
  `benGuardianEmailHidden` bit(1) DEFAULT NULL,
  `benGuardianEmailReadOnly` bit(1) DEFAULT NULL,
  `benGuardianFnHidden` bit(1) DEFAULT NULL,
  `benGuardianFnReadOnly` bit(1) DEFAULT NULL,
  `benGuardianGenderHidden` bit(1) DEFAULT NULL,
  `benGuardianGenderReadOnly` bit(1) DEFAULT NULL,
  `benGuardianOnHidden` bit(1) DEFAULT NULL,
  `benGuardianOnReadOnly` bit(1) DEFAULT NULL,
  `benGuardianRelationHidden` bit(1) DEFAULT NULL,
  `benGuardianRelationReadOnly` bit(1) DEFAULT NULL,
  `benGuardianSnHidden` bit(1) DEFAULT NULL,
  `benGuardianSnReadOnly` bit(1) DEFAULT NULL,
  `benGuardianTownHidden` bit(1) DEFAULT NULL,
  `benGuardianTownReadOnly` bit(1) DEFAULT NULL,
  `benIdNoHidden` bit(1) DEFAULT NULL,
  `benIdNoReadOnly` bit(1) DEFAULT NULL,
  `benLumpsumEntitlementHidden` bit(1) DEFAULT NULL,
  `benLumpsumEntitlementReadOnly` bit(1) DEFAULT NULL,
  `benMaidenNameHidden` bit(1) DEFAULT NULL,
  `benMaidenNameReadOnly` bit(1) DEFAULT NULL,
  `benMonthlyEntitlementHidden` bit(1) DEFAULT NULL,
  `benMonthlyEntitlementReadOnly` bit(1) DEFAULT NULL,
  `benNIdHidden` bit(1) DEFAULT NULL,
  `benNIdReadOnly` bit(1) DEFAULT NULL,
  `benNationalityHidden` bit(1) DEFAULT NULL,
  `benNationalityReadOnly` bit(1) DEFAULT NULL,
  `benOthernamesHidden` bit(1) DEFAULT NULL,
  `benOthernamesReadOnly` bit(1) DEFAULT NULL,
  `benRelationShipCategoryHidden` bit(1) DEFAULT NULL,
  `benRelationShipCategoryReadOnly` bit(1) DEFAULT NULL,
  `benRelationshipTypeHidden` bit(1) DEFAULT NULL,
  `benRelationshipTypeReadOnly` bit(1) DEFAULT NULL,
  `benSurnameHidden` bit(1) DEFAULT NULL,
  `benSurnameReadOnly` bit(1) DEFAULT NULL,
  `fileHidden` bit(1) DEFAULT NULL,
  `fileReadOnly` bit(1) DEFAULT NULL,
  `permanentDistrictIdHidden` bit(1) DEFAULT NULL,
  `permanentDistrictIdReadOnly` bit(1) DEFAULT NULL,
  `permanentTAIdHidden` bit(1) DEFAULT NULL,
  `permanentTAIdReadOnly` bit(1) DEFAULT NULL,
  `permanentVillageIdHidden` bit(1) DEFAULT NULL,
  `permanentVillageIdReadOnly` bit(1) DEFAULT NULL,
  `placeOfBirthDistrictIdHidden` bit(1) DEFAULT NULL,
  `placeOfBirthDistrictIdReadOnly` bit(1) DEFAULT NULL,
  `placeOfBirthTAIdHidden` bit(1) DEFAULT NULL,
  `placeOfBirthTAIdReadOnly` bit(1) DEFAULT NULL,
  `placeOfBirthVillageIdHidden` bit(1) DEFAULT NULL,
  `placeOfBirthVillageIdReadOnly` bit(1) DEFAULT NULL,
  `saveBeneficiaryHidden` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiaryFormConfig`
--

LOCK TABLES `beneficiaryFormConfig` WRITE;
/*!40000 ALTER TABLE `beneficiaryFormConfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `beneficiaryFormConfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benefitrequest`
--

DROP TABLE IF EXISTS `benefitrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benefitrequest` (
  `id` bigint NOT NULL,
  `CRMDeclineReason` longtext,
  `accountName` varchar(255) DEFAULT NULL,
  `accountNumber` varchar(255) DEFAULT NULL,
  `amountPercentage` double DEFAULT NULL,
  `approved` bit(1) DEFAULT NULL,
  `authorize` bit(1) DEFAULT NULL,
  `authorizeByCrm` bit(1) DEFAULT NULL,
  `bankBranchName` varchar(255) DEFAULT NULL,
  `bankName` varchar(255) DEFAULT NULL,
  `benefitReason` varchar(255) DEFAULT NULL,
  `benefitReasonId` bigint DEFAULT NULL,
  `certify` bit(1) DEFAULT NULL,
  `dateApproved` datetime(6) DEFAULT NULL,
  `dateApprovedByCrm` datetime(6) DEFAULT NULL,
  `dateAuthorize` datetime(6) DEFAULT NULL,
  `dateCertified` datetime(6) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `dateDeclined` datetime(6) DEFAULT NULL,
  `declineReason` longtext,
  `declined` bit(1) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `documentsUploaded` bit(1) DEFAULT NULL,
  `earlyRetirementOption` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employeeOutstandingLoan` varchar(255) DEFAULT NULL,
  `employeeOutstandingLoanAmount` varchar(255) DEFAULT NULL,
  `employerHasEdited` bit(1) DEFAULT NULL,
  `hasMortagageOrNo` varchar(255) DEFAULT NULL,
  `approved_by_member` varchar(255) DEFAULT NULL,
  `is_edited` varchar(255) DEFAULT NULL,
  `isPercentageOrAmount` varchar(255) DEFAULT NULL,
  `posted_to_fm` varchar(255) DEFAULT NULL,
  `medicalExplanation` longtext,
  `medicalIssue` varchar(255) DEFAULT NULL,
  `memberId` bigint DEFAULT NULL,
  `memberNo` varchar(255) DEFAULT NULL,
  `mortageLoan` decimal(19,2) DEFAULT NULL,
  `mssUserId` bigint DEFAULT NULL,
  `overseasAddress` varchar(255) DEFAULT NULL,
  `overseasBank` varchar(255) DEFAULT NULL,
  `overseasCountry` varchar(255) DEFAULT NULL,
  `overseasEmail` varchar(255) DEFAULT NULL,
  `overseasFax` varchar(255) DEFAULT NULL,
  `overseasIbanDetails` varchar(255) DEFAULT NULL,
  `overseasSwiftCode` varchar(255) DEFAULT NULL,
  `overseasTelephone` varchar(255) DEFAULT NULL,
  `paymentOption` varchar(255) DEFAULT NULL,
  `requiresDocuments` bit(1) DEFAULT NULL,
  `schemeId` bigint DEFAULT NULL,
  `sendToXi` bit(1) DEFAULT NULL,
  `sponsorDeclineReason` longtext,
  `sponsorId` bigint DEFAULT NULL,
  `staffContribution` varchar(255) DEFAULT NULL,
  `staffPercentagePerVestingRule` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submitted` varchar(255) DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  `approvedById` bigint DEFAULT NULL,
  `authorizedById` bigint DEFAULT NULL,
  `certifiedById` bigint DEFAULT NULL,
  `authorizedBycrmId` bigint DEFAULT NULL,
  `declinedById` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaue3ht74en0b9vgdwmjdris5e` (`approvedById`),
  KEY `FKonxlg6rc9i3yoihr8jp9f5kyc` (`authorizedById`),
  KEY `FK9q02hro4ajhe2e5ugq3tjcv7j` (`certifiedById`),
  KEY `FK9dsvdcdxtmh9e3r50725odo81` (`authorizedBycrmId`),
  KEY `FKev3s6mjrybocrk6890xwvbjfg` (`declinedById`),
  CONSTRAINT `FK9dsvdcdxtmh9e3r50725odo81` FOREIGN KEY (`authorizedBycrmId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK9q02hro4ajhe2e5ugq3tjcv7j` FOREIGN KEY (`certifiedById`) REFERENCES `users` (`id`),
  CONSTRAINT `FKaue3ht74en0b9vgdwmjdris5e` FOREIGN KEY (`approvedById`) REFERENCES `users` (`id`),
  CONSTRAINT `FKev3s6mjrybocrk6890xwvbjfg` FOREIGN KEY (`declinedById`) REFERENCES `users` (`id`),
  CONSTRAINT `FKonxlg6rc9i3yoihr8jp9f5kyc` FOREIGN KEY (`authorizedById`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benefitrequest`
--

LOCK TABLES `benefitrequest` WRITE;
/*!40000 ALTER TABLE `benefitrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `benefitrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billingofficerperms`
--

DROP TABLE IF EXISTS `billingofficerperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billingofficerperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `benefitsMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `sponsorConfigMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `tpfaMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billingofficerperms`
--

LOCK TABLES `billingofficerperms` WRITE;
/*!40000 ALTER TABLE `billingofficerperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `billingofficerperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `id` bigint NOT NULL,
  `bookingDate` varchar(255) DEFAULT NULL,
  `dueDate` varchar(255) DEFAULT NULL,
  `month` varchar(255) DEFAULT NULL,
  `year` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `broadcastinbox`
--

DROP TABLE IF EXISTS `broadcastinbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcastinbox` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdAt` datetime(6) DEFAULT NULL,
  `documents` longtext,
  `fromId` bigint DEFAULT NULL,
  `fromName` varchar(255) DEFAULT NULL,
  `isRead` bit(1) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `message` longtext,
  `receiversOutbox` longtext,
  `subject` varchar(255) DEFAULT NULL,
  `toId` bigint DEFAULT NULL,
  `toName` varchar(255) DEFAULT NULL,
  `updatedAt` datetime(6) DEFAULT NULL,
  `fromprofile_id` bigint NOT NULL,
  `toprofile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaq42qhwcpisx0gpwrfmqwuc81` (`fromprofile_id`),
  KEY `FKqyn1yfbjwvca9x3r4755ruv8b` (`toprofile_id`),
  CONSTRAINT `FKaq42qhwcpisx0gpwrfmqwuc81` FOREIGN KEY (`fromprofile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKqyn1yfbjwvca9x3r4755ruv8b` FOREIGN KEY (`toprofile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `broadcastinbox`
--

LOCK TABLES `broadcastinbox` WRITE;
/*!40000 ALTER TABLE `broadcastinbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `broadcastinbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `broadcastoutbox`
--

DROP TABLE IF EXISTS `broadcastoutbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcastoutbox` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdAt` datetime(6) DEFAULT NULL,
  `documents` longtext,
  `fromId` bigint DEFAULT NULL,
  `fromName` varchar(255) DEFAULT NULL,
  `isRead` bit(1) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `message` longtext,
  `receiversOutbox` longtext,
  `subject` varchar(255) DEFAULT NULL,
  `toId` bigint DEFAULT NULL,
  `toName` varchar(255) DEFAULT NULL,
  `updatedAt` datetime(6) DEFAULT NULL,
  `fromprofile_id` bigint NOT NULL,
  `toprofile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrkptecj8gugh8gwnc2suc36qw` (`fromprofile_id`),
  KEY `FK3ff5ghrs7smvvsk83w3v7sxgr` (`toprofile_id`),
  CONSTRAINT `FK3ff5ghrs7smvvsk83w3v7sxgr` FOREIGN KEY (`toprofile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKrkptecj8gugh8gwnc2suc36qw` FOREIGN KEY (`fromprofile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `broadcastoutbox`
--

LOCK TABLES `broadcastoutbox` WRITE;
/*!40000 ALTER TABLE `broadcastoutbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `broadcastoutbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claimdocuments`
--

DROP TABLE IF EXISTS `claimdocuments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claimdocuments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `benefitRequestId` bigint DEFAULT NULL,
  `checkListName` varchar(255) NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `documentId` bigint DEFAULT NULL,
  `reasonForExit` varchar(255) DEFAULT NULL,
  `uploaded` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claimdocuments`
--

LOCK TABLES `claimdocuments` WRITE;
/*!40000 ALTER TABLE `claimdocuments` DISABLE KEYS */;
/*!40000 ALTER TABLE `claimdocuments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claimsauthorizerperms`
--

DROP TABLE IF EXISTS `claimsauthorizerperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claimsauthorizerperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `benefitsMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `sponsorConfigMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `tpfaMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claimsauthorizerperms`
--

LOCK TABLES `claimsauthorizerperms` WRITE;
/*!40000 ALTER TABLE `claimsauthorizerperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `claimsauthorizerperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claimsofficerperms`
--

DROP TABLE IF EXISTS `claimsofficerperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claimsofficerperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `benefitsMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `sponsorConfigMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `tpfaMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claimsofficerperms`
--

LOCK TABLES `claimsofficerperms` WRITE;
/*!40000 ALTER TABLE `claimsofficerperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `claimsofficerperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claimsreviewerperms`
--

DROP TABLE IF EXISTS `claimsreviewerperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claimsreviewerperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `benefitsMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `sponsorConfigMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `tpfaMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claimsreviewerperms`
--

LOCK TABLES `claimsreviewerperms` WRITE;
/*!40000 ALTER TABLE `claimsreviewerperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `claimsreviewerperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config` (
  `id` bigint NOT NULL,
  `businessImage` varchar(255) DEFAULT NULL,
  `businessName` varchar(255) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `countryCode` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `currencyName` varchar(255) DEFAULT NULL,
  `currencyShortName` varchar(255) DEFAULT NULL,
  `emailFrom` varchar(255) DEFAULT NULL,
  `emailHost` varchar(255) DEFAULT NULL,
  `emailPassword` varchar(255) DEFAULT NULL,
  `emailPort` int DEFAULT NULL,
  `emailUsername` varchar(255) DEFAULT NULL,
  `two_fa_auth` bit(1) DEFAULT NULL,
  `fmBasePath` varchar(255) NOT NULL,
  `fmPassword` varchar(255) NOT NULL,
  `fmUsername` varchar(255) NOT NULL,
  `middlewarePassword` varchar(255) DEFAULT NULL,
  `middlewareUsername` varchar(255) DEFAULT NULL,
  `mpesaMiddleWarePath` varchar(255) DEFAULT NULL,
  `numTrials` int DEFAULT NULL,
  `registrationDeclaration` varchar(255) DEFAULT NULL,
  `reportServerUrl` varchar(255) DEFAULT NULL,
  `statusConfig` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,NULL,'Systech MSS','OTHERS','Kenya','254','2021-03-01 22:35:56.000000','Kenya Shilling','Ksh','support@mss.com','smtp.gmail.com','Admin@123',587,'support@mss.com',_binary '\0','http://129.159.250.225:8082/Xe/api/','Admin@123','mssuser','','','',3,'I hereby declare that the information provided is true and correct. I also understand that any willful dishonesty may render for refusal of this application.','http://129.159.250.225:8888/jinfonet/tryView.jsp?jrs.report=/Xe','ACTIVE');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countryCode`
--

DROP TABLE IF EXISTS `countryCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countryCode` (
  `id` bigint NOT NULL,
  `country` varchar(255) NOT NULL,
  `countryCode` varchar(255) NOT NULL,
  `statusConfig` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countryCode`
--

LOCK TABLES `countryCode` WRITE;
/*!40000 ALTER TABLE `countryCode` DISABLE KEYS */;
/*!40000 ALTER TABLE `countryCode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creperms`
--

DROP TABLE IF EXISTS `creperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowInitiateClaims` bit(1) DEFAULT NULL,
  `allowUploadDocs` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `schemesMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creperms`
--

LOCK TABLES `creperms` WRITE;
/*!40000 ALTER TABLE `creperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `creperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmperms`
--

DROP TABLE IF EXISTS `crmperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crmperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `approveClaimAuthorizerActivated` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `sponsorMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmperms`
--

LOCK TABLES `crmperms` WRITE;
/*!40000 ALTER TABLE `crmperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `date_values`
--

DROP TABLE IF EXISTS `date_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `date_values` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dateNumber` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date_values`
--

LOCK TABLES `date_values` WRITE;
/*!40000 ALTER TABLE `date_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `date_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailtemplate`
--

DROP TABLE IF EXISTS `emailtemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emailtemplate` (
  `id` bigint NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `namedKeys` varchar(255) DEFAULT NULL,
  `template` longtext,
  `templatesType` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_srx4x1cxannym7ip6o1e0yg3c` (`templatesType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emailtemplate`
--

LOCK TABLES `emailtemplate` WRITE;
/*!40000 ALTER TABLE `emailtemplate` DISABLE KEYS */;
INSERT INTO `emailtemplate` VALUES (1,'2021-05-12 16:22:09.000000','name,username,password,portalLink','<p>Dear <b>#name</b>,<br>\n<br>\n<br>\nWe have the pleasure to inform you that we have created Online Portal Credentials to enable you access both individual\nMember Details for your <b>Pension Account and Scheme Wide information</b> for the whole membership.<br>\n<br>\nYour credentials are as follows:<br>\n<br>\nUser ID: <b>#username</b><br>\nPassword: <b>#password</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br><b>\n#portalLink</b><br>\n<br>\nUser Guides are available once you log in to help you in navigating the Portal for both Individual Member Details\n(Pension Fund Tab) and the whole membership (Principal Officer Tab).<br>\n<br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','ACCOUNT_ACTIVATION','Mss Account Activation'),(13647,'2021-05-21 08:45:20.280000','name,benefitNumber,change,portalLink,','<p>Dear <b>#name</b></p><p>Your recently initiated claim of id <b>#benefitNumber</b> has been <b>#change</b>.&nbsp; You will be further notified on any other changes.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','CLAIM_STATUS','Your claim has an update'),(13690,'2021-05-21 10:23:01.436000','name,ticketNumber,portalLink,','<p>Dear <b>#name</b></p><p>Your Ticket has been successfully raised. You can follow up you ticket through ticket number <b>#ticketNumber</b>. </p><p>You will be notified on any actions on your ticket via email.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','TICKET_RAISED','Ticket Raised'),(13691,'2021-05-21 10:28:48.073000','name,ticketNumber,message,replyBy,timeReplied,portalLink,','\n                                                    <p>Dear #name</p><p>Your Ticket of ticket number&nbsp;&nbsp;<span style=\"font-weight: bolder;\">#ticketNumber </span>has <span style=\"color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 700; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">&nbsp;</span><span style=\"color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\">a new reply from <b>#replyBy</b> at <b>#timeReplied</b> .</span></p><blockquote class=\"blockquote\"><h3><span style=\"orphans: 2; text-align: left; text-indent: 0px; widows: 2; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\"><span style=\"color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; text-transform: none; white-space: normal; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\">The reply:&nbsp;<b>&nbsp;</b></span><b>#message</b><span style=\"color: rgb(103, 106, 108); font-family: &quot;open sans&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; text-transform: none; white-space: normal; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"></span></span></h3></blockquote><p>You will be notified on any actions on your ticket via email.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>\n                                                ','TICKET_REPLY','Your Ticket has a new reply'),(18681,'2021-06-02 11:53:37.975000','name,username,password,portalLink,','<p>\n                                                    </p><p>Dear&nbsp;<span style=\"font-weight: bolder;\">#name</span>,</p>We have the pleasure to inform you that we have created Online Portal Credentials to enable you access both individual Member Details for your&nbsp;<span style=\"font-weight: bolder;\">Pension Account and Scheme Wide information</span>&nbsp;for the whole membership.<br><br>Your credentials are as follows:<br><br>User ID:&nbsp;<span style=\"font-weight: bolder;\">#username</span><br>Password:&nbsp;<span style=\"font-weight: bolder;\">#password</span><br><br>The portal can be accessed on the following website:<br><br><span style=\"font-weight: bolder;\">#portalLink</span><p></p><p><span style=\"font-weight: bolder;\"></span>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span>\n                                                </p>','MEMBER_ACCOUNT_ACTIVATION','Account Activation'),(22755,'2021-06-21 06:22:03.589000','name,memberName,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>A member has requested to join <b>#scheme</b> under <b>#sponsor</b>.</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','PO_NEW_MEMBER_APPROVAL_REQUEST','Pending New Member'),(22756,'2021-06-21 06:23:27.511000','name,portalLink,','<p><p>Dear&nbsp;<span style=\"font-weight: bolder;\">#name</span></p>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style=\"color: rgb(41, 82, 24);\">APPROVED</span></p><p><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','NEW_MEMBER_APPROVAL','New Member Request Approval'),(22757,'2021-06-21 06:23:50.157000','name,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style=\"color: rgb(156, 0, 0);\">DECLINED</span></p><p><span style=\"color: rgb(156, 0, 0);\"></span>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','NEW_MEMBER_DECLINE','New Member Request '),(22758,'2021-06-21 06:24:45.132000','name,portalLink,','<p>Dear&nbsp;<span style=\"font-weight: bolder;\">#name</span></p><p>Your Request to update bio data has been approved.</p><p>#portalLink</p><p>If you have any queries, please contact us on&nbsp;<a href=\"http://nlcustomerportal@nico-life.com/\" target=\"_blank\">nlcustomerportal@nico-life.com</a>&nbsp;or call 265 1 822 699 to get assistance</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','MEMBER_BIO_DATA_UPDATE_APPROVAL','Bio Data Update'),(22863,'2021-06-21 21:22:35.256000','name,url,','<p>Dear <b>#name;</b></p><p>You recently requested for password reset for your Self Service Account, please click on the URL below to reset it or ignore if it wasn\'t you.</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>#url</b><br></p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842','REQUEST_PASSWORD_RESET','Forgot Password'),(22864,'2021-06-21 21:25:59.678000','name,username,password,portalLink,','<p><p>Dear <b>#name;</b></p><p><b><br></b>You recently requested for \npassword reset for your Self Service Account, please click on the URL \nbelow to reset it or ignore if it wasn\'t you.</p><p>Username : &nbsp;&nbsp;&nbsp;&nbsp;<b>#username<br></b>password :&nbsp;&nbsp;&nbsp;&nbsp; <b>#password<br></b>Visit <b><small id=\"namedKeys\"><b>#portalLink</b></small></b><br></p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com/\" target=\"_blank\">https://systechafrica.com/</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','PASSWORD_RESET','Password Reset'),(24252,'2021-07-03 22:58:45.721000','name,username,password,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>\nYour credentials are as follows:<br>\n<br>\nUser ID: <b>#username</b><br>\nPassword: <b>#password</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br><b>\n#portalLink<br></b><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p><p><br></p>','ADMIN_ACCOUNT_ACTIVATION','Admin Account Activation'),(24253,'2021-07-03 22:59:15.898000','name,username,password,portalLink,','Dear <b>#name</b>,<br>\n<br>\nYour credentials are as follows:<br>\n<br>\nUser ID: <b>#username</b><br>\nPassword: <b>#password</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br><b>\n#portalLink<br></b><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span>','PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION','Principal Officer Account Activation'),(27422,'2021-07-26 01:39:21.930000','name,token,','<h3>OTP : <span style=\"font-weight: bold;\">#token</span></h3><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','OTP_VERIFICATION','OTP Verification'),(29023,'2021-07-30 06:04:55.767000','name,memberName,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>New Claim brought by <b><span style=\"font-family: &quot;Arial&quot;;\">#memberName</span></b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br>\n#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','CLAIM_INITIATED','New Pending Claim'),(29024,'2021-07-30 06:05:21.661000','name,benefitNumber,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Claim Initiated<br>\n<br>\nTracking ID : <b>#benefitNumber</b><br><br>\nThe portal can be accessed on the following website:<br>\n<br>\n#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','MEMBER_CLAIM_INITIATED','Member Claim Initiated'),(29025,'2021-07-30 06:06:14.074000','name,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Beneficiary details <b>approved</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br>\n#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','MEMBER_BENEFICIARY_APPROVAL','Member Beneficiary Approval'),(29026,'2021-07-30 06:06:41.340000','name,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Beneficiary details <b>DECLINED</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br>\n#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','MEMBER_BENEFICIARY_DECLINE','Member Beneficiary Decline'),(29027,'2021-07-30 06:07:20.040000','name,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Your details have been&nbsp; <b>DECLINED</b><br>\n<br>\nThe portal can be accessed on the following website:<br>\n<br>\n#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: <span><span class=\"LrzXr zdqRlf kno-fv\"><span data-dtype=\"d3ifr\" data-local-attribute=\"d3ph\"><span>0723 847842</span></span></span></span></p>','MEMBER_BIO_DATA_UPDATE_DECLINE','Member Bio Data Decline'),(29028,'2021-07-30 06:07:42.020000','name,scheme,sponsor,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>Your request to join <b>#scheme</b> under <b>#sponsor</b> has been&nbsp; <span style=\"color: rgb(0, 0, 255);\"><b>RECEIVED<br></b></span><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','NEW_MEMBER_REGISTERED','New Member Registered'),(29029,'2021-07-30 06:33:35.415000','name,memberName,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>A member <span style=\"font-weight: bold;\">#memberName</span> has&nbsp; requested to update beneficiary details.</p><p>#portalLink</p><p><br>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST','Principal Officer Beneficiary Approval'),(29030,'2021-07-30 06:37:54.647000','name,memberName,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>A member <span style=\"font-weight: bold;\">#memberName</span> has&nbsp; requested to update&nbsp; details.</p><p>#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','PO_MEMBER_BIO_DATA_APPROVAL_REQUEST','Principal Officer Member Bio-Data Approval'),(29033,'2021-07-30 06:38:23.496000','name,memberName,portalLink,','<p>Dear <b>#name</b>,<br>\n<br>A member <span style=\"font-weight: bold;\">#memberName</span> has initiated a claim.</p><p>#portalLink</p><p>Sent by Systech Limited Customer Service<br>Systech Ltd| Website:&nbsp;<a href=\"https://systechafrica.com\" target=\"_blank\">https://systechafrica.com</a>&nbsp;| Tel: 0723 847842</p>','PO_PENDING_CLAIM','Principal Officer Pending Claim');
/*!40000 ALTER TABLE `emailtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `body` longtext,
  `createdAt` datetime(6) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `profile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6lc35rr8nfcxbyitsw0gksx82` (`profile_id`),
  CONSTRAINT `FK6lc35rr8nfcxbyitsw0gksx82` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forms`
--

DROP TABLE IF EXISTS `forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdAt` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `originalFileName` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forms`
--

LOCK TABLES `forms` WRITE;
/*!40000 ALTER TABLE `forms` DISABLE KEYS */;
/*!40000 ALTER TABLE `forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `landingpagecontent`
--

DROP TABLE IF EXISTS `landingpagecontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `landingpagecontent` (
  `id` bigint NOT NULL,
  `building` varchar(255) DEFAULT NULL,
  `businessHours` longtext,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fixedPhone` varchar(255) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `road` varchar(255) DEFAULT NULL,
  `secondaryPhone` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `loginImage` bigint DEFAULT NULL,
  `logo` bigint DEFAULT NULL,
  `mapLoc` longtext,
  `memberIcon` bigint DEFAULT NULL,
  `memberMessage` longtext,
  `pensionerIcon` bigint DEFAULT NULL,
  `pensionerImage` bigint DEFAULT NULL,
  `pensionerMessage` longtext,
  `statusConfig` varchar(255) DEFAULT NULL,
  `welcomeMessage` longtext,
  `whySaveMessage` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `landingpagecontent`
--

LOCK TABLES `landingpagecontent` WRITE;
/*!40000 ALTER TABLE `landingpagecontent` DISABLE KEYS */;
INSERT INTO `landingpagecontent` VALUES (1,'Mayfair','Monday - Friday <br/>\n7.30AM - 7PM<br/>\nSaturday<br/>\n8AM-2PM<br/>\nSunday & Holidays<br/>\nClosed','Kenya','test@gma.com','0752635153',0,0,'Nairobi','Parklends','0831538323','Nairobi','2021-04-22 12:47:52.000000',5664,5672,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d63821.30692629097!2d36.74357818841834!3d-1.2742329120678817!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f171680370193%3A0xf5bbb42d28fa1a88!2sSystech%20Limited!5e0!3m2!1sen!2ske!4v1626264175898!5m2!1sen!2ske\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>',0,'Being a member, means you can now access your pension contribution history',0,6715,'Being a member, means you can now access your pension contribution history','ACTIVE','We are the leader with 25 years of experience\nin the Pension Administration market!','Do not become a burden to your young ones. Save Now, Live free after retirement.');
/*!40000 ALTER TABLE `landingpagecontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mailconfig`
--

DROP TABLE IF EXISTS `mailconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mailconfig` (
  `id` bigint NOT NULL,
  `smtp_base_utl` varchar(255) DEFAULT NULL,
  `enableTLS` bit(1) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `smtp_email_from` varchar(255) DEFAULT NULL,
  `smtp_host` varchar(255) DEFAULT NULL,
  `mailType` varchar(255) DEFAULT NULL,
  `smtp_password` varchar(255) DEFAULT NULL,
  `smtp_port` int DEFAULT NULL,
  `supportEmail` varchar(255) DEFAULT NULL,
  `smtp_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mailconfig`
--

LOCK TABLES `mailconfig` WRITE;
/*!40000 ALTER TABLE `mailconfig` DISABLE KEYS */;
INSERT INTO `mailconfig` VALUES (2,'http://127.0.0.1:8080/mss',_binary '',_binary '\0','bursting.reports@gmail.com','smtp.gmail.com','TLS','Bursting@123',587,'aviatoryona67@gmail.com','bursting.reports@gmail.com'),(6969,'http://localhost:8080',_binary '\0',_binary '','aviatoryona@gmail.com','smtp.gmail.com','TLS','19051995Yk',587,'aviatoryona@gmail.com','aviatoryona@gmail.com');
/*!40000 ALTER TABLE `mailconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberFormConfigs`
--

DROP TABLE IF EXISTS `memberFormConfigs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberFormConfigs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buildingHidden` bit(1) DEFAULT NULL,
  `buildingReadOnly` bit(1) DEFAULT NULL,
  `cellPhoneHidden` bit(1) DEFAULT NULL,
  `cellPhoneReadOnly` bit(1) DEFAULT NULL,
  `companyIdHidden` bit(1) DEFAULT NULL,
  `companyIdReadOnly` bit(1) DEFAULT NULL,
  `contractEndDateHidden` bit(1) DEFAULT NULL,
  `contractEndDateReadOnly` bit(1) DEFAULT NULL,
  `countryHidden` bit(1) DEFAULT NULL,
  `countryReadOnly` bit(1) DEFAULT NULL,
  `dateJoinedSchemeHidden` bit(1) DEFAULT NULL,
  `dateJoinedSchemeReadOnly` bit(1) DEFAULT NULL,
  `dateOfAppointmentHidden` bit(1) DEFAULT NULL,
  `dateOfAppointmentReadOnly` bit(1) DEFAULT NULL,
  `departmentHidden` bit(1) DEFAULT NULL,
  `departmentReadOnly` bit(1) DEFAULT NULL,
  `depotHidden` bit(1) DEFAULT NULL,
  `depotReadOnly` bit(1) DEFAULT NULL,
  `designationHidden` bit(1) DEFAULT NULL,
  `designationReadOnly` bit(1) DEFAULT NULL,
  `dobHidden` bit(1) DEFAULT NULL,
  `dobReadOnly` bit(1) DEFAULT NULL,
  `emailHidden` bit(1) DEFAULT NULL,
  `emailReadOnly` bit(1) DEFAULT NULL,
  `fileHidden` bit(1) DEFAULT NULL,
  `firstnameHidden` bit(1) DEFAULT NULL,
  `firstnameReadOnly` bit(1) DEFAULT NULL,
  `fixedPhoneHidden` bit(1) DEFAULT NULL,
  `fixedPhoneReadOnly` bit(1) DEFAULT NULL,
  `genderHidden` bit(1) DEFAULT NULL,
  `genderReadOnly` bit(1) DEFAULT NULL,
  `idNoHidden` bit(1) DEFAULT NULL,
  `idNoReadOnly` bit(1) DEFAULT NULL,
  `jobGradeIdHidden` bit(1) DEFAULT NULL,
  `jobGradeIdReadOnly` bit(1) DEFAULT NULL,
  `maritalStatusAtEntryHidden` bit(1) DEFAULT NULL,
  `maritalStatusAtEntryReadOnly` bit(1) DEFAULT NULL,
  `maritalStatusHidden` bit(1) DEFAULT NULL,
  `maritalStatusReadOnly` bit(1) DEFAULT NULL,
  `mclassIdHidden` bit(1) DEFAULT NULL,
  `mclassIdReadOnly` bit(1) DEFAULT NULL,
  `memberNoHidden` bit(1) DEFAULT NULL,
  `memberNoReadOnly` bit(1) DEFAULT NULL,
  `membershipNoHidden` bit(1) DEFAULT NULL,
  `membershipNoReadOnly` bit(1) DEFAULT NULL,
  `membershipStatusHidden` bit(1) DEFAULT NULL,
  `membershipStatusReadOnly` bit(1) DEFAULT NULL,
  `monthlySalaryHidden` bit(1) DEFAULT NULL,
  `monthlySalaryReadOnly` bit(1) DEFAULT NULL,
  `nationalPenNoHidden` bit(1) DEFAULT NULL,
  `nationalPenNoReadOnly` bit(1) DEFAULT NULL,
  `nationalityHidden` bit(1) DEFAULT NULL,
  `nationalityReadOnly` bit(1) DEFAULT NULL,
  `otherContactsHidden` bit(1) DEFAULT NULL,
  `otherContactsReadOnly` bit(1) DEFAULT NULL,
  `otherIdNoHidden` bit(1) DEFAULT NULL,
  `otherIdNoReadOnly` bit(1) DEFAULT NULL,
  `othernamesHidden` bit(1) DEFAULT NULL,
  `othernamesReadOnly` bit(1) DEFAULT NULL,
  `partyRefNoHidden` bit(1) DEFAULT NULL,
  `partyRefNoReadOnly` bit(1) DEFAULT NULL,
  `permanentDistrictNameHidden` bit(1) DEFAULT NULL,
  `permanentDistrictNameReadOnly` bit(1) DEFAULT NULL,
  `permanentTANameNameHidden` bit(1) DEFAULT NULL,
  `permanentTANameReadOnly` bit(1) DEFAULT NULL,
  `permanentVillageNameHidden` bit(1) DEFAULT NULL,
  `permanentVillageNameReadOnly` bit(1) DEFAULT NULL,
  `placeOfBirthDistrictNameHidden` bit(1) DEFAULT NULL,
  `placeOfBirthDistrictNameReadOnly` bit(1) DEFAULT NULL,
  `placeOfBirthTANameHidden` bit(1) DEFAULT NULL,
  `placeOfBirthTANameReadOnly` bit(1) DEFAULT NULL,
  `policyNoHidden` bit(1) DEFAULT NULL,
  `policyNoReadOnly` bit(1) DEFAULT NULL,
  `postalAddressHidden` bit(1) DEFAULT NULL,
  `postalAddressReadOnly` bit(1) DEFAULT NULL,
  `regionHidden` bit(1) DEFAULT NULL,
  `regionReadOnly` bit(1) DEFAULT NULL,
  `residentialAddressHidden` bit(1) DEFAULT NULL,
  `residentialAddressReadOnly` bit(1) DEFAULT NULL,
  `roadHidden` bit(1) DEFAULT NULL,
  `roadReadOnly` bit(1) DEFAULT NULL,
  `salutationHidden` bit(1) DEFAULT NULL,
  `salutationReadOnly` bit(1) DEFAULT NULL,
  `schemeIdHidden` bit(1) DEFAULT NULL,
  `schemeIdReadOnly` bit(1) DEFAULT NULL,
  `secondaryPhoneHidden` bit(1) DEFAULT NULL,
  `secondaryPhoneReadOnly` bit(1) DEFAULT NULL,
  `staffNoHidden` bit(1) DEFAULT NULL,
  `staffNoReadOnly` bit(1) DEFAULT NULL,
  `submitFormHidden` bit(1) DEFAULT NULL,
  `surnameHidden` bit(1) DEFAULT NULL,
  `surnameReadOnly` bit(1) DEFAULT NULL,
  `taxPinNoHidden` bit(1) DEFAULT NULL,
  `taxPinNoReadOnly` bit(1) DEFAULT NULL,
  `townHidden` bit(1) DEFAULT NULL,
  `townReadOnly` bit(1) DEFAULT NULL,
  `villageNameHidden` bit(1) DEFAULT NULL,
  `villageNameReadOnly` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberFormConfigs`
--

LOCK TABLES `memberFormConfigs` WRITE;
/*!40000 ALTER TABLE `memberFormConfigs` DISABLE KEYS */;
/*!40000 ALTER TABLE `memberFormConfigs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberperms`
--

DROP TABLE IF EXISTS `memberperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowBenefitCalculator` bit(1) DEFAULT NULL,
  `allowInitiateClaim` bit(1) DEFAULT NULL,
  `allowMakeContributions` bit(1) DEFAULT NULL,
  `allowRequestStatement` bit(1) DEFAULT NULL,
  `allowStkPush` bit(1) DEFAULT NULL,
  `allowWhatIfAnalysis` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `balancesMenuActivated` bit(1) DEFAULT NULL,
  `bankMenuActivated` bit(1) DEFAULT NULL,
  `benefitsFmActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `contributionsMenuActivated` bit(1) DEFAULT NULL,
  `documentsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `projectionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberperms`
--

LOCK TABLES `memberperms` WRITE;
/*!40000 ALTER TABLE `memberperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `memberperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addressPlaceOfBirth` varchar(255) DEFAULT NULL,
  `addressTraditionalAuthority` varchar(255) DEFAULT NULL,
  `addressVillage` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `currentMonthlySalary` varchar(255) DEFAULT NULL,
  `dateOfAppointment` varchar(255) DEFAULT NULL,
  `dateOfBirth` datetime(6) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `disabled` varchar(255) DEFAULT NULL,
  `documents` longtext,
  `emailAddress` varchar(255) DEFAULT NULL,
  `employed` varchar(255) DEFAULT NULL,
  `employerRefNo` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `idNumber` varchar(255) DEFAULT NULL,
  `iddoc` varchar(255) DEFAULT NULL,
  `otheridnumber` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `maritalStatus` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `nssn` varchar(255) DEFAULT NULL,
  `othernames` varchar(255) DEFAULT NULL,
  `phmPlaceOfBirth` varchar(255) DEFAULT NULL,
  `phmPlaceOfBirthID` varchar(255) DEFAULT NULL,
  `phmTraditionalAuthority` varchar(255) DEFAULT NULL,
  `phmTraditionalAuthorityID` varchar(255) DEFAULT NULL,
  `phmVillage` varchar(255) DEFAULT NULL,
  `phmVillageID` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `pobPlaceOfBirth` varchar(255) DEFAULT NULL,
  `pobPlaceOfBirthID` varchar(255) DEFAULT NULL,
  `pobTraditionalAuthority` varchar(255) DEFAULT NULL,
  `pobTraditionalAuthorityID` varchar(255) DEFAULT NULL,
  `pobVillage` varchar(255) DEFAULT NULL,
  `pobVillageID` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `posted` bit(1) DEFAULT NULL,
  `residentialAddress` varchar(255) DEFAULT NULL,
  `road` varchar(255) DEFAULT NULL,
  `schemeid` bigint DEFAULT NULL,
  `schemename` varchar(255) NOT NULL,
  `secondaryPhoneNumber` varchar(255) DEFAULT NULL,
  `sponsorId` bigint DEFAULT NULL,
  `sponsorName` varchar(255) NOT NULL,
  `staffNo` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n7d47abhycm2n82538suv207h` (`phoneNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m05sb1hgsv38qjb4ksyh5eat2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobilemoneyconfig`
--

DROP TABLE IF EXISTS `mobilemoneyconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobilemoneyconfig` (
  `id` bigint NOT NULL,
  `accountReference` longtext,
  `callbackUrl` longtext,
  `isLive` bit(1) DEFAULT NULL,
  `minAmount` double DEFAULT NULL,
  `mobileMoneyProcedure` longtext,
  `mpesaAppKey` longtext,
  `mpesaAppSecret` longtext,
  `mpesaPassKey` longtext,
  `mpesaPaybill` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `timeoutUrl` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobilemoneyconfig`
--

LOCK TABLES `mobilemoneyconfig` WRITE;
/*!40000 ALTER TABLE `mobilemoneyconfig` DISABLE KEYS */;
INSERT INTO `mobilemoneyconfig` VALUES (1,'Systech Scheme','http://129.159.250.225:8085/mss/resources/api/mpesaCallBack',_binary '\0',1,'1.Go to the M-pesa Menu.<br>                                            2.Select Pay Bill.<br>                                            3.Enter Business No. <span><b>174379</b></span>.<br>                                            4.Enter Account No.<span><b>XXXXXX</b></span> (Where XXXXXX is  <b>Member number</b>)<br>                                            5.Enter the Amount.<br>                                            6.Enter your M-Pesa PIN then send. ','UrKWUKv4UXEsGdlM7szaJvcDAZ1OrEVN','EmAahKmMXZtkKX20','bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919','174379','ACTIVE','http://129.159.250.225:8085/mss/resources/api/mpesaTimeoutCallBack');
/*!40000 ALTER TABLE `mobilemoneyconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `delivered` bit(1) NOT NULL,
  `emailFrom` varchar(255) NOT NULL,
  `message` longtext NOT NULL,
  `name` varchar(255) NOT NULL,
  `retryCount` int DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otpconfig`
--

DROP TABLE IF EXISTS `otpconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otpconfig` (
  `id` bigint NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `otpIdentifier` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otpconfig`
--

LOCK TABLES `otpconfig` WRITE;
/*!40000 ALTER TABLE `otpconfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `otpconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otplogs`
--

DROP TABLE IF EXISTS `otplogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otplogs` (
  `id` bigint NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `expiry` datetime(6) DEFAULT NULL,
  `nextCheck` datetime(6) DEFAULT NULL,
  `otpIdentifier` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otplogs`
--

LOCK TABLES `otplogs` WRITE;
/*!40000 ALTER TABLE `otplogs` DISABLE KEYS */;
/*!40000 ALTER TABLE `otplogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pensionerperms`
--

DROP TABLE IF EXISTS `pensionerperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pensionerperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `canAuthenticateSelfie` bit(1) DEFAULT NULL,
  `canRegisterSelfie` bit(1) DEFAULT NULL,
  `canUpdateSelfie` bit(1) DEFAULT NULL,
  `coeMenuActivated` bit(1) DEFAULT NULL,
  `deductionsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `payrollsMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pensionerperms`
--

LOCK TABLES `pensionerperms` WRITE;
/*!40000 ALTER TABLE `pensionerperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `pensionerperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poperms`
--

DROP TABLE IF EXISTS `poperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBenefitCalculator` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowInitiateClaims` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `allowViewMemberContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `schemesMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poperms`
--

LOCK TABLES `poperms` WRITE;
/*!40000 ALTER TABLE `poperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `poperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `id` bigint NOT NULL,
  `loginIdentifier` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (2,'EMAIL','MEMBER'),(3,'EMAIL','SPONSOR'),(4,'PHONE','PENSIONER'),(5,'EMAIL','CRM'),(6,'EMAIL','ADMIN'),(7,'NSSN','CRE'),(8,'EMAIL','PRINCIPAL OFFICER');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_permissions`
--

DROP TABLE IF EXISTS `profile_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_permissions` (
  `permission_id` bigint NOT NULL,
  `profile_id` bigint NOT NULL,
  PRIMARY KEY (`permission_id`,`profile_id`),
  KEY `FK58sdxd4c83xuvusbujkp83xdm` (`profile_id`),
  CONSTRAINT `FK58sdxd4c83xuvusbujkp83xdm` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKavt8jyq5oo8mljvyog7976xed` FOREIGN KEY (`permission_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_permissions`
--

LOCK TABLES `profile_permissions` WRITE;
/*!40000 ALTER TABLE `profile_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securityconfig`
--

DROP TABLE IF EXISTS `securityconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securityconfig` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `issuer` varchar(255) DEFAULT NULL,
  `tokenValidityMillis` bigint DEFAULT NULL,
  `tokenValidityMillisForRememberMe` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securityconfig`
--

LOCK TABLES `securityconfig` WRITE;
/*!40000 ALTER TABLE `securityconfig` DISABLE KEYS */;
INSERT INTO `securityconfig` VALUES (1,'2021-03-03 08:27:18.000000','com.systech',86400,1314000);
/*!40000 ALTER TABLE `securityconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selfieResults`
--

DROP TABLE IF EXISTS `selfieResults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `selfieResults` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `cycleId` bigint DEFAULT NULL,
  `finalResult` bit(1) DEFAULT NULL,
  `image` longtext,
  `jobId` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selfieResults`
--

LOCK TABLES `selfieResults` WRITE;
/*!40000 ALTER TABLE `selfieResults` DISABLE KEYS */;
/*!40000 ALTER TABLE `selfieResults` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `ipAddress` varchar(255) NOT NULL,
  `stoppedAt` datetime(6) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3toi1jrs5yqhsv3td58p3s37r` (`userId`),
  CONSTRAINT `FK3toi1jrs5yqhsv3td58p3s37r` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smileIdentityConfig`
--

DROP TABLE IF EXISTS `smileIdentityConfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smileIdentityConfig` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authenticationAmount` bigint DEFAULT NULL,
  `authenticationTrials` bigint DEFAULT NULL,
  `callbackApi` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `decodedApiKey` longtext,
  `encodedApiKey` longtext,
  `partnerId` varchar(255) DEFAULT NULL,
  `reRegistrationAmount` bigint DEFAULT NULL,
  `registrationAmount` bigint DEFAULT NULL,
  `returnHistory` bit(1) DEFAULT NULL,
  `returnImages` bit(1) DEFAULT NULL,
  `returnJobStatus` bit(1) DEFAULT NULL,
  `serverId` int DEFAULT NULL,
  `statusConfig` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smileIdentityConfig`
--

LOCK TABLES `smileIdentityConfig` WRITE;
/*!40000 ALTER TABLE `smileIdentityConfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `smileIdentityConfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor_config`
--

DROP TABLE IF EXISTS `sponsor_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sponsor_config` (
  `id` bigint NOT NULL,
  `authorizationLevel` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `statusConfig` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_config`
--

LOCK TABLES `sponsor_config` WRITE;
/*!40000 ALTER TABLE `sponsor_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor_users`
--

DROP TABLE IF EXISTS `sponsor_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sponsor_users` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `staffNo` bigint DEFAULT NULL,
  `permission_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_117waiwbimit2jsj4dwxvh91p` (`permission_id`),
  CONSTRAINT `FKam8heh8wlkp98t08lgvu36sne` FOREIGN KEY (`permission_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_users`
--

LOCK TABLES `sponsor_users` WRITE;
/*!40000 ALTER TABLE `sponsor_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorperms`
--

DROP TABLE IF EXISTS `sponsorperms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sponsorperms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allowAddBatchUser` bit(1) DEFAULT NULL,
  `allowAddSingleUser` bit(1) DEFAULT NULL,
  `allowApproveDocuments` bit(1) DEFAULT NULL,
  `allowBookBill` bit(1) DEFAULT NULL,
  `allowStageContributions` bit(1) DEFAULT NULL,
  `auditTrailMenuActivated` bit(1) DEFAULT NULL,
  `benefitsMenuActivated` bit(1) DEFAULT NULL,
  `billsMenuActivated` bit(1) DEFAULT NULL,
  `claimsMenuActivated` bit(1) DEFAULT NULL,
  `dmsMenuActivated` bit(1) DEFAULT NULL,
  `homeMenuActivated` bit(1) DEFAULT NULL,
  `manageAccountMenuActivated` bit(1) DEFAULT NULL,
  `membersMenuActivated` bit(1) DEFAULT NULL,
  `personalInfoMenuActivated` bit(1) DEFAULT NULL,
  `receiptsMenuActivated` bit(1) DEFAULT NULL,
  `sponsorConfigMenuActivated` bit(1) DEFAULT NULL,
  `stagedContributionsMenuActivated` bit(1) DEFAULT NULL,
  `ticketsMenuActivated` bit(1) DEFAULT NULL,
  `tpfaMenuActivated` bit(1) DEFAULT NULL,
  `usersAccountMenuActivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorperms`
--

LOCK TABLES `sponsorperms` WRITE;
/*!40000 ALTER TABLE `sponsorperms` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorperms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage_contribution`
--

DROP TABLE IF EXISTS `stage_contribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stage_contribution` (
  `id` bigint NOT NULL,
  `MerchantRequestID` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `memberId` bigint DEFAULT NULL,
  `mpesaPhoneNumber` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `paybill` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `requestId` varchar(255) DEFAULT NULL,
  `resultMessage` longtext,
  `schemeId` bigint DEFAULT NULL,
  `sendToXi` bit(1) DEFAULT NULL,
  `timestamp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage_contribution`
--

LOCK TABLES `stage_contribution` WRITE;
/*!40000 ALTER TABLE `stage_contribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `stage_contribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stagedbeneficiaries`
--

DROP TABLE IF EXISTS `stagedbeneficiaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stagedbeneficiaries` (
  `id` bigint NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `bankName` bigint DEFAULT NULL,
  `benAccountName` varchar(255) DEFAULT NULL,
  `benAccountNo` varchar(255) DEFAULT NULL,
  `benAddressPostalAddress` varchar(255) DEFAULT NULL,
  `benAttachment` varchar(255) DEFAULT NULL,
  `benAttachmentname` varchar(255) DEFAULT NULL,
  `benBankBranchId` bigint DEFAULT NULL,
  `benBankBranchName` varchar(255) DEFAULT NULL,
  `benBankName` varchar(255) DEFAULT NULL,
  `benBirthCert` varchar(255) DEFAULT NULL,
  `benCellPhone` varchar(255) DEFAULT NULL,
  `benDateOfAppointment` varchar(255) DEFAULT NULL,
  `benDob` varchar(255) DEFAULT NULL,
  `benFirstname` varchar(255) DEFAULT NULL,
  `benGender` varchar(255) DEFAULT NULL,
  `benGuardianAddr` varchar(255) DEFAULT NULL,
  `benGuardianCountry` varchar(255) DEFAULT NULL,
  `benGuardianEmail` varchar(255) DEFAULT NULL,
  `benGuardianFn` varchar(255) DEFAULT NULL,
  `benGuardianGender` varchar(255) DEFAULT NULL,
  `benGuardianOn` varchar(255) DEFAULT NULL,
  `benGuardianRelation` varchar(255) DEFAULT NULL,
  `benGuardianSn` varchar(255) DEFAULT NULL,
  `benGuardianTown` varchar(255) DEFAULT NULL,
  `benIdNo` varchar(255) DEFAULT NULL,
  `benLumpsumEntitlement` varchar(255) DEFAULT NULL,
  `benMaidenName` varchar(255) DEFAULT NULL,
  `benMaritalStatus` varchar(255) DEFAULT NULL,
  `benMemberId` bigint DEFAULT NULL,
  `benMonthlyEntitlement` varchar(255) DEFAULT NULL,
  `benNationality` varchar(255) DEFAULT NULL,
  `benOthernames` varchar(255) DEFAULT NULL,
  `benPhysicalCondition` varchar(255) DEFAULT NULL,
  `benRelationShipCategory` varchar(255) DEFAULT NULL,
  `benRelationshipType` varchar(255) DEFAULT NULL,
  `benSurname` varchar(255) DEFAULT NULL,
  `beneficiaryId` bigint DEFAULT NULL,
  `legibilityStatus` varchar(255) DEFAULT NULL,
  `memberEmail` varchar(255) DEFAULT NULL,
  `memberName` varchar(255) DEFAULT NULL,
  `memberNo` varchar(255) DEFAULT NULL,
  `nId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permanentDistrictId` bigint DEFAULT NULL,
  `permanentDistrictName` varchar(255) DEFAULT NULL,
  `permanentTAId` bigint DEFAULT NULL,
  `permanentTAName` varchar(255) DEFAULT NULL,
  `permanentVillageId` bigint DEFAULT NULL,
  `permanentVillageName` varchar(255) DEFAULT NULL,
  `placeOfBirthDistrictId` bigint DEFAULT NULL,
  `placeOfBirthDistrictName` varchar(255) DEFAULT NULL,
  `placeOfBirthTAId` bigint DEFAULT NULL,
  `placeOfBirthTAName` varchar(255) DEFAULT NULL,
  `placeOfBirthVillageId` bigint DEFAULT NULL,
  `placeOfBirthVillageName` varchar(255) DEFAULT NULL,
  `schemeId` varchar(255) DEFAULT NULL,
  `sponsorId` varchar(255) DEFAULT NULL,
  `unknown` varchar(255) DEFAULT NULL,
  `documents` varchar(255) DEFAULT NULL,
  `posted` bit(1) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stagedbeneficiaries`
--

LOCK TABLES `stagedbeneficiaries` WRITE;
/*!40000 ALTER TABLE `stagedbeneficiaries` DISABLE KEYS */;
/*!40000 ALTER TABLE `stagedbeneficiaries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stagememberdetails`
--

DROP TABLE IF EXISTS `stagememberdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stagememberdetails` (
  `id` bigint NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `building` varchar(255) DEFAULT NULL,
  `cellPhone` varchar(255) DEFAULT NULL,
  `companyId` varchar(255) DEFAULT NULL,
  `contractEndDate` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `dateJoinedScheme` datetime(6) DEFAULT NULL,
  `dateOfAppointment` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `depot` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `fixedPhone` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `idNo` varchar(255) DEFAULT NULL,
  `jobGradeId` varchar(255) DEFAULT NULL,
  `maritalStatus` varchar(255) DEFAULT NULL,
  `maritalStatusAtDoeName` varchar(255) DEFAULT NULL,
  `mclassId` varchar(255) DEFAULT NULL,
  `memberNo` varchar(255) DEFAULT NULL,
  `membershipNo` varchar(255) DEFAULT NULL,
  `monthlySalary` double DEFAULT NULL,
  `nationalPenNo` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `otherContacts` varchar(255) DEFAULT NULL,
  `otherIdNo` varchar(255) DEFAULT NULL,
  `othernames` varchar(255) DEFAULT NULL,
  `partyRefNo` varchar(255) DEFAULT NULL,
  `permanentDistrictName` varchar(255) DEFAULT NULL,
  `permanentTAName` varchar(255) DEFAULT NULL,
  `permanentVillageName` varchar(255) DEFAULT NULL,
  `pinNo` varchar(255) DEFAULT NULL,
  `placeOfBirthDistrictName` varchar(255) DEFAULT NULL,
  `placeOfBirthTAName` varchar(255) DEFAULT NULL,
  `policyNo` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `residentialAddress` varchar(255) DEFAULT NULL,
  `road` varchar(255) DEFAULT NULL,
  `schemeId` bigint DEFAULT NULL,
  `secondaryPhone` varchar(255) DEFAULT NULL,
  `sponsorId` varchar(255) DEFAULT NULL,
  `staffNo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `villageName` varchar(255) DEFAULT NULL,
  `documents` longtext,
  `fname` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `memberId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lanvubixpddqqtwhigtq9ksks` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stagememberdetails`
--

LOCK TABLES `stagememberdetails` WRITE;
/*!40000 ALTER TABLE `stagememberdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `stagememberdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supportmessages`
--

DROP TABLE IF EXISTS `supportmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supportmessages` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` longtext,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supportmessages`
--

LOCK TABLES `supportmessages` WRITE;
/*!40000 ALTER TABLE `supportmessages` DISABLE KEYS */;
/*!40000 ALTER TABLE `supportmessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_Sponsors`
--

DROP TABLE IF EXISTS `tbl_user_Sponsors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user_Sponsors` (
  `id` bigint NOT NULL,
  `activated` bit(1) NOT NULL,
  `attempt` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_expiry` datetime(6) DEFAULT NULL,
  `profileID` bigint DEFAULT NULL,
  `securityCode` varchar(255) DEFAULT NULL,
  `smsActivationCode` varchar(255) DEFAULT NULL,
  `userProfile` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `parent_sponsor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8fif9vm7f3ppyy5qn5eop0834` (`username`),
  KEY `FKjdk6webwof1f15c27a82w3ks3` (`parent_sponsor_id`),
  CONSTRAINT `FKjdk6webwof1f15c27a82w3ks3` FOREIGN KEY (`parent_sponsor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_Sponsors`
--

LOCK TABLES `tbl_user_Sponsors` WRITE;
/*!40000 ALTER TABLE `tbl_user_Sponsors` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_user_Sponsors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` bigint NOT NULL,
  `TicketRefNo` varchar(255) DEFAULT NULL,
  `body` longtext NOT NULL,
  `closed` bit(1) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `newOwnerRepliesCount` bigint DEFAULT NULL,
  `newSupportRepliesCount` bigint DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `schemeId` bigint DEFAULT NULL,
  `sponsorId` bigint DEFAULT NULL,
  `subject` varchar(255) NOT NULL,
  `created_by_id` bigint DEFAULT NULL,
  `forwadedBy_id` bigint DEFAULT NULL,
  `profile_id` bigint DEFAULT NULL,
  `recipient_id` bigint DEFAULT NULL,
  `ticketcategory_id` bigint DEFAULT NULL,
  `ticketIssue_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjn8jr9dliggcvmx6o12a3e7k9` (`created_by_id`),
  KEY `FKec3m0t1vnv08064b901ggx84s` (`forwadedBy_id`),
  KEY `FKorscutbhywlq3s152g5elkura` (`profile_id`),
  KEY `FKdklqrqprcbj3lfaqm3dsk4t7q` (`recipient_id`),
  KEY `FKd7gjha0so0ev4kw672oa5c8l2` (`ticketcategory_id`),
  KEY `FKnifk10jj4yo3i1d77hco8sush` (`ticketIssue_id`),
  CONSTRAINT `FKd7gjha0so0ev4kw672oa5c8l2` FOREIGN KEY (`ticketcategory_id`) REFERENCES `ticketcategory` (`id`),
  CONSTRAINT `FKdklqrqprcbj3lfaqm3dsk4t7q` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKec3m0t1vnv08064b901ggx84s` FOREIGN KEY (`forwadedBy_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjn8jr9dliggcvmx6o12a3e7k9` FOREIGN KEY (`created_by_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnifk10jj4yo3i1d77hco8sush` FOREIGN KEY (`ticketIssue_id`) REFERENCES `ticketIssues` (`id`),
  CONSTRAINT `FKorscutbhywlq3s152g5elkura` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketIssues`
--

DROP TABLE IF EXISTS `ticketIssues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticketIssues` (
  `id` bigint NOT NULL,
  `issue` varchar(255) DEFAULT NULL,
  `profile_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK48i5q86bkxgfsvpxv4qi5g5pg` (`profile_id`),
  CONSTRAINT `FK48i5q86bkxgfsvpxv4qi5g5pg` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketIssues`
--

LOCK TABLES `ticketIssues` WRITE;
/*!40000 ALTER TABLE `ticketIssues` DISABLE KEYS */;
INSERT INTO `ticketIssues` VALUES (1,'Forgot PWD',6);
/*!40000 ALTER TABLE `ticketIssues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketcategory`
--

DROP TABLE IF EXISTS `ticketcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticketcategory` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gpuwuerabm2clhr1drgke9s69` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketcategory`
--

LOCK TABLES `ticketcategory` WRITE;
/*!40000 ALTER TABLE `ticketcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticketcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketcategory_profiles`
--

DROP TABLE IF EXISTS `ticketcategory_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticketcategory_profiles` (
  `ticketcategory_id` bigint NOT NULL,
  `profile_id` bigint NOT NULL,
  PRIMARY KEY (`ticketcategory_id`,`profile_id`),
  KEY `FK680s256vfqutkyntvh0rfcx4s` (`profile_id`),
  CONSTRAINT `FK680s256vfqutkyntvh0rfcx4s` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKhrqqqsfvbl3c76fxixnytunjo` FOREIGN KEY (`ticketcategory_id`) REFERENCES `ticketcategory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketcategory_profiles`
--

LOCK TABLES `ticketcategory_profiles` WRITE;
/*!40000 ALTER TABLE `ticketcategory_profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticketcategory_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketmessage`
--

DROP TABLE IF EXISTS `ticketmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticketmessage` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `message` longtext NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `ticketmessage_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlos0wmpshxleod2semiimcc94` (`user_id`),
  KEY `FK6x0i33tf53hf9604jbg7w03jo` (`ticketmessage_id`),
  CONSTRAINT `FK6x0i33tf53hf9604jbg7w03jo` FOREIGN KEY (`ticketmessage_id`) REFERENCES `ticket` (`id`),
  CONSTRAINT `FKlos0wmpshxleod2semiimcc94` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketmessage`
--

LOCK TABLES `ticketmessage` WRITE;
/*!40000 ALTER TABLE `ticketmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticketmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `activated` bit(1) NOT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `approvedByCrm` bit(1) DEFAULT NULL,
  `authenticationTrials` bigint DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deactivatedByAdmin` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `flg_firsttime` varchar(255) DEFAULT NULL,
  `fm_identifier` varchar(255) DEFAULT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `lockedStatus` bit(1) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `numTrials` int DEFAULT NULL,
  `parentSponsorEmail` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `reset_date` datetime(6) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `security_code` varchar(20) DEFAULT NULL,
  `accountStatus` varchar(255) DEFAULT NULL,
  `cellPhone` varchar(255) DEFAULT NULL,
  `mbshipStatus` varchar(255) DEFAULT NULL,
  `memberId` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nationalPenNo` varchar(255) DEFAULT NULL,
  `pensionerId` varchar(255) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `schemeId` bigint DEFAULT NULL,
  `sponsorId` bigint DEFAULT NULL,
  `sponsorRefNo` varchar(255) DEFAULT NULL,
  `staffNo` varchar(255) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  `userStatus` varchar(255) DEFAULT NULL,
  `profile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`),
  KEY `FK5q3e9303ap1wvtia6sft7ht1s` (`profile_id`),
  CONSTRAINT `FK5q3e9303ap1wvtia6sft7ht1s` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','63577997585887771305',_binary '\0',0,'2021-06-08 13:19:34.000000',_binary '\0','admin@mss.com','mssuser','false','ADMIN','en','mssuser',_binary '\0','admin',0,NULL,'0e7517141fb53f21ee439b355b5a1d0a','File_20210702144356rsz_sample_logo.png',NULL,NULL,NULL,'Active','0790909090','0',0,'mssuser mssuser','',NULL,'ADMINISTRATOR',6603,0,'0','',28272,'ACTIVE',6);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 11:41:08
