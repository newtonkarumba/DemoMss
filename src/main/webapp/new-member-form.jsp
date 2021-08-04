<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
<jsp:include page="includes/partial/header.jsp" />
--%>

<div class="main-content">
    <div class="container">
        <div class="row">
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a data-toggle="pill" href="#new-member">NEW MEMBER</a></li>
                <%--
                                <li><a data-toggle="pill" href="#existing-member" >EXISTING MEMBER</a></li>
                --%>
            </ul>
            <p>&nbsp;</p>

            <div class="tab-content">
                <div id="new-member" class="tab-pane fade in active border-top">
                    <div class="col-sm-12 col-md-12 col-lg-12"> <!-- required for floating -->
                        <p></p>
                        <div class="row" id="fetch-sponsor">
                            <form method="post" id="form-fetch-sponsor-details">
                                <div class="row">
                                    <div class="col-md-3">
                                        CURRENT EMPLOYER'S
                                        REFERENCE NUMBER
                                    </div>
                                    <div class="col-md-6 form-group">
                                        <input
                                            type="text" name="sponsorId" class="form-control"
                                            id="sponsorId"
                                            placeholder="Current employer reference number"
                                            oninput="this.value=this.value.toUpperCase()">
                                    </div>
                                    <div class="col-md-3">
                                        <button class="btn btn-primary btn-block">FETCH SPONSOR DETAILS</button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="row" id="member-show-form">
                            <form method="post" id="form-register-etl">
                                <h1 class="heading">NEW MEMBER REGISTRATION </h1>
                                <div class="row">
                                    <div class="col-md-2">
                                        IDENTIFICATION DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="IdNumber" class="control-label">ID NUMBER:<span
                                                        id="explainEnter"> Enter ID Number and Press Enter</span></label>
                                                <input
                                                        type="text" name="etlIdNumber" class="form-control"
                                                        id="etlIdNumber"
                                                        placeholder="ID Number">
                                            </div>
                                            <div class="col-md-6 form-group" id="ssnit-details">
                                                <label for="socialSecurity" class="control-label">NATIONAL PENSION
                                                    NUMBER:</label> <input
                                                    type="text" name="socialSecurity" class="form-control"
                                                    id="socialSecurity"
                                                    placeholder="National Pension Number"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->

                                    </div>
                                </div>

                                <div class="row" id="employer-details">
                                    <div class="col-md-2">
                                        EMPLOYER DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="employer" class="control-label">CURRENT EMPLOYER'S
                                                    NAME:</label> <input
                                                    type="text" name="employer" class="form-control" id="employer"
                                                    placeholder="Current Employer's Name" value="${sponsor.companyName}"
                                                    oninput="this.value=this.value.toUpperCase()" disabled>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="employerAddress" class="control-label">CURRENT
                                                    EMPLOYER'S POSTAL ADDRESS:</label> <input
                                                    type="text" name="employerAddress" class="form-control"
                                                    id="employerAddress"
                                                    placeholder="Current Employer's Address"
                                                    value="${sponsor.companyAddress}"
                                                    oninput="this.value=this.value.toUpperCase()" disabled>
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="employerProdNo" class="control-label">CURRENT EMPLOYER'S
                                                    ID:</label> <input
                                                    type="text" name="employerProdNo" class="form-control"
                                                    id="employerProdNo" value="${sponsor.productNo}"
                                                    placeholder="Current employer product number"
                                                    oninput="this.value=this.value.toUpperCase()" disabled>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="schemeproduct" class="control-label">SELECT SCHEME
                                                    PRODUCT:</label>
                                                <input type="text"
                                                       name="schemeproduct" id="schemeproduct" class="form-control"
                                                       value="${sponsor.product}"
                                                       disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="personal_details">
                                    <div class="col-md-2">
                                        CONTRIBUTOR'S NAME
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-2 form-group">
                                                <label for="title" class="control-label required">TITLE:</label>
                                                <select id="title" name="title" class="form-control">
                                                    <option value="">Select Title ...</option>
                                                    <option value="PROF">Prof</option>
                                                    <option value="DR">Dr</option>
                                                    <option value="MR">Mr</option>
                                                    <option value="MRS">Mrs</option>
                                                    <option value="REV">Rev</option>
                                                    <option value="HON">Hon</option>
                                                    <option value="ENG">Eng</option>
                                                    <option value="MISS">Ms</option>
                                                </select>
                                            </div>
                                            <div class="col-md-5 form-group">
                                                <label for="surName" class="control-label required">SURNAME:</label>
                                                <input
                                                        type="text" name="surName" class="form-control" id="surName"
                                                        placeholder="Surname Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-5 form-group">
                                                <label for="firstName" class="control-label required">FIRST
                                                    NAME:</label>
                                                <input
                                                        type="text" name="etl_firstName" class="form-control"
                                                        id="etl_firstName"
                                                        placeholder="First Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="lastName" class="control-label">OTHER NAMES:</label>
                                                <input
                                                        type="text" name="etl_lastName" class="form-control"
                                                        id="etl_lastName"
                                                        placeholder="OTHER NAMES"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>

                                            <div class="col-md-6 form-group">
                                                <label for="salary" class="control-label required">MONTHLY SALARY
                                                    :</label>
                                                <input
                                                        type="number" name="salary" class="form-control"
                                                        id="salary"
                                                        placeholder="MONTHLY SALARY"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="dateOfAppointment" class="control-label  required">DATE OF
                                                    APPOINTMENT:</label>
                                                <input type="text" readonly="readonly"
                                                       name="dateOfAppointment"
                                                       class="form-control datepicker"
                                                       id="dateOfAppointment"
                                                       placeholder="Date Of Appointment">

                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="taxNumber" class="control-label">TAX NUMBER
                                                    :</label>
                                                <input
                                                        type="text" name="taxNumber" class="form-control"
                                                        id="taxNumber"
                                                        placeholder="TAX NUMBER"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>

                                        </div>

                                    </div><!--End of row-->

                                </div>
                                <div class="row" id="other_identification_details">
                                    <div class="col-md-2">
                                        OTHER IDENTIFICATION DETIALS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="otherIdType" class="control-label">IDENTIFICATION
                                                    TYPE:</label>
                                                <select id="otherIdType" name="otherIdType" class="form-control">
                                                    <option value="">Select IDENTIFICATION TYPE ...</option>
                                                    <option value="PASSPORT">Passport No</option>
                                                    <option value="VOTER">Voter ID</option>
                                                    <option value="PENNO">NHIS</option>
                                                    <option value="DRIVER">Driver's License</option>
                                                    <option value="POLICY_NO">Policy Number</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="otherIdentificationNumber"
                                                       class="control-label">IDENTIFICATION NUMBER:</label>
                                                <input
                                                        type="text" name="otherIdentificationNumber"
                                                        class="form-control"
                                                        id="otherIdentificationNumber"
                                                        placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="maiden-details">
                                    <div class="col-md-2">
                                        PREVIOUS NAME / MAIDEN NAME
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="MsurName" class="control-label">SURNAME:</label> <input
                                                    type="text" name="MsurName" class="form-control" id="MsurName"
                                                    placeholder="Surname Name"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="MfirstName" class="control-label">FIRST NAME:</label>
                                                <input
                                                        type="text" name="MfirstName" class="form-control"
                                                        id="MfirstName"
                                                        placeholder="First Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->

                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="M_otherName" class="control-label">OTHER NAMES:</label>
                                                <input
                                                        type="text" name="M_otherName" class="form-control"
                                                        id="M_otherName"
                                                        placeholder="OTHER NAMES"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="contact-details">
                                    <div class="col-md-2">
                                        OTHER DETAILS *
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <label for="dateOfBirth" class="control-label required">Date Of
                                                    Birth:</label> <input type="text" readonly="readonly"
                                                                          name="etl_dateOfBirth"
                                                                          onchange="autoAgeCalculation()"
                                                                          class="form-control datepicker"
                                                                          id="etl_dateOfBirth"
                                                                          placeholder="Date Of Birth">
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="age" class="control-label required">AGE:</label> <input
                                                    type="text" name="age" class="form-control" id="age" min="18"
                                                    readonly
                                                    placeholder="age">
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="etl_gender" class="control-label required">Gender:</label>
                                                <select
                                                        name="etl_gender" id="etl_gender" class="form-control">
                                                    <option value="">Select gender...</option>
                                                    <c:forEach var="gender" items="${genders}">
                                                        <option value="${gender.name}">
                                                                ${gender.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">

                                            <div class="col-md-4 form-group">
                                                <label for="etl_maritalStatus"
                                                       class="control-label required">Current Marital
                                                    Status:</label> <select name="etl_maritalStatus"
                                                                            id="etl_maritalStatus"
                                                                            class="form-control">
                                                <option value="">Select marital status...</option>
                                                <c:forEach var="maritalStatus" items="${maritalStatuses}">
                                                    <option value="${maritalStatus.name}">
                                                            ${maritalStatus.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="maritalStatusAtDoe"
                                                       class="control-label required">Marital
                                                    Status At Date of Entry:</label> <select
                                                    name="maritalStatusAtDoe"
                                                    id="maritalStatusAtDoe"
                                                    class="form-control">
                                                <option value="">Select marital status...</option>
                                                <c:forEach var="maritalStatus" items="${maritalStatuses}">
                                                    <option value="${maritalStatus.name}">
                                                            ${maritalStatus.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="staffNumber" class="control-label required">STAFF
                                                    NUMBER:</label> <input
                                                    type="text" name="staffNumber" class="form-control" id="staffNumber"
                                                    placeholder="Staff Number">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="nationality-details">
                                    <div class="col-md-2">
                                        NATIONALITY
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <label for="nationality"
                                                       class="control-label required">NATIONALITY:</label>
                                                <input
                                                        type="text" name="nationality" class="form-control"
                                                        id="nationality"
                                                        placeholder="Nationality"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="country" class="control-label required">COUNTRY:</label>
                                                <select
                                                        name="etl_country" id="etl_country" class="form-control">
                                                    <option value="">Select country...</option>
                                                    <c:forEach var="country" items="${countries}">
                                                        <option value="${country.id}">
                                                                ${country.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>


                                    </div>
                                </div>
                                <div class="row" id="address-details">
                                    <div class="col-md-2">
                                        ADDRESSES
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <label for="postalAddress" class="control-label">POSTAL
                                                    ADDRESS:</label> <input
                                                    type="text" name="postalAddress" class="form-control"
                                                    id="postalAddress"
                                                    placeholder="Postal Address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="homeAddress" class="control-label">RESIDENTIAL HOME
                                                    ADDRESS:</label> <input
                                                    type="text" name="homeAddress" class="form-control"
                                                    id="homeAddress"
                                                    placeholder="Permanent Home Address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>

                                            <div class="col-md-4 form-group">
                                                <label for="postalTown" class="control-label">TOWN:</label> <input
                                                    type="text" name="postalTown" class="form-control"
                                                    id="postalTown"
                                                    placeholder="Postal Town"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <label for="building" class="control-label">BUILDING:</label> <input
                                                    type="text" name="building" class="form-control"
                                                    id="building"
                                                    placeholder="Building"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <label for="road" class="control-label">ROAD:</label> <input
                                                    type="text" name="road" class="form-control"
                                                    id="road"
                                                    placeholder="Road"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row" id="current-contact-details">
                                    <div class="col-md-2">
                                        CURRENT CONTACT DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="etl_phoneNumber" class="control-label required">MOBILE PHONE
                                                    NUMBER
                                                </label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%"></select>
                                                    <input type="text" name="etl_phoneNumber" class="form-control"
                                                           id="etl_phoneNumber" placeholder="Phone Number"
                                                           style="width: 75%"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="secondaryPhoneNumber" class="control-label">SECONDARY PHONE
                                                    NUMBER
                                                </label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%"></select>
                                                    <input type="text" name="secondaryPhoneNumber" class="form-control"
                                                           id="secondaryPhoneNumber" placeholder="Secondary phone"
                                                           style="width: 75%"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="otherContact" class="control-label">OTHER
                                                    CONTACT
                                                </label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%"></select>
                                                    <input type="text" name="otherContact" class="form-control"
                                                           id="otherContact" placeholder="Other Contacts"
                                                           style="width: 75%"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="etl_emailAddress"
                                                       class="control-label required">EMAIL:</label>
                                                <input
                                                        type="email" name="etl_emailAddress" class="form-control"
                                                        id="etl_emailAddress"
                                                        placeholder="Email"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div>
                                    </div>
                                    <%-- <div class="row">
                                         <div class="col-md-6 form-group">
                                             <label for="etl_tierAccount" class="control-label">DO YOU HAVE A
                                                 TIER
                                                 ACCOUNT?
                                                 :</label> <select name="etl_tierAccount"
                                                                   id="etl_tierAccount"
                                                                   class="form-control">
                                             <option value="NO">NO</option>
                                             <option value="YES">YES</option>
                                         </select>
                                         </div>
                                     </div>--%>
                                </div>


                                <div class="row" id="place-of-birth">
                                    <div class="col-md-2">
                                        PLACE OF BIRTH
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-3 form-group">
                                                <label for="pdistrict" class="control-label">DISTRICT:</label>
                                                <select
                                                        name="pdistrict" id="pdistrict" class="form-control"
                                                        onchange="getTraditionalAuthorities();">
                                                    <option value="">Select district...</option>
                                                    <c:forEach var="distict" items="${districtsList}">
                                                        <option value="${distict.id}">
                                                                ${distict.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>

                                            </div>
                                            <div class="col-md-4 form-group" id="placeOfBirthTraditionalAuthorityDiv">


                                            </div>
                                            <div class="col-md-4 form-group" id="placeVillagesTraditionalAuthority">


                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="parmanent-home-address">
                                    <div class="col-md-2">
                                        PARMANENT HOME ADDRESS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-3 form-group">
                                                <label for="parmentDistrict" class="control-label">DISTRICT:</label>
                                                <select
                                                        name="parmentDistrict" id="parmentDistrict" class="form-control"
                                                        onchange="getParmanentTraditionalAuthorities();">
                                                    <option value="">Select district...</option>
                                                    <c:forEach var="distict" items="${districtsList}">
                                                        <option value="${distict.id}">
                                                                ${distict.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>


                                            </div>
                                            <div class="col-md-4 form-group" id="parmanentTraditionalAuthorityDiv">


                                            </div>
                                            <div class="col-md-4 form-group" id="parmentVillageDiv">


                                            </div>

                                        </div>
                                    </div>
                                </div>


                                <div class="row" id="parent-details">
                                    <div class="col-md-2">
                                        NEXT OF KIN DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="nextOfKinName" class="control-label">NAME:</label>
                                                <input
                                                        type="text" name="nextOfKinName" class="form-control"
                                                        id="nextOfKinName"
                                                        placeholder="Name of Next of Kin"
                                                        oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="nextOfKinRelationship"
                                                       class="control-label">Relationship
                                                    To Applicant
                                                </label> <select
                                                    name="nextOfKinRelationship" id="nextOfKinRelationship"
                                                    class="form-control">
                                                <option value="">Select...</option>
                                                <option value="wife">WIFE
                                                </option>
                                                <option value="husband">HUSBAND
                                                </option>
                                                <option value="daughter">DAUGHTER
                                                </option>
                                                <option value="son">SON
                                                </option>
                                                <option value="mother">MOTHER
                                                </option>
                                                <option value="father">FATHER
                                                </option>
                                                <option value="brother">BROTHER
                                                </option>
                                                <option value="sister">SISTER
                                                </option>
                                                <option value="other">OTHER
                                                </option>
                                            </select>
                                            </div>

                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="nextOfKinAddress" class="control-label">
                                                    ADDRESS:</label> <input
                                                    type="text" name="nextOfKinAddress" class="form-control"
                                                    id="nextOfKinAddress"
                                                    placeholder="Next Of Kin Address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="nextOfKinAllocation" class="control-label">PERCENTAGE
                                                    ALLOCATION
                                                    TO
                                                    KIN
                                                    (To Total
                                                    100%):</label> <input
                                                    type="number" name="nextOfKinAllocation" class="form-control"
                                                    id="nextOfKinAllocation" min="0" max="100"
                                                    placeholder="Allocation">
                                            </div>

                                        </div><!--end of row-->
                                        <!--end of row-->
                                    </div>
                                </div>
                                <div class="row" id="beneficiary-details">
                                    <div class="col-md-2">
                                        BENEFICIARY DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="beneficiaryName" class="control-label required">NAME
                                                    OF
                                                    BENEFICIARY:</label> <input
                                                    type="text" name="beneficiaryName" class="form-control"
                                                    id="beneficiaryName"
                                                    placeholder="Beneficiary Name"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benDateOfBirth" class="control-label required">Date
                                                    Of
                                                    Birth:</label> <input type="text" readonly="readonly"
                                                                          name="benDateOfBirth"
                                                                          class="form-control datepicker"
                                                                          id="benDateOfBirth"
                                                                          placeholder="Date Of Birth">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benRelationship" class="control-label required">Relationship
                                                    To Applicant
                                                </label> <select
                                                    name="benRelationship" id="benRelationship"
                                                    class="form-control">
                                                <option value="">Select...</option>
                                                <option value="wife">WIFE
                                                </option>
                                                <option value="husband">HUSBAND
                                                </option>
                                                <option value="daughter">DAUGHTER
                                                </option>
                                                <option value="son">SON
                                                </option>
                                                <option value="mother">MOTHER
                                                </option>
                                                <option value="father">FATHER
                                                </option>
                                                <option value="brother">BROTHER
                                                </option>
                                                <option value="sister">SISTER
                                                </option>
                                                <option value="other">OTHER
                                                </option>
                                            </select>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPostalAddress" class="control-label">POSTAL
                                                    ADDRESS
                                                    OF
                                                    BENEFICARY</label> <input
                                                    type="text" name="benPostalAddress" class="form-control"
                                                    id="benPostalAddress"
                                                    placeholder="Postal address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benEmail" class="control-label">EMAIL ADDRESS OF
                                                    BENEFICARY:</label> <input
                                                    type="email" name="benEmail" class="form-control"
                                                    id="benEmail"
                                                    placeholder="Email"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="phoneNumber" class="control-label">BENEFICIARY
                                                    MOBILE
                                                    PHONE NUMBER:</label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%;"></select>
                                                    <input type="text" name="benPhoneNumber"
                                                           class="form-control"
                                                           id="benPhoneNumber" placeholder="Phone Number"
                                                           style="width: 75%;"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benAllocation" class="control-label required">PERCENTAGE
                                                    ALLOCATION
                                                    TO
                                                    BENEFICARY
                                                    (To Total
                                                    100%):</label> <input
                                                    type="number" name="benAllocation" class="form-control"
                                                    id="benAllocation" min="0" max="100"
                                                    onchange="allocaton('benAllocation')"
                                                    placeholder="Allocation">
                                            </div>
                                            <div>
                                                <button class="btn btn-success" id="addMore" type="button"><i
                                                        class="glyphicon glyphicon-plus"></i> ADD BENEFICIARY
                                                </button>
                                            </div>
                                        </div><!--end of row-->
                                    </div>
                                </div>
                                <div class="row" id="memberBen1">
                                    <div class="col-md-2">
                                        BENEFICIARY DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="beneficiaryName1" class="control-label required">NAME
                                                    OF
                                                    BENEFICIARY:</label> <input
                                                    type="text" name="beneficiaryName1" class="form-control"
                                                    id="beneficiaryName1"
                                                    placeholder="Beneficiary Name"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benDateOfBirth1" class="control-label required">Date
                                                    Of
                                                    Birth:</label> <input type="text" readonly="readonly"
                                                                          name="benDateOfBirth1"
                                                                          class="form-control datepicker"
                                                                          id="benDateOfBirth1"
                                                                          placeholder="Date Of Birth">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benRelationship1" class="control-label required">RELATIONSHIP
                                                    OF
                                                    BENEFICIARY TO
                                                    CONTRIBUTOR:</label> <select
                                                    name="benRelationship1" id="benRelationship1"
                                                    class="form-control"
                                            <%-- oninput="this.value=this.value.toUpperCase()"--%>>
                                                <option value="">Select...</option>
                                                <option value="wife">WIFE
                                                </option>
                                                <option value="husband">HUSBAND
                                                </option>
                                                <option value="daughter">DAUGHTER
                                                </option>
                                                <option value="son">SON
                                                </option>
                                                <option value="mother">MOTHER
                                                </option>
                                                <option value="father">FATHER
                                                </option>
                                                <option value="brother">BROTHER
                                                </option>
                                                <option value="sister">SISTER
                                                </option>
                                                <option value="other">OTHER
                                                </option>
                                            </select>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPostalAddress1" class="control-label required">POSTAL
                                                    ADDRESS
                                                    OF
                                                    BENEFICARY</label> <input
                                                    type="text" name="benPostalAddress1" class="form-control"
                                                    id="benPostalAddress1"
                                                    placeholder="Postal address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benEmail1" class="control-label">EMAIL ADDRESS OF
                                                    BENEFICARY:</label> <input
                                                    type="email" name="benEmail1" class="form-control"
                                                    id="benEmail1"
                                                    placeholder="Email"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <%--<div class="col-md-6 form-group">--%>
                                            <%--<label for="benPhoneNumber" class="control-label">CELL PHONE--%>
                                            <%--NUMBER OF--%>
                                            <%--BENEFICARY</label> <input--%>
                                            <%--type="text" name="benPhoneNumber" class="form-control" id="benPhoneNumber"--%>
                                            <%--placeholder="Cell Phone">--%>
                                            <%--</div>--%>
                                            <div class="col-md-6 form-group">
                                                <label for="benPhoneNumber1" class="control-label">BENEFICIARY
                                                    MOBILE
                                                    PHONE NUMBER :</label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%;"></select>
                                                    <input type="text" name="benPhoneNumber1"
                                                           class="form-control"
                                                           id="benPhoneNumber1" placeholder="Phone Number"
                                                           style="width: 75%;"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benAllocation1" class="control-label required">PERCENTAGE
                                                    ALLOCATION
                                                    TO
                                                    BENEFICARY
                                                    (To Total
                                                    100%):</label> <input
                                                    type="number" name="benAllocation1" class="form-control"
                                                    id="benAllocation1" min="0" max=""
                                                    onchange="allocaton('benAllocation1')"
                                                    placeholder="Allocation">
                                            </div>
                                            <div>
                                                <button class="btn btn-success" id="addMore1" type="button"><i
                                                        class="glyphicon glyphicon-plus"></i>ADD BENEFICIARY
                                                </button>
                                            </div>
                                            <div>
                                                <button class="btn btn-danger" id="removeBen1" type="button"><i
                                                        class="glyphicon glyphicon-minus"></i> Remove
                                                </button>
                                            </div>
                                        </div><!--end of row-->
                                    </div>
                                </div>
                                <div class="row" id="memberBen2">
                                    <div class="col-md-2">
                                        BENEFICIARY DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="beneficiaryName2" class="control-label required">NAME
                                                    OF
                                                    BENEFICIARY:</label> <input
                                                    type="text" name="beneficiaryName2" class="form-control"
                                                    id="beneficiaryName2"
                                                    placeholder="Beneficiary Name"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benDateOfBirth2" class="control-label required">Date
                                                    Of
                                                    Birth:</label> <input type="text" readonly="readonly"
                                                                          name="benDateOfBirth2"
                                                                          class="form-control datepicker"
                                                                          id="benDateOfBirth2"
                                                                          placeholder="Date Of Birth">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benRelationship2" class="control-label required">RELATIONSHIP
                                                    OF
                                                    BENEFICIARY TO
                                                    CONTRIBUTOR:</label>
                                                <select
                                                        name="benRelationship2" id="benRelationship2"
                                                        class="form-control"
                                                <%--  oninput="this.value=this.value.toUpperCase()"--%>>
                                                    <option value="">Select...</option>
                                                    <option value="wife">WIFE
                                                    </option>
                                                    <option value="husband">HUSBAND
                                                    </option>
                                                    <option value="daughter">DAUGHTER
                                                    </option>
                                                    <option value="son">SON
                                                    </option>
                                                    <option value="mother">MOTHER
                                                    </option>
                                                    <option value="father">FATHER
                                                    </option>
                                                    <option value="brother">BROTHER
                                                    </option>
                                                    <option value="sister">SISTER
                                                    </option>
                                                    <option value="other">OTHER
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPostalAddress2" class="control-label required">POSTAL
                                                    ADDRESS
                                                    OF
                                                    BENEFICARY</label> <input
                                                    type="text" name="benPostalAddress2" class="form-control"
                                                    id="benPostalAddress2"
                                                    placeholder="Postal address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benEmail2" class="control-label">EMAIL ADDRESS OF
                                                    BENEFICARY:</label> <input
                                                    type="email" name="benEmail2" class="form-control"
                                                    id="benEmail2"
                                                    placeholder="Email"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPhoneNumber2" class="control-label">BENEFICIARY
                                                    MOBILE
                                                    PHONE NUMBER :</label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%;"></select>
                                                    <input type="text" name="benPhoneNumber2"
                                                           class="form-control"
                                                           id="benPhoneNumber2" placeholder="Phone Number"
                                                           style="width: 75%;"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benAllocation2" class="control-label required">PERCENTAGE
                                                    ALLOCATION
                                                    TO
                                                    BENEFICARY
                                                    (To Total
                                                    100%):</label> <input
                                                    type="number" name="benAllocation2" class="form-control"
                                                    id="benAllocation2" min="0" max=""
                                                    onchange="allocaton('benAllocation2')"
                                                    placeholder="Allocation">
                                            </div>
                                            <div>
                                                <button class="btn btn-success" id="addMore2" type="button"><i
                                                        class="glyphicon glyphicon-plus"></i> ADD BENEFICIARY
                                                </button>
                                            </div>
                                            <div>
                                                <button class="btn btn-danger" id="removeBen2" type="button"><i
                                                        class="glyphicon glyphicon-minus"></i> Remove
                                                </button>
                                            </div>
                                        </div><!--end of row-->
                                    </div>
                                </div>
                                <div class="row" id="memberBen3">
                                    <div class="col-md-2">
                                        BENEFICIARY DETAILS
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="beneficiaryName3" class="control-label required">NAME
                                                    OF
                                                    BENEFICIARY:</label> <input
                                                    type="text" name="beneficiaryName3" class="form-control"
                                                    id="beneficiaryName3"
                                                    placeholder="Beneficiary Name"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benDateOfBirth3" class="control-label required">Date
                                                    Of
                                                    Birth:</label> <input type="text" readonly="readonly"
                                                                          name="benDateOfBirth3"
                                                                          class="form-control datepicker"
                                                                          id="benDateOfBirth3"
                                                                          placeholder="Date Of Birth">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benRelationship3" class="control-label required">RELATIONSHIP
                                                    OF
                                                    BENEFICIARY TO
                                                    CONTRIBUTOR:</label>
                                                <select
                                                        name="benRelationship3" id="benRelationship3"
                                                        class="form-control"
                                                <%-- oninput="this.value=this.value.toUpperCase()"--%>>
                                                    <option value="">Select...</option>
                                                    <option value="wife">WIFE
                                                    </option>
                                                    <option value="husband">HUSBAND
                                                    </option>
                                                    <option value="daughter">DAUGHTER
                                                    </option>
                                                    <option value="son">SON
                                                    </option>
                                                    <option value="mother">MOTHER
                                                    </option>
                                                    <option value="father">FATHER
                                                    </option>
                                                    <option value="brother">BROTHER
                                                    </option>
                                                    <option value="sister">SISTER
                                                    </option>
                                                    <option value="other">OTHER
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPostalAddress3" class="control-label required">POSTAL
                                                    ADDRESS
                                                    OF
                                                    BENEFICARY</label> <input
                                                    type="text" name="benPostalAddress3" class="form-control"
                                                    id="benPostalAddress3"
                                                    placeholder="Postal address"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                        </div><!--end of row-->
                                        <div class="row">
                                            <div class="col-md-6 form-group">
                                                <label for="benEmail3" class="control-label">EMAIL ADDRESS OF
                                                    BENEFICARY:</label> <input
                                                    type="email" name="benEmail3" class="form-control"
                                                    id="benEmail3"
                                                    placeholder="Email"
                                                    oninput="this.value=this.value.toUpperCase()">
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benPhoneNumber3" class="control-label">BENEFICIARY
                                                    MOBILE
                                                    PHONE NUMBER :</label>
                                                <div class="form-inline">
                                                    <select class="form-control  etl-country-code pull-left"
                                                            name="country-code" style="width: 25%;"></select>
                                                    <input type="text" name="benPhoneNumber3"
                                                           class="form-control"
                                                           id="benPhoneNumber3" placeholder="Phone Number"
                                                           style="width: 75%;"
                                                           oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <label for="benAllocation3" class="control-label required">PERCENTAGE
                                                    ALLOCATION
                                                    TO
                                                    BENEFICARY
                                                    (To Total
                                                    100%):</label> <input
                                                    type="number" name="benAllocation3" class="form-control"
                                                    id="benAllocation3" min="0" max=""
                                                    placeholder="Allocation">
                                            </div>
                                            <div>
                                                <button class="btn btn-danger" id="removeBen3" type="button"><i
                                                        class="glyphicon glyphicon-minus"></i> Remove
                                                </button>
                                            </div>
                                        </div><!--end of row-->
                                    </div>
                                </div>
                                <div class="row" id="captch-details">
                                    <div class="col-md-6 form-group">
                                        <div class="col-md-6 CaptchaImage" id="newEtlMemberCaptchaImage">
                                            <img src="<%=request.getContextPath()%>/captcha" alt="captcha"/>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Enter the text to your left
                                                here</label>
                                            <input type="text" class="form-control"
                                                   name="newEtlMemberCaptchaImage"
                                                   id="etlMemberCaptchaChars"/>
                                        </div>
                                        <button class="btn btn-primary btn-block">REGISTER</button>
                                    </div>
                                </div><!--end of row-->


                            </form>
                        </div>


                    </div>
                </div>
                <%-- <div id="existing-member" class="tab-pane fade in">
                     <div class="col-sm-6 col-md-4 col-lg-4"> <!-- required for floating -->
                         Details for existing member
                     </div>
                 </div>
 --%>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    document.getElementById('member-show-form').hidden = true;
    document.getElementById('parmentVillageDiv').hidden = true;
    document.getElementById('parmanentTraditionalAuthorityDiv').hidden = true;
    document.getElementById('placeOfBirthTraditionalAuthorityDiv').hidden = true;
    document.getElementById('placeVillagesTraditionalAuthority').hidden = true;


    document.getElementById("etlIdNumber").onkeypress = function (event) {
        if (event.keyCode == 13 || event.which == 13) {
            var employerProdNo = document.getElementById("employerProdNo").value;
            var idType = 'NATIONAL';
            var idnumber = document.getElementById("etlIdNumber").value;


            start_wait();
            $.ajax({
                url: $('#base_url').val() + 'admin',
                type: 'post',
                data: {
                    ACTION: 'CHECK_IF_IDNUMBER_EXIST',
                    employerProdNo: employerProdNo,
                    idNumber: idnumber,
                    idType: idType
                },
                dataType: 'json',
                success: function (json) {
                    stop_wait();
                    if (json.success) {
                        document.getElementById('employer-details').hidden = false;
                        document.getElementById('personal_details').hidden = false;
                        document.getElementById('maiden-details').hidden = false;
                        document.getElementById('other_identification_details').hidden = false;
                        document.getElementById('contact-details').hidden = false;
                        document.getElementById('nationality-details').hidden = false;
                        document.getElementById('place-of-birth').hidden = false;
                        document.getElementById('parmanent-home-address').hidden = false;
                        document.getElementById('address-details').hidden = false;
                        document.getElementById('parent-details').hidden = false;
                        document.getElementById('beneficiary-details').hidden = false;
                        document.getElementById('current-contact-details').hidden = false;
                        document.getElementById('memberBen1').hidden = false;
                        document.getElementById('captch-details').hidden = false;
                        document.getElementById('ssnit-details').hidden = false;
                        document.getElementById('explainEnter').style.display = 'none';


                    } else {
                        bootbox.alert("<p class=\"text-center\">" + json.message + "</p>");

                    }

                }
            });
        }
    };

    function getTraditionalAuthorities() {


        $("#placeOfBirthTraditionalAuthorityDiv").show();

        var districtId = document.getElementById('pdistrict').value;

        $.ajax({
            url: $('#base_url').val() + 'admin',
            type: 'post',
            data: {
                ACTION: 'GET_TRADITIONAL_AUTHORITIES',
                districtId: districtId
            },
            dataType: 'json',
            success: function (json) {
                if (json.success) {
                    json = $.parseJSON(json.data);

                    var combo = "<label for=\"pTraditionalAuthority\" class=\"control-label\">TRADITIONAL\n" +
                        "                                                    AUTHORITY:</label><select id=\"pTraditionalAuthority\" name=\"pTraditionalAuthority\" onchange=\"getVillages();\" class=\"form-control\"><option>--Select traditional authority--</option>";
                    $.each(json, function (key, value) {
                        if (key == 'rows') {
                            for (var i = 0; i < json.rows.length; i++) {
                                var row = json.rows[i];


                                combo = combo + "<option value = " + row['id'] + ">" + row['name'] + "</option>";

                                array = json.rows;
                            }

                            combo = combo + "</select>";

                        }
                    });
                    $('#placeOfBirthTraditionalAuthorityDiv').html(combo);
                } else {
                    stop_wait();
                    bootbox.alert('<p class="text-center">' + json.message + '</p>');
                }
            }
        });


    }

    function getVillages() {


        $("#placeVillagesTraditionalAuthority").show();

        var authorityId = document.getElementById('pTraditionalAuthority').value;

        $.ajax({
            url: $('#base_url').val() + 'admin',
            type: 'post',
            data: {
                ACTION: 'GET_VILLAGES',
                authorityId: authorityId
            },
            dataType: 'json',
            success: function (json) {
                if (json.success) {
                    json = $.parseJSON(json.data);

                    var combo = "<label for=\"pVillage\" class=\"control-label\">VILLAGE:</label><select id=\"pVillage\" name=\"pVillage\" class=\"form-control\"><option>--Select village--</option>";
                    $.each(json, function (key, value) {
                        if (key == 'rows') {
                            for (var i = 0; i < json.rows.length; i++) {
                                var row = json.rows[i];


                                combo = combo + "<option value = " + row['id'] + ">" + row['name'] + "</option>";

                                array = json.rows;
                            }

                            combo = combo + "</select>";

                        }
                    });
                    $('#placeVillagesTraditionalAuthority').html(combo);
                } else {
                    stop_wait();
                    bootbox.alert('<p class="text-center">' + json.message + '</p>');
                }
            }
        });


    }


    function getParmanentTraditionalAuthorities() {


        $("#parmanentTraditionalAuthorityDiv").show();

        var districtId = document.getElementById('parmentDistrict').value;

        $.ajax({
            url: $('#base_url').val() + 'admin',
            type: 'post',
            data: {
                ACTION: 'GET_TRADITIONAL_AUTHORITIES',
                districtId: districtId
            },
            dataType: 'json',
            success: function (json) {
                if (json.success) {
                    json = $.parseJSON(json.data);

                    var combo = "<label for=\"parmentTraditionalAuthority\" class=\"control-label\">TRADITIONAL\n" +
                        "                                                    AUTHORITY:</label><select id=\"parmentTraditionalAuthority\" name=\"parmentTraditionalAuthority\" onchange=\"getParmanentVillages();\" class=\"form-control\"><option>--Select traditional authority--</option>";
                    $.each(json, function (key, value) {
                        if (key == 'rows') {
                            for (var i = 0; i < json.rows.length; i++) {
                                var row = json.rows[i];


                                combo = combo + "<option value = " + row['id'] + ">" + row['name'] + "</option>";

                                array = json.rows;
                            }

                            combo = combo + "</select>";

                        }
                    });
                    $('#parmanentTraditionalAuthorityDiv').html(combo);
                } else {
                    stop_wait();
                    bootbox.alert('<p class="text-center">' + json.message + '</p>');
                }
            }
        });


    }

    function getParmanentVillages() {


        $("#parmentVillageDiv").show();

        var authorityId = document.getElementById('parmentTraditionalAuthority').value;

        $.ajax({
            url: $('#base_url').val() + 'admin',
            type: 'post',
            data: {
                ACTION: 'GET_VILLAGES',
                authorityId: authorityId
            },
            dataType: 'json',
            success: function (json) {
                if (json.success) {
                    json = $.parseJSON(json.data);

                    var combo = "<label for=\"parmentVillage\" class=\"control-label\">VILLAGE:</label><select id=\"parmentVillage\" name=\"parmentVillage\" class=\"form-control\"><option>--Select village--</option>";
                    $.each(json, function (key, value) {
                        if (key == 'rows') {
                            for (var i = 0; i < json.rows.length; i++) {
                                var row = json.rows[i];


                                combo = combo + "<option value = " + row['id'] + ">" + row['name'] + "</option>";

                                array = json.rows;
                            }

                            combo = combo + "</select>";

                        }
                    });
                    $('#parmentVillageDiv').html(combo);
                } else {
                    stop_wait();
                    bootbox.alert('<p class="text-center">' + json.message + '</p>');
                }
            }
        });


    }
</script>


<%--
<jsp:include page="includes/partial/footer.jsp" />--%>
