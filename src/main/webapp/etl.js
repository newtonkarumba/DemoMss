/**
 *1. Fetch sponsor details
 * 2. Check ID NUMBER IF EXISTS
 * 3. Validate
 * 4. Register
 */
let total=0;
(function () {
    winReady()
})();

function winReady(){
    getCountriesHTML(function (content) {
        $('#etl_country').html(content);
        $('#etl_country_of_Birth').html(content);

    }, "Ghana")
    loadCountryCodes()

    //load districts
    getDistrictsHtml(function (content) {
        // console.log(content)
        $('#pdistrict').html(content);
        $('#parmentDistrict').html(content);
    });
}

function showBen(id) {
    totalAllocationReached(function () {
        let c = $('#' + id);
        c.addClass('w3-animate-zoom')
        c.removeClass('w3-animate-right')
        c.css('display', 'none');
        c.css('display', 'block');
        window.location.href = '#' + id;
    },function () {
        if (total===100){
            err('Maximum allocation reached')
        }
    })
}

function removeBen(id, num) {
    $('#beneficiaryName' + num).val('')
    $('#benDateOfBirth' + num).val('')
    $('#benRelationship' + num).val('')
    $('#benPostalAddress' + num).val('')
    $('#benEmail' + num).val('')
    $('#benAllocation' + num).val(0)
    let c = $('#' + id);
    c.removeClass('w3-animate-zoom')
    c.addClass('w3-animate-right')
    c.css('display', 'none');
}

function autoAgeCalculation() {
    let ageDate;
    let dateNow;

    try {
        let birthDay = document.getElementById("dateOfBirth").value;
        let dateofBirthYear = new Date(birthDay).getUTCFullYear();
        let todayYearDate = new Date().getUTCFullYear();
        ageDate = todayYearDate - dateofBirthYear;


    } catch (e) {
        ageDate = new Date();
        ageDate = Math.abs(ageDate.getUTCFullYear() - 1970);
    }
    document.getElementById('age').value = ageDate;
}

function CHECK_IF_IDNUMBER_EXIST() {
    let employerProdNo = getField("employerProdNo").val();
    let idType = 'NATIONAL';
    let idNumber = getField("etlIdNumber").val();

    let isTrue = false;
    if (employerProdNo && idNumber)
        $.ajax({
            url: `${baseUrl}/api/CHECK_IF_IDNUMBER_EXIST/${employerProdNo}/${idNumber}/${idType}`,
            dataType: 'json',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            async: false,
            beforeSend: function () {
                $('#pg1').css('display', 'block');
            },
            success: function (data) {
                if (data.success) {
                    $('#pg1').css('display', 'none');
                    isTrue = true;
                } else {
                    $('#pg1').css('display', 'none');
                    isTrue = false;
                }

                if (!isTrue)
                    err("ID Number already in use");
            },
            error: function (jqXHR, textStatus, exception) {
                $('#pg1').css('display', 'none');
                isTrue = false;
            }
        });


    return isTrue;
}

function FETCH_SPONSOR_DETAILS() {
    let sponsorRef = $('#sponsorId').val();
    let isTrue = false;
    if (sponsorRef)
        $.ajax({
            url: `${baseUrl}/api/get-sponsor-details/0/${sponsorRef}`,
            dataType: 'json',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            async: false,
            beforeSend: function () {
                $('#pg').css('display', 'block');
                $('#mimsg').text("")
            },
            success: function (data) {
                if (data.success) {
                    let obj = data.data;
                    let schemeName = obj.schemeName;
                    $('#pg').css('display', 'none');

                    getField('employer').val(obj.name)
                    getField('employerProdNo').val(obj.employerRefNo)
                    getField('employerAddress').val(obj.postalAddress)
                    getField('schemeproduct').val(obj.product)
                    isTrue = true;
                } else {
                    $('#pg').css('display', 'none');
                    $('#mimsg').text("Failed to get employer details")
                    isTrue = false;
                }

            },
            error: function (jqXHR, textStatus, exception) {
                $('#pg').css('display', 'none');
                $('#mimsg').text("Failed to get employer details")
                isTrue = false;
            }
        });

    return isTrue;
}

function loadCountryCodes() {
    $(".etl-country-code").html(
        `<option data-countryCode='GH' value='+233' Selected>Ghana (+233)</option>
        <option data-countryCode='US' value='+1'>USA (+1)</option>
        <option data-countryCode='DZ' value='+213'>Algeria (+213)</option>
        <option data-countryCode='AD' value='+376'>Andorra (+376)</option>
        <option data-countryCode='AO' value='+244'>Angola (+244)</option>
        <option data-countryCode='AI' value='+1264'>Anguilla (+1264)</option>
        <option data-countryCode='AG' value='+1268'>Antigua &amp; Barbuda (+1268)</option>
        <option data-countryCode='AR' value='+54'>Argentina (+54)</option>
        <option data-countryCode='AM' value='+374'>Armenia (+374)</option>
        <option data-countryCode='AW' value='+297'>Aruba (+297)</option>
        <option data-countryCode='AU' value='+61'>Australia (+61)</option>
        <option data-countryCode='AT' value='+43'>Austria (+43)</option>
        <option data-countryCode='AZ' value='+994'>Azerbaijan (+994)</option>
        <option data-countryCode='BS' value='+1242'>Bahamas (+1242)</option>
        <option data-countryCode='BH' value='+973'>Bahrain (+973)</option>
        <option data-countryCode='BD' value='+880'>Bangladesh (+880)</option>
        <option data-countryCode='BB' value='+1246'>Barbados (+1246)</option>
        <option data-countryCode='BY' value='+375'>Belarus (+375)</option>
        <option data-countryCode='BE' value='+32'>Belgium (+32)</option>
        <option data-countryCode='BZ' value='+501'>Belize (+501)</option>
        <option data-countryCode='BJ' value='+229'>Benin (+229)</option>
        <option data-countryCode='BM' value='+1441'>Bermuda (+1441)</option>
        <option data-countryCode='BT' value='+975'>Bhutan (+975)</option>
        <option data-countryCode='BO' value='+591'>Bolivia (+591)</option>
        <option data-countryCode='BA' value='+387'>Bosnia Herzegovina (+387)</option>
        <option data-countryCode='BW' value='+267'>Botswana (+267)</option>
        <option data-countryCode='BR' value='+55'>Brazil (+55)</option>
        <option data-countryCode='BN' value='+673'>Brunei (+673)</option>
        <option data-countryCode='BG' value='+359'>Bulgaria (+359)</option>
        <option data-countryCode='BF' value='+226'>Burkina Faso (+226)</option>
        <option data-countryCode='BI' value='+257'>Burundi (+257)</option>
        <option data-countryCode='KH' value='+855'>Cambodia (+855)</option>
        <option data-countryCode='CM' value='+237'>Cameroon (+237)</option>
        <option data-countryCode='CA' value='+1'>Canada (+1)</option>
        <option data-countryCode='CV' value='+238'>Cape Verde Islands (+238)</option>
        <option data-countryCode='KY' value='+1345'>Cayman Islands (+1345)</option>
        <option data-countryCode='CF' value='+236'>Central African Republic (+236)</option>
        <option data-countryCode='CL' value='+56'>Chile (+56)</option>
        <option data-countryCode='CN' value='+86'>China (+86)</option>
        <option data-countryCode='CO' value='+57'>Colombia (+57)</option>
        <option data-countryCode='KM' value='+269'>Comoros (+269)</option>
        <option data-countryCode='CG' value='+242'>Congo (+242)</option>
        <option data-countryCode='CK' value='+682'>Cook Islands (+682)</option>
        <option data-countryCode='CR' value='+506'>Costa Rica (+506)</option>
        <option data-countryCode='HR' value='+385'>Croatia (+385)</option>
        <option data-countryCode='CU' value='+53'>Cuba (+53)</option>
        <option data-countryCode='CY' value='+90392'>Cyprus North (+90392)</option>
        <option data-countryCode='CY' value='+357'>Cyprus South (+357)</option>
        <option data-countryCode='CZ' value='+42'>Czech Republic (+42)</option>
        <option data-countryCode='DK' value='+45'>Denmark (+45)</option>
        <option data-countryCode='DJ' value='+253'>Djibouti (+253)</option>
        <option data-countryCode='DM' value='+1809'>Dominica (+1809)</option>
        <option data-countryCode='DO' value='+1809'>Dominican Republic (+1809)</option>
        <option data-countryCode='EC' value='+593'>Ecuador (+593)</option>
        <option data-countryCode='EG' value='+20'>Egypt (+20)</option>
        <option data-countryCode='SV' value='+503'>El Salvador (+503)</option>
        <option data-countryCode='GQ' value='+240'>Equatorial Guinea (+240)</option>
        <option data-countryCode='ER' value='+291'>Eritrea (+291)</option>
        <option data-countryCode='EE' value='+372'>Estonia (+372)</option>
        <option data-countryCode='ET' value='+251'>Ethiopia (+251)</option>
        <option data-countryCode='FK' value='+500'>Falkland Islands (+500)</option>
        <option data-countryCode='FO' value='+298'>Faroe Islands (+298)</option>
        <option data-countryCode='FJ' value='+679'>Fiji (+679)</option>
        <option data-countryCode='FI' value='+358'>Finland (+358)</option>
        <option data-countryCode='FR' value='+33'>France (+33)</option>
        <option data-countryCode='GF' value='+594'>French Guiana (+594)</option>
        <option data-countryCode='PF' value='+689'>French Polynesia (+689)</option>
        <option data-countryCode='GA' value='+241'>Gabon (+241)</option>
        <option data-countryCode='GM' value='+220'>Gambia (+220)</option>
        <option data-countryCode='GE' value='+7880'>Georgia (+7880)</option>
        <option data-countryCode='DE' value='+49'>Germany (+49)</option>
        <option data-countryCode='GH' value='+233'>Ghana (+233)</option>
        <option data-countryCode='GI' value='+350'>Gibraltar (+350)</option>
        <option data-countryCode='GR' value='+30'>Greece (+30)</option>
        <option data-countryCode='GL' value='+299'>Greenland (+299)</option>
        <option data-countryCode='GD' value='+1473'>Grenada (+1473)</option>
        <option data-countryCode='GP' value='+590'>Guadeloupe (+590)</option>
        <option data-countryCode='GU' value='+671'>Guam (+671)</option>
        <option data-countryCode='GT' value='+502'>Guatemala (+502)</option>
        <option data-countryCode='GN' value='+224'>Guinea (+224)</option>
        <option data-countryCode='GW' value='+245'>Guinea - Bissau (+245)</option>
        <option data-countryCode='GY' value='+592'>Guyana (+592)</option>
        <option data-countryCode='HT' value='+509'>Haiti (+509)</option>
        <option data-countryCode='HN' value='+504'>Honduras (+504)</option>
        <option data-countryCode='HK' value='+852'>Hong Kong (+852)</option>
        <option data-countryCode='HU' value='+36'>Hungary (+36)</option>
        <option data-countryCode='IS' value='+354'>Iceland (+354)</option>
        <option data-countryCode='IN' value='+91'>India (+91)</option>
        <option data-countryCode='ID' value='+62'>Indonesia (+62)</option>
        <option data-countryCode='IR' value='+98'>Iran (+98)</option>
        <option data-countryCode='IQ' value='+964'>Iraq (+964)</option>
        <option data-countryCode='IE' value='+353'>Ireland (+353)</option>
        <option data-countryCode='IL' value='+972'>Israel (+972)</option>
        <option data-countryCode='IT' value='+39'>Italy (+39)</option>
        <option data-countryCode='JM' value='+1876'>Jamaica (+1876)</option>
        <option data-countryCode='JP' value='+81'>Japan (+81)</option>
        <option data-countryCode='JO' value='+962'>Jordan (+962)</option>
        <option data-countryCode='KZ' value='+7'>Kazakhstan (+7)</option>
        <option data-countryCode='KE' value='+254'>Kenya (+254)</option>
        <option data-countryCode='KI' value='+686'>Kiribati (+686)</option>
        <option data-countryCode='KP' value='+850'>Korea North (+850)</option>
        <option data-countryCode='KR' value='+82'>Korea South (+82)</option>
        <option data-countryCode='KW' value='+965'>Kuwait (+965)</option>
        <option data-countryCode='KG' value='+996'>Kyrgyzstan (+996)</option>
        <option data-countryCode='LA' value='+856'>Laos (+856)</option>
        <option data-countryCode='LV' value='+371'>Latvia (+371)</option>
        <option data-countryCode='LB' value='+961'>Lebanon (+961)</option>
        <option data-countryCode='LS' value='+266'>Lesotho (+266)</option>
        <option data-countryCode='LR' value='+231'>Liberia (+231)</option>
        <option data-countryCode='LY' value='+218'>Libya (+218)</option>
        <option data-countryCode='LI' value='+417'>Liechtenstein (+417)</option>
        <option data-countryCode='LT' value='+370'>Lithuania (+370)</option>
        <option data-countryCode='LU' value='+352'>Luxembourg (+352)</option>
        <option data-countryCode='MO' value='+853'>Macao (+853)</option>
        <option data-countryCode='MK' value='+389'>Macedonia (+389)</option>
        <option data-countryCode='MG' value='+261'>Madagascar (+261)</option>
        <option data-countryCode='MW' value='+265'>Malawi (+265)</option>
        <option data-countryCode='MY' value='+60'>Malaysia (+60)</option>
        <option data-countryCode='MV' value='+960'>Maldives (+960)</option>
        <option data-countryCode='ML' value='+223'>Mali (+223)</option>
        <option data-countryCode='MT' value='+356'>Malta (+356)</option>
        <option data-countryCode='MH' value='+692'>Marshall Islands (+692)</option>
        <option data-countryCode='MQ' value='+596'>Martinique (+596)</option>
        <option data-countryCode='MR' value='+222'>Mauritania (+222)</option>
        <option data-countryCode='YT' value='+269'>Mayotte (+269)</option>
        <option data-countryCode='MX' value='+52'>Mexico (+52)</option>
        <option data-countryCode='FM' value='+691'>Micronesia (+691)</option>
        <option data-countryCode='MD' value='+373'>Moldova (+373)</option>
        <option data-countryCode='MC' value='+377'>Monaco (+377)</option>
        <option data-countryCode='MN' value='+976'>Mongolia (+976)</option>
        <option data-countryCode='MS' value='+1664'>Montserrat (+1664)</option>
        <option data-countryCode='MA' value='+212'>Morocco (+212)</option>
        <option data-countryCode='MZ' value='+258'>Mozambique (+258)</option>
        <option data-countryCode='MN' value='+95'>Myanmar (+95)</option>
        <option data-countryCode='NA' value='+264'>Namibia (+264)</option>
        <option data-countryCode='NR' value='+674'>Nauru (+674)</option>
        <option data-countryCode='NP' value='+977'>Nepal (+977)</option>
        <option data-countryCode='NL' value='+31'>Netherlands (+31)</option>
        <option data-countryCode='NC' value='+687'>New Caledonia (+687)</option>
        <option data-countryCode='NZ' value='+64'>New Zealand (+64)</option>
        <option data-countryCode='NI' value='+505'>Nicaragua (+505)</option>
        <option data-countryCode='NE' value='+227'>Niger (+227)</option>
        <option data-countryCode='NG' value='+234'>Nigeria (+234)</option>
        <option data-countryCode='NU' value='+683'>Niue (+683)</option>
        <option data-countryCode='NF' value='+672'>Norfolk Islands (+672)</option>
        <option data-countryCode='NP' value='+670'>Northern Marianas (+670)</option>
        <option data-countryCode='NO' value='+47'>Norway (+47)</option>
        <option data-countryCode='OM' value='+968'>Oman (+968)</option>
        <option data-countryCode='PW' value='+680'>Palau (+680)</option>
        <option data-countryCode='PA' value='+507'>Panama (+507)</option>
        <option data-countryCode='PG' value='+675'>Papua New Guinea (+675)</option>
        <option data-countryCode='PY' value='+595'>Paraguay (+595)</option>
        <option data-countryCode='PE' value='+51'>Peru (+51)</option>
        <option data-countryCode='PH' value='+63'>Philippines (+63)</option>
        <option data-countryCode='PL' value='+48'>Poland (+48)</option>
        <option data-countryCode='PT' value='+351'>Portugal (+351)</option>
        <option data-countryCode='PR' value='+1787'>Puerto Rico (+1787)</option>
        <option data-countryCode='QA' value='+974'>Qatar (+974)</option>
        <option data-countryCode='RE' value='+262'>Reunion (+262)</option>
        <option data-countryCode='RO' value='+40'>Romania (+40)</option>
        <option data-countryCode='RU' value='+7'>Russia (+7)</option>
        <option data-countryCode='RW' value='+250'>Rwanda (+250)</option>
        <option data-countryCode='SM' value='+378'>San Marino (+378)</option>
        <option data-countryCode='ST' value='+239'>Sao Tome &amp; Principe (+239)</option>
        <option data-countryCode='SA' value='+966'>Saudi Arabia (+966)</option>
        <option data-countryCode='SN' value='+221'>Senegal (+221)</option>
        <option data-countryCode='CS' value='+381'>Serbia (+381)</option>
        <option data-countryCode='SC' value='+248'>Seychelles (+248)</option>
        <option data-countryCode='SL' value='+232'>Sierra Leone (+232)</option>
        <option data-countryCode='SG' value='+65'>Singapore (+65)</option>
        <option data-countryCode='SK' value='+421'>Slovak Republic (+421)</option>
        <option data-countryCode='SI' value='+386'>Slovenia (+386)</option>
        <option data-countryCode='SB' value='+677'>Solomon Islands (+677)</option>
        <option data-countryCode='SO' value='+252'>Somalia (+252)</option>
        <option data-countryCode='ZA' value='+27'>South Africa (+27)</option>
        <option data-countryCode='ES' value='+34'>Spain (+34)</option>
        <option data-countryCode='LK' value='+94'>Sri Lanka (+94)</option>
        <option data-countryCode='SH' value='+290'>St. Helena (+290)</option>
        <option data-countryCode='KN' value='+1869'>St. Kitts (+1869)</option>
        <option data-countryCode='SC' value='+1758'>St. Lucia (+1758)</option>
        <option data-countryCode='SD' value='+249'>Sudan (+249)</option>
        <option data-countryCode='SR' value='+597'>Suriname (+597)</option>
        <option data-countryCode='SZ' value='+268'>Swaziland (+268)</option>
        <option data-countryCode='SE' value='+46'>Sweden (+46)</option>
        <option data-countryCode='CH' value='+41'>Switzerland (+41)</option>
        <option data-countryCode='SI' value='+963'>Syria (+963)</option>
        <option data-countryCode='TW' value='+886'>Taiwan (+886)</option>
        <option data-countryCode='TJ' value='+7'>Tajikstan (+7)</option>
        <option data-countryCode='TH' value='+66'>Thailand (+66)</option>
        <option data-countryCode='TG' value='+228'>Togo (+228)</option>
        <option data-countryCode='TO' value='+676'>Tonga (+676)</option>
        <option data-countryCode='TT' value='+1868'>Trinidad &amp; Tobago (+1868)</option>
        <option data-countryCode='TN' value='+216'>Tunisia (+216)</option>
        <option data-countryCode='TR' value='+90'>Turkey (+90)</option>
        <option data-countryCode='TM' value='+7'>Turkmenistan (+7)</option>
        <option data-countryCode='TM' value='+993'>Turkmenistan (+993)</option>
        <option data-countryCode='TC' value='+1649'>Turks &amp; Caicos Islands (+1649)</option>
        <option data-countryCode='TV' value='+688'>Tuvalu (+688)</option>
        <option data-countryCode='UG' value='+256'>Uganda (+256)</option>
        <option data-countryCode='GB' value='+44'>UK (+44)</option>
        <option data-countryCode='UA' value='+380'>Ukraine (+380)</option>
        <option data-countryCode='AE' value='+971'>United Arab Emirates (+971)</option>
        <option data-countryCode='UY' value='+598'>Uruguay (+598)</option>
        <option data-countryCode='US' value='+1'>USA (+1)</option>
        <option data-countryCode='UZ' value='+7'>Uzbekistan (+7)</option>
        <option data-countryCode='VU' value='+678'>Vanuatu (+678)</option>
        <option data-countryCode='VA' value='+379'>Vatican City (+379)</option>
        <option data-countryCode='VE' value='+58'>Venezuela (+58)</option>
        <option data-countryCode='VN' value='+84'>Vietnam (+84)</option>
        <option data-countryCode='VG' value='+84'>Virgin Islands - British (+1284)</option>
        <option data-countryCode='VI' value='+84'>Virgin Islands - US (+1340)</option>
        <option data-countryCode='WF' value='+681'>Wallis &amp; Futuna (+681)</option>
        <option data-countryCode='YE' value='+969'>Yemen (North)(+969)</option>
        <option data-countryCode='YE' value='+967'>Yemen (South)(+967)</option>
        <option data-countryCode='ZM' value='+260'>Zambia (+260)</option>
        <option data-countryCode='ZW' value='+263'>Zimbabwe (+263)</option>`
    );
}

function registerEtl(form) {

    let formData = getFormData(form)
    // if (form) {
    //     console.log(formData)
    //     return
    // }

    // $('#form-register-etl').submit()
    let etlCode = getField('etl-country-code').val();
    if (etlCode === null || etlCode === "undefined") {
        etlCode = '+233';
        formData.countryCode=etlCode;
    }

    // Get the form instance
    // const params = {
    //     surName: $('#surName').val(),
    //     country: $('#etl_country').val(),
    //     dateOfBirth: $('#etl_dateOfBirth').val(),
    //     emailAddress: $('#etl_emailAddress').val(),
    //     salary: $('#salary').val(),
    //     dateOfAppointment: $('#dateOfAppointment').val(),
    //     firstName: $('#etl_firstName').val(),
    //     motherName: '',
    //     fatherName: '',
    //     lastName: $('#etl_lastName').val(),
    //     age: $('#age').val(),
    //     staffNumber: $('#staffNumber').val(),
    //     gender: $('#etl_gender').val(),
    //     idNumber: $('#etlIdNumber').val(),
    //     idType: 'NATIONAL',
    //     maritalStatus: $('#etl_maritalStatus').val(),
    //     region: $('#region').val(),
    //     town: $('#town').val(),
    //     phoneNumber: etlCode + $('#etl_phoneNumber').val(),
    //     postalAddress: $('#postalAddress').val(),
    //     telephone: $('#telephone').val(),
    //     employer: $('#employer').val(),
    //     etl_tierAccount: $('#etl_tierAccount').val(),
    //     employerAddress: $('#employerAddress').val(),
    //     employerProdNo: $('#employerProdNo').val(),
    //     schemeproduct: $('#schemeproduct').val(),
    //     socialSecurity: $('#socialSecurity').val(),
    //     beneficiaryName: $('#beneficiaryName').val(),
    //     benDateOfBirth: $('#benDateOfBirth').val(),
    //     benRelationship: $('#benRelationship').val(),
    //     benPostalAddress: $('#benPostalAddress').val(),
    //     benEmail: $('#benEmail').val(),
    //     benPhoneNumber: etlCode + $('#benPhoneNumber').val(),
    //     benAllocation: $('#benAllocation').val(),
    //     beneficiaryName1: $('#beneficiaryName1').val(),
    //     benDateOfBirth1: $('#benDateOfBirth1').val(),
    //     benRelationship1: $('#benRelationship1').val(),
    //     benPostalAddress1: $('#benPostalAddress1').val(),
    //     benEmail1: $('#benEmail1').val(),
    //     benPhoneNumber1: '',
    //     benAllocation1: $('#benAllocation1').val(),
    //     beneficiaryName2: $('#beneficiaryName2').val(),
    //     benDateOfBirth2: $('#benDateOfBirth2').val(),
    //     benRelationship2: $('#benRelationship2').val(),
    //     benPostalAddress2: $('#benPostalAddress2').val(),
    //     benEmail2: $('#benEmail2').val(),
    //     benPhoneNumber2: '',
    //     benAllocation2: $('#benAllocation2').val(),
    //     beneficiaryName3: $('#beneficiaryName3').val(),
    //     benDateOfBirth3: $('#benDateOfBirth3').val(),
    //     benRelationship3: $('#benRelationship3').val(),
    //     benPostalAddress3: $('#benPostalAddress3').val(),
    //     benEmail3: $('#benEmail3').val(),
    //     benPhoneNumber3: '',
    //     benAllocation3: $('#benAllocation3').val(),
    //     title: $('#title').val(),
    //     code: $('#postalCode').val(),
    //     otherIDType: $('#otherIdType').val(),
    //     otherIDNumber: $('#otherIdentificationNumber').val(),
    //     maritalStatusAtDoe: $('#maritalStatusAtDoe').val(),
    //     otherContacts: etlCode + $('#otherContact').val(),
    //     nextOfKinName: $('#nextOfKinName').val(),
    //     nextOfKinRelationship: $('#nextOfKinRelationship').val(),
    //     nextOfKinAddress: $('#nextOfKinAddress').val(),
    //     MsurName: $('#MsurName').val(),
    //     nationality: $('#nationality').val(),
    //     MfirstName: $('#MfirstName').val(),
    //     M_otherName: $('#M_otherName').val(),
    //     homeAddress: $('#homeAddress').val(),
    //     taxNumber: $('#taxNumber').val(),
    //     postalTown: $('#postalTown').val(),
    //     building: $('#building').val(),
    //     road: $('#road').val(),
    //     secondaryPhoneNumber: $('#secondaryPhoneNumber').val(),
    //     pdistrict: $('#pdistrict').val(),
    //     pTraditionalAuthority: $('#pTraditionalAuthority').val(),
    //     pVillage: $('#pVillage').val(),
    //     parmentDistrict: $('#parmentDistrict').val(),
    //     parmentTraditionalAuthority: $('#parmentTraditionalAuthority').val(),
    //     parmentVillage: $('#parmentVillage').val(),
    //     nextOfKinAllocation: $('#nextOfKinAllocation').val(),
    //     type: 'member-etl',
    //     inCaptchaChars: $('#etlMemberCaptchaChars').val()
    // };

    for (const [key, value] of Object.entries(formData)) {
        if (value) {
        } else {
            formData[key] = "";
        }
    }

    on();
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/register/new/ETL`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            success: function (data) {
                if (data.success) {
                    success(`Registration successful`, function () {
                        window.location.href = `./`;
                    })
                    return
                }
                err(`${data.msg}`)
            },
            error: function (jqXHR, textStatus, exception) {
                try {
                    let json = jqXHR.responseJSON;
                    if (json) {
                        let msg = (json.msg);
                        if (msg) {
                            err(json.msg);
                            return;
                        }
                    }
                } catch (e) {
                }
                err();
            }
        });
    }, 500)
}

function allocaton(id) {

}

function createBeneficiariesForm(callBack) {
    let div=    `<div class="ibox-content">
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="beneficiaryName" class="control-label required">NAME OF
                                                        BENEFICIARY:</label> <input
                                                        type="text" name="beneficiaryName" class="form-control"
                                                        id="beneficiaryName" placeholder="Beneficiary Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benDateOfBirth" class="control-label required">Date Of
                                                        Birth:</label>
                                                    <input type="date"
                                                           name="benDateOfBirth"
                                                           class="form-control datepicker"
                                                           id="benDateOfBirth"
                                                           placeholder="Date Of Birth">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benRelationship" class="control-label required">Relationship
                                                        To Applicant
                                                    </label>
                                                    <select name="benRelationship" id="benRelationship"
                                                            class="form-control required">
                                                        <option selected disabled>Select...</option>
                                                        <option value="wife">WIFE</option>
                                                        <option value="husband">HUSBAND</option>
                                                        <option value="daughter">DAUGHTER</option>
                                                        <option value="son">SON</option>
                                                        <option value="mother">MOTHER</option>
                                                        <option value="father">FATHER</option>
                                                        <option value="brother">BROTHER</option>
                                                        <option value="sister">SISTER</option>
                                                        <option value="other">OTHER</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPostalAddress" class="control-label">POSTAL ADDRESS
                                                        OF
                                                        BENEFICIARY</label>
                                                    <input
                                                            type="text" name="benPostalAddress"
                                                            class="form-control required"
                                                            id="benPostalAddress" placeholder="Postal address"
                                                            oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benEmail" class="control-label">EMAIL ADDRESS OF
                                                        BENEFICIARY:</label> <input
                                                        type="email" name="benEmail" class="form-control"
                                                        id="benEmail" placeholder="Email"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPhoneNumber" class="control-label">BENEFICIARY
                                                        MOBILE PHONE NUMBER:</label>
                                                    <div class="form-inline">
                                                        <select class="form-control  etl-country-code pull-left"
                                                                name="countryCode" style="width: 25%;"></select>
                                                        <input type="text" name="benPhoneNumber"
                                                               class="form-control"
                                                               id="benPhoneNumber" placeholder="Phone Number"
                                                               style="width: 75%;"
                                                               oninput="this.value=this.value.toUpperCase()">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benAllocation" class="control-label required">PERCENTAGE
                                                        ALLOCATION TO BENEFICIARY (To Total 100%):</label>
                                                    <input
                                                            type="number" name="benAllocation"
                                                            class="form-control required"
                                                            id="benAllocation" min="0" max="100"
                                                            value="0"
                                                            onchange="allocaton('benAllocation')"
                                                            placeholder="Allocation">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <button class="btn btn-success" type="button"
                                                            onclick="showBen('ben1')">
                                                        <i class="glyphicon glyphicon-plus"></i> ADD BENEFICIARY
                                                    </button>
                                                </div>
                                            </div>
                                        </div>`;

    let x=0;
    for (let i = 1; i < 10; i++) {
        div+=` <div id="ben${i}" style="display: none; margin-top: 10px"
                                             class="ibox-content w3-animate-right">
                                            <h3>Beneficiary Details</h3>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="beneficiaryName${i}" class="control-label ">NAME OF
                                                        BENEFICIARY:</label> <input
                                                        type="text" name="beneficiaryName${i}" class="form-control"
                                                        id="beneficiaryName${i}" placeholder="Beneficiary Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benDateOfBirth${i}" class="control-label ">Date Of
                                                        Birth:</label>
                                                    <input type="date"
                                                           name="benDateOfBirth${i}"
                                                           class="form-control datepicker"
                                                           id="benDateOfBirth${i}"
                                                           placeholder="Date Of Birth">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benRelationship${i}" class="control-label ">Relationship
                                                        To Applicant
                                                    </label>
                                                    <select name="benRelationship${i}" id="benRelationship${i}"
                                                            class="form-control ">
                                                        <option value="wife">WIFE</option>
                                                        <option value="husband">HUSBAND</option>
                                                        <option value="daughter">DAUGHTER</option>
                                                        <option value="son">SON</option>
                                                        <option value="mother">MOTHER</option>
                                                        <option value="father">FATHER</option>
                                                        <option value="brother">BROTHER</option>
                                                        <option value="sister">SISTER</option>
                                                        <option value="other">OTHER</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPostalAddress${i}" class="control-label">POSTAL ADDRESS
                                                        OF
                                                        BENEFICIARY</label>
                                                    <input
                                                            type="text" name="benPostalAddress${i}"
                                                            class="form-control "
                                                            id="benPostalAddress${i}" placeholder="Postal address"
                                                            oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benEmail${i}" class="control-label">EMAIL ADDRESS OF
                                                        BENEFICIARY:</label> <input
                                                        type="email" name="benEmail${i}" class="form-control "
                                                        id="benEmail${i}" placeholder="Email"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPhoneNumber${i}" class="control-label">BENEFICIARY
                                                        MOBILE PHONE NUMBER:</label>
                                                    <div class="form-inline">
                                                        <select class="form-control  etl-country-code pull-left"
                                                                name="countryCode" style="width: 25%;"></select>
                                                        <input type="text" name="benPhoneNumber${i}"
                                                               class="form-control"
                                                               id="benPhoneNumber${i}" placeholder="Phone Number"
                                                               style="width: 75%;"
                                                               oninput="this.value=this.value.toUpperCase()">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benAllocation${i}" class="control-label ">PERCENTAGE
                                                        ALLOCATION TO BENEFICIARY (To Total 100%):</label>
                                                    <input
                                                            type="number" name="benAllocation${i}"
                                                            class="form-control "
                                                            id="benAllocation${i}" min="0" max="100"
                                                            onchange="allocaton('benAllocation')"
                                                            value="0"
                                                            placeholder="Allocation">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <button class="btn btn-success" type="button"
                                                            onclick="showBen('ben${i+1}')">
                                                        <i class="glyphicon glyphicon-plus"></i> ADD BENEFICIARY
                                                    </button>
                                                    <button class="btn btn-danger" type="button"
                                                            onclick="removeBen('ben${i}','${i}')">
                                                        <i class="glyphicon glyphicon-minus"></i> REMOVE
                                                    </button>
                                                </div>
                                            </div>
                                        </div>`;

        x=i;
    }
    x+=1;
    div+=` <div id="ben${x}" style="display: none; margin-top: 10px"
                                             class="ibox-content w3-animate-right">
                                            <h3>Beneficiary Details</h3>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="beneficiaryName${x}" class="control-label ">NAME OF
                                                        BENEFICIARY:</label> <input
                                                        type="text" name="beneficiaryName${x}" class="form-control"
                                                        id="beneficiaryName${x}" placeholder="Beneficiary Name"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benDateOfBirth${x}" class="control-label ">Date Of
                                                        Birth:</label>
                                                    <input type="date"
                                                           name="benDateOfBirth${x}"
                                                           class="form-control datepicker"
                                                           id="benDateOfBirth${x}"
                                                           placeholder="Date Of Birth">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benRelationship${x}" class="control-label ">Relationship
                                                        To Applicant
                                                    </label>
                                                    <select name="benRelationship${x}" id="benRelationship${x}"
                                                            class="form-control ">
                                                        <option selected disabled>Select...</option>
                                                        <option value="wife">WIFE</option>
                                                        <option value="husband">HUSBAND</option>
                                                        <option value="daughter">DAUGHTER</option>
                                                        <option value="son">SON</option>
                                                        <option value="mother">MOTHER</option>
                                                        <option value="father">FATHER</option>
                                                        <option value="brother">BROTHER</option>
                                                        <option value="sister">SISTER</option>
                                                        <option value="other">OTHER</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPostalAddress${x}" class="control-label">POSTAL ADDRESS
                                                        OF
                                                        BENEFICIARY</label>
                                                    <input
                                                            type="text" name="benPostalAddress${x}"
                                                            class="form-control "
                                                            id="benPostalAddress${x}" placeholder="Postal address"
                                                            oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 form-group">
                                                    <label for="benEmail${x}" class="control-label">EMAIL ADDRESS OF
                                                        BENEFICIARY:</label> <input
                                                        type="email" name="benEmail${x}" class="form-control "
                                                        id="benEmail${x}" placeholder="Email"
                                                        oninput="this.value=this.value.toUpperCase()">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benPhoneNumber${x}" class="control-label">BENEFICIARY
                                                        MOBILE PHONE NUMBER:</label>
                                                    <div class="form-inline">
                                                        <select class="form-control  etl-country-code pull-left"
                                                                name="countryCode" style="width: 25%;"></select>
                                                        <input type="text" name="benPhoneNumber${x}"
                                                               class="form-control"
                                                               id="benPhoneNumber${x}" placeholder="Phone Number"
                                                               style="width: 75%;"
                                                               oninput="this.value=this.value.toUpperCase()">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <label for="benAllocation${x}" class="control-label ">PERCENTAGE
                                                        ALLOCATION TO BENEFICIARY (To Total 100%):</label>
                                                    <input
                                                            type="number" name="benAllocation${x}"
                                                            class="form-control "
                                                            id="benAllocation${x}" min="0" max="100"
                                                            onchange="allocaton('benAllocation')"
                                                            value="0"
                                                            placeholder="Allocation">
                                                </div>
                                                <div class="col-md-6 form-group">
                                                    <button class="btn btn-danger" type="button"
                                                            onclick="removeBen('ben${x}','${x}')">
                                                        <i class="glyphicon glyphicon-minus"></i> REMOVE
                                                    </button>
                                                </div>
                                            </div>
                                        </div>`;
    callBack(div);

}

function totalAllocationReached(callBack=null,callBack2=null) {
    total=parseFloat(getField('benAllocation').val());
    if (total===100){
        if (callBack2!=null){
            callBack2()
        }
        return true
    }
    for (let i = 1; i < 11; i++) {
        total+=parseFloat(getField(`benAllocation${i}`).val());
        console.log(total)
    }
    if (total===100){
        if (callBack2!=null){
            callBack2()
        }
        return true
    }
    if (total>100){
        err('Invalid allocations, total exceeds 100%')
        return false
    }

    if (callBack!=null){
        callBack()
    }
    console.log(total)

    return false;
}