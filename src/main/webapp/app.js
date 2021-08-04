// const baseUrl = `http://localhost:8080/resources`;
const baseUrl = `./resources`;
const country_list = ["Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua &amp; Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia &amp; Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Chad", "Chile", "China", "Colombia", "Congo", "Cook Islands", "Costa Rica", "Cote D Ivoire", "Croatia", "Cruise Ship", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia", "French West Indies", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kuwait", "Kyrgyz Republic", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Mauritania", "Mauritius", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Pierre &amp; Miquelon", "Samoa", "San Marino", "Satellite", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "South Africa", "South Korea", "Spain", "Sri Lanka", "St Kitts &amp; Nevis", "St Lucia", "St Vincent", "St. Lucia", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor L'Este", "Togo", "Tonga", "Trinidad &amp; Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks &amp; Caicos", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Venezuela", "Vietnam", "Virgin Islands (US)", "Yemen", "Zambia", "Zimbabwe"];

$(document).ready(function () {
    // $('#text-a24a').keyup(function () {
    //     let password = $('#email-5a14').val();
    //     let cpassword = $('#text-a24a').val();
    //     if (password !== cpassword) {
    //         $('#text-msg').html('Password do not match')
    //     } else {
    //         $('#text-msg').html('')
    //     }
    // })

    $('#btn_send').click(function () {
        register()
    })

    $('#btn-support').click(function () {
        sendSupportMsg();
    })

    $('#btnRequestPwd').click(function () {
        sendPasswordResetRequest();
    })

    $('#btnResetPwd').click(function () {
        sendResetPassword();
    })

    loadDistricts()
})

function getInputVal(id) {
    return $(`#${id}`).val();
}

function getSelectInputVal(id) {
    return $(`#${id} option:selected`).text();
}

function loginLoginPage() {
    let userName = $('#name-5a14').val();
    let pwd = $('#text-a24a').val();
    if (isBlank(userName) || isBlank(pwd)) {
        return;
    }
    doLogin(userName, pwd);
}

function loginIndexPage() {
    let userName = $('#name-ef64').val();
    let pwd = $('#email-ef64').val();
    if (isBlank(userName) || isBlank(pwd)) {
        return;
    }
    doLogin(userName, pwd);
}

function doLogin(userName, pwd) {
    on();
    setTimeout(function () {
        let midata = {
            "username": userName,
            "password": pwd,
            "rememberMe": false
        }

        $.ajax({
            url: `${baseUrl}/api/authenticate`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(midata),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            success: function (data) {
                if (data.success) {
                    // console.log(data)
                    if ((data.data.sessionCreated)) {
                        loginSuccess(data)
                    } else {
                        //2FA IS ENABLED
                        sessionStorage.setItem('tkn', data.token)
                        sessionStorage.setItem('login', userName)
                        sessionStorage.setItem('pwd', pwd)
                        window.location.href = `otp.html`;
                    }
                    return;
                }
                err('Error encountered, please try again')
            },
            error: function (jqXHR, textStatus, errorThrown) {
                try {
                    let json = jqXHR.responseJSON;
                    if (json) {
                        let msg = (json.msg);
                        if (msg) {
                            err(msg)
                            return;
                        }
                    }
                } catch (e) {
                }
                err('Incorrect credentials')
            }
        });

    }, 500);
}

function loginSuccess(data) {
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() + 1);
    let session = {
        user: {
            username: data.data.user.login,
            more: data.data.user
        },
        token: data.token,
        expires: expireDate
    }

    localStorage.setItem('app-state-session', JSON.stringify(session));
    let token = data.token;
    let userId = data.data.user.id;
    let pic = data.data.user.photo;
    let schemeId = data.data.user.userDetails.schemeId;
    let role = data.data.profileName;
    let sessionId = data.data.sessionId;
    localStorage.setItem('userId', userId);
    localStorage.setItem('pic', pic);
    localStorage.setItem('sessionId', sessionId);
    localStorage.setItem('schemeId', schemeId);
    localStorage.setItem('role', role);
    localStorage.setItem('tkn', token);
    window.location.href = `./mssvision/`;
}

function doLogin_before2FA(userName, pwd) {
    on();
    setTimeout(function () {
        let midata = {
            "username": userName,
            "password": pwd,
            "rememberMe": false
        }

        $.ajax({
            url: `${baseUrl}/api/authenticate`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(midata),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            success: function (data) {
                if (data.success) {
                    let expireDate = new Date();
                    expireDate.setDate(expireDate.getDate() + 1);
                    let session = {
                        user: {
                            username: data.data.user.login,
                            more: data.data.user
                        },
                        token: data.token,
                        expires: expireDate
                    }

                    localStorage.setItem('app-state-session', JSON.stringify(session));
                    let token = data.token;
                    let userId = data.data.user.id;
                    let pic = data.data.user.photo;
                    let schemeId = data.data.user.userDetails.schemeId;
                    let role = data.data.profileName;
                    let sessionId = data.data.sessionId;
                    localStorage.setItem('userId', userId);
                    localStorage.setItem('pic', pic);
                    localStorage.setItem('sessionId', sessionId);
                    localStorage.setItem('schemeId', schemeId);
                    localStorage.setItem('role', role);
                    localStorage.setItem('tkn', token);
                    window.location.href = `./mssvision/`;
                    return;
                }

                err('Error encountered, please try again')
            },
            error: function (jqXHR, textStatus, errorThrown) {
                try {
                    let json = jqXHR.responseJSON;
                    if (json) {
                        let msg = (json.msg);
                        if (msg) {
                            err(msg)
                            return;
                        }
                    }
                } catch (e) {
                }
                err('Incorrect credentials')
            }
        });

    }, 500);
}

let profiles;

function getAllProfiles() {
    $.getJSON(`${baseUrl}/api/selfRegisterProfiles`, function (data) {
        if (data.success) {
            let content = '<option selected disabled>Select...</option>';
            profiles = data.data;
            for (let i = 0; i < profiles.length; i++) {
                let profile = profiles[i];
                content += `<option value="${profile.id}">${profile.name}</option>`
            }

            $('#select-587c').html(content);
        }
    })
}

function testText() {
    alert($('#select-587c option:selected').text())
}

function ValidateEmail(mail) {
    return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail);
}

function register() {
    let email = $('#name-5a14').val(); //phone or email, memberNO, staffNO, NSSN
    let profileId = $('#select-587c').val();
    let fmIdentifier = $('#select-587c option:selected').text();

    let password = '#*11234'; //$('#email-5a14').val();
    let cpassword = '#*11234';//$('#text-a24a').val();

    if (!profileId) {
        return;
    }

    if (password.length < 4) {
        return;
    }

    if (isPhoneCheck) {
        email = phoneInput.getNumber();
    } else {
        //check email
        if (registerCheckEmail)
            if (!ValidateEmail(email)) {
                return;
            }
    }

    if (!email) return;
    //check password
    if (password !== cpassword) {
        console.log('passwords do not match')
        return;
    }

    let profile = {
        "password": password,
        "login": email,
        "email": email,
        "activated": false,
        "langKey": "en",
        "fmIdentifier": fmIdentifier,
        "profileId": profileId
    };

    on();

    setTimeout(function () {
        let btnSend = $('#btn_send');
        $.ajax({
            url: `${baseUrl}/api/register`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(profile),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            beforeSend: function (xhr) {
                btnSend.text('Loading...')
                btnSend.attr('disable', 'disable')
            },
            success: function (data) {
                btnSend.text('SUBMIT')
                btnSend.removeAttr('disable')

                if (data.success) {
                    // $('#gif').css('display', 'none');
                    // $('#msg').css('display', 'block');
                    // $('#msg').html(`<h4>Registration successful check your email for activation</h4>`);
                    // setTimeout(function () {
                    //     window.location.href = `./`;
                    // }, 5000);
                    success(`Registration successful check your email for activation`, function () {
                        window.location.href = `./`;
                    })
                    return
                }
                err(`${data.msg}`)

            },
            error: function (jqXHR, textStatus, exception) {
                btnSend.text('SUBMIT')
                btnSend.removeAttr('disable')
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

function getFormData(form) {
    let unindexed_array = form.serializeArray();
    let indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function getSponsorDetailsCallBack(sponsorRef, callBack) {
    $.ajax({
        url: `${baseUrl}/api/get-sponsor-details/0/${sponsorRef}`,
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        async: false,
        beforeSend: function () {
        },
        success: function (data) {
            callBack(data)
        },
        error: function (jqXHR, textStatus, exception) {
            callBack(null)
        }
    });
}

function getSponsorDetails(sponsorRef) {
    let isTrue = false;
    getSponsorDetailsCallBack(sponsorRef, function (data) {
        if (data.success) {
            let obj = data.data;
            let schemeName = obj.schemeName;
            $('#pg').css('display', 'none');
            $('#schemeName').val(schemeName);
            getSchemeDetails(schemeName)
            isTrue = true;
        } else {
            $('#pg').css('display', 'none');
            $('#mimsg').text("Failed to get employer details")
            isTrue = false;
        }
    })
    return isTrue;
}

function loadSponsorDetails(sponsorRef) {
    getSponsorDetailsCallBack(sponsorRef, function (data) {
        if (data.success) {
            let obj = data.data;
            let sponsorId = obj.id;
            $('#sponsorId').val(sponsorId);
        }
    })
}

function getSchemeDetails(schemeName) {
    $.getJSON(`${baseUrl}/api/schemes/getSchemeByName/${schemeName}`, function (data) {
        if (data.success) {
            let obj = data.data;
            $('#schemeId').val(obj.id);
        }
    })
}

function newMemberRegister(form) {
    let formData = getFormData(form)


    formData.sponsorName = getSelectInputVal('employerRefNo');

    formData.pobPlaceOfBirth = getSelectInputVal('pobPlaceOfBirth');
    formData.pobPlaceOfBirthID = getInputVal('pobPlaceOfBirth');
    formData.pobTraditionalAuthority = getSelectInputVal('pobTraditionalAuthority');
    formData.pobTraditionalAuthorityID = getInputVal('pobTraditionalAuthority');
    formData.pobVillage = getSelectInputVal('pobVillage');
    formData.pobVillageID = getInputVal('pobVillage');

    formData.phmPlaceOfBirth = getSelectInputVal('phmPlaceOfBirth');
    formData.phmPlaceOfBirthID = getInputVal('phmPlaceOfBirth');
    formData.phmTraditionalAuthority = getSelectInputVal('phmTraditionalAuthority');
    formData.phmTraditionalAuthorityID = getInputVal('phmTraditionalAuthority');
    formData.phmVillage = getSelectInputVal('phmVillage');
    formData.phmVillageID = getInputVal('phmVillage');


    // if (formData){
    //     console.log(formData)
    //     return
    // }

    on();
    let fileList = $('#files').prop("files");
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/register/new`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            success: function (data) {
                if (data.success) {
                    let memberId = data.data;
                    if (fileList && fileList.length > 0) {
                        newRegisterUploadDocs(memberId, form, function () {
                            success(`Registration successful`, function () {
                                window.location.href = `./`;
                            })
                        })
                        return;
                    }
                    success(`${data.msg}`, function () {
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

function newRegisterUploadDocs(memberId, form, callBack) {
    let formData = new FormData(form[0]);
    $.ajax({
        data: formData,
        async: false,
        cache: false,
        processData: false,
        contentType: false,
        url: `${baseUrl}/api/register/new/upload/${memberId}`,
        type: 'POST',
        error: function (xhr, status, error) {
            // console.log(xhr)
            callBack()
        },
        success: function (response) {
            // console.log(response)
            callBack()
        },
    });
}

function testUploadDocs() {
    let fileList = $('#files').prop("files");
    if (fileList && fileList.length > 0)
        $('#form').submit(function () {
            let formData = new FormData($(this)[0]);
            console.log(formData)
            let msg_error = 'An error has occured. Please try again later.';
            let msg_timeout = 'The server is not responding';
            let message = '';
            $.ajax({
                data: formData,
                async: false,
                cache: false,
                processData: false,
                contentType: false,
                url: `${baseUrl}/api/documents/upload/1`,
                type: 'POST',
                error: function (xhr, status, error) {
                    if (status === "timeout") {
                        alert(msg_timeout);
                    } else {
                        alert(msg_error);
                    }
                },
                success: function (response) {
                    alert(response);
                },
            });
        });
}

function getLandingPageContent() {
    $.getJSON(`${baseUrl}/api/getLandingPageContentDetailsAll`, function (data) {
        if (data.success) {
            let obj = data.data;
            let logo = obj.logo;
            let pensionerImage = obj.pensionerImage;
            let memberIcon = obj.memberIcon;
            let pensionerIcon = obj.pensionerIcon;
            let mapLoc = obj.mapLoc;
            localStorage.setItem('logo', logo);
            localStorage.setItem('pensionerImage', pensionerImage);

            $('#sitelogo').html(`<img src="./FileHandler?file=${logo}" class="u-logo-image u-logo-image-1" data-image-width="242.3334" style="width: 242px !important;">`);

            $('#pensionerImage').html(`<img src="./FileHandler?file=${pensionerImage}" data-image-width="600" style="width: 600px !important;">`);

            // $('#memberIcon').html(`<img src="./FileHandler?file=${memberIcon}" width="auto" height="100" >`);
            //  $('#pensionerIcon').html(`<img src="./FileHandler?file=${pensionerIcon}" width="auto" height="100">`);
            $('#memberMessage').text(obj.memberMessage);
            $('#pensionerMessage').text(obj.pensionerMessage);
            $('#welcomeMessage').text(obj.welcomeMessage);
            $('#whySaveMessage').text(obj.whySaveMessage);
            $('#mapLoc').html(obj.mapLoc);

            let address = `${obj.address.building}<br>${obj.address.road}<br>${obj.address.town},${obj.address.country}`;
            $('#addressBox').html(address);

            let contact = `${obj.address.fixedPhone} <br> ${obj.address.secondaryPhone}`;
            $('#contactBox').html(contact);

            let businessHours = obj.address.businessHours;
            $('#businessHours').html(businessHours);
        }
        getActiveConfig();

    })
}

function becomeMember() {
    let me = this,
        client = localStorage.getItem('client');
    if (client) {
        if (client === "ETL") {
            window.location.href = './registrationEtl.html'
        } else {
            window.location.href = './registration.html'
        }
        return;
    }
    //
    $('#btn-becomeMember').addClass('w3-animate-fading')
    getActiveConfig(function () {
        $('#btn-becomeMember').removeClass('w3-animate-fading')
        becomeMember();
    })
}

function clientLogin() {
    let me = this,
        client = localStorage.getItem('client');
    if (client) {
        if (client === "ETL") {
            window.location.href = './etl-auth.html'
        } else {
            window.location.href = './mssvision'
        }
    }
}

function clientForgotPwd() {
    let me = this,
        client = localStorage.getItem('client');
    if (client) {
        if (client === "ETL") {
            window.location.href = './etl-pwd.html'
        } else {
            window.location.href = './forgot-password.html'
        }
    }
}

/**
 *
 */
function getActiveConfig(callBack = null) {
    $.getJSON(`${baseUrl}/api/getSpecificFieldsOfActiveConfigs`, function (data) {
        if (data.success) {
            let obj = data.data;
            localStorage.setItem('businessName', obj.businessName);
            localStorage.setItem('businessImage', obj.businessImage);
            localStorage.setItem('currencyName', obj.currencyName);
            localStorage.setItem('currencyShortName', obj.currencyShortName);
            localStorage.setItem('client', obj.client);
            localStorage.setItem('reportServerUrl', obj.reportServerUrl);

            let declarationStatement = $('#declarationStatement');
            if (declarationStatement) {
                declarationStatement.html(
                    obj.registrationDeclaration
                )
            }
            setPage()

            if (callBack != null)
                callBack();

        }
    })
}

function setPage() {
    changePageTitle(localStorage.getItem('businessName'))
    // changeFavicon(`./FileHandler?file=${localStorage.getItem('logo')}`);
    changeFavicon(`./FileHandler?o=favicon.png&file=favicon.png`);
    // if (obj.businessImage)
    //     changeFavicon(obj.businessImage)
}

function sendSupportMsg() {
    let name = $('#name-da5f').val();
    let email = $('#email-da5f').val();
    let message = $('#message-da5f').val();

    if (isBlank(name)) return;
    if (!ValidateEmail(email)) return;
    if (isBlank(message)) return;

    let params = {
        'name': name,
        'email': email,
        'message': message
    };


    $('#sendingClbk').css('display', 'block');
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/support/send`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(params),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            beforeSend: function (xhr) {
            },
            success: function (data) {
                $('#sendingClbk').css('display', 'none');

                if (data.success) {
                    $('#supportmsgclbk').css('display', 'block');
                    setTimeout(function () {
                        $('#supportmsgclbk').css('display', 'none');
                    }, 5000);
                } else {
                    $('#sendingClbk').css('display', 'none');
                }
            },
            error: function (jqXHR, exception) {
                $('#sendingClbk').css('display', 'none');
            }
        });
    }, 5000)
}

function sendPasswordResetRequest() {
    let email = $('input[name="email"]').val();
    if (!ValidateEmail(email)) return;

    on()
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/account/reset-password/${email}`,
            type: 'post',
            dataType: 'json',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            beforeSend: function (xhr) {
            },
            success: function (data) {
                if (data.success) {
                    success(`A password reset link has been sent to your email`, function () {
                        window.location.href = `./`;
                    })
                    return;
                }

                err(`${data.msg}`)
            },
            error: function (jqXHR, exception) {
                err()
            }
        });
    }, 500);
}

function sendResetPassword() {
    let key = $('input[name="key"]').val();
    let newpwd = $('input[name="newpwd"]').val();
    let cpwd = $('input[name="cpwd"]').val();

    if (isBlank(newpwd)) return;
    if (newpwd !== cpwd) {
        $('input[name="cpwd"]').css('border', '1px solid red');
        return;
    }

    on()
    let params = {
        "key": key,
        "newPassword": newpwd
    };
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/account/reset-password/finish`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(params),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            beforeSend: function (xhr) {
            },
            success: function (data) {
                if (data.success) {
                    // $('#gif').css('display', 'none');
                    // $('#msg').css('display', 'block');
                    // $('#msg').html(`<h4>Your password has been reset use your new password to login.</h4>`);
                    // setTimeout(function () {
                    //     window.location.href = `./`;
                    // }, 5000);
                    success(`Your password has been reset use your new password to login.`, function () {
                        window.location.href = `./`;
                    })
                    return;
                }
                err(`${data.msg}`)
            },
            error: function (jqXHR, exception) {
                err()
            }
        });
    }, 500);
}

function getMemberId() {
    try {
        let data1 = localStorage.getItem("app-state-session");
        let jsonObject = JSON.parse(data1);
        if (typeof jsonObject === 'undefined' || jsonObject === null) {
            return -1;
        }
        return jsonObject.user.more.userDetails.memberId;
    } catch (e) {
        return -1;
    }
}

function formatToMoney(amount) {
    return numeral(amount).format('0,0.00');
}

function loadMemberStatement(callBack) {
    let userId = localStorage.getItem("userId");
    let fromDate = localStorage.getItem("fromDate");
    let toDate = localStorage.getItem("toDate");
    let url = `${baseUrl}/api/filterContributions/${userId}/${getMemberId()}/${fromDate}/${toDate}`;
    $.getJSON(url, function (obj) {
        if (obj.success)
            callBack(obj.data);
    })
}

function loadFAQ() {
    let url = `${baseUrl}/api/faq/getAll`;
    let content = ``;
    $.getJSON(url, function (obj) {
        if (obj.success) {
            let data = obj.data;
            for (let i = 0; i < data.length; i++) {
                let item = data[i];
                if (item.title !== "")
                    content += `<div class="faq-item">
                            <div class="row">
                                <div class="col-md-7">
                                    <a data-toggle="collapse" href="#faq${item.id}" class="faq-question">
                                    <i class="fa fa-chevron-down"></i> ${item.title}
                                    </a>
                                    <small><strong>${item.subtitle}</strong></small>
                                </div>
                                <div class="col-md-3"></div>
                                <div class="col-md-2"></div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div id="faq${item.id}" class="panel-collapse collapse ">
                                        <div class="faq-answer">
                                            <p>${item.body}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`;
            }
            $('#faqitems').html(content);
        }
    })
}

function resetOverlay() {
    $('#gif').css('display', 'block');
    let msg = getField('msg');
    msg.css('display', 'none');
    msg.html(`<h4>Error encountered</h4>`);
}

function getSchemes() {
    $('#pg').css('display', 'block');
    let url = `${baseUrl}/api/scheme/getAllSchemes/0`;
    let content = `<option selected disabled>Select scheme ...</option>`;
    $.getJSON(url, function (data) {
        $('#pg').css('display', 'none');
        if (data.success) {
            let obj = data.data;
            for (let i = 0; i < obj.length; i++) {
                let item = obj[i];
                content += `<option value="${item.id}">${item.schemeName}</option>`
            }
            $('#schemeId').html(content);
        }
    });
}

function loadSponsor() {
    $('#pg').css('display', 'block');
    let schemeId = $('#schemeId').val();
    if (schemeId) {
        let schemeName = getSelectInputVal('schemeId');
        console.log(schemeName)
        $('#schemeName').val(schemeName);
        let url = `${baseUrl}/api/scheme/getSchemeSponsors/0/${schemeId}`;
        let content = `<option selected disabled>Select Employer...</option>`;
        $.getJSON(url, function (data) {
            $('#pg').css('display', 'none');
            if (data.success) {
                let obj = data.data;
                for (let i = 0; i < obj.length; i++) {
                    let item = obj[i];
                    content += `<option value="${item.employerRefNo}">${item.name}</option>`
                }
                $('#employerRefNo').html(content);
            }
        });
    }
}

let registerCheckEmail = false,
    isPhoneCheck = false;

function changeRegistrationLabel() {
    let accountType = getSelectInputVal('select-587c'),
        lbl = $('#label-5a14'),
        nameField = $('#name-5a14'),
        label = `Email`,
        type = 'text',
        placeholder = `Enter your email`;

    lbl.removeClass('w3-animate-zoom');
    nameField.removeClass('w3-animate-zoom');

    for (let i = 0; i < profiles.length; i++) {
        let profile = profiles[i];
        if (profile.name === accountType) {
            label = profile.loginIdentifierName,
                placeholder = label;
        }
    }

    if (label === "Phone") {
        isPhoneCheck = true;
        registerCheckEmail = false;
        $('#div-phone').css('display', 'block');
        $('#div-phone').addClass('w3-animate-zoom');
        $('#div-email').css('display', 'none');
        return
    }

    isPhoneCheck = false;
    $('#div-phone').css('display', 'none');
    $('#div-email').css('display', 'block');
    if (label === "Email") {
        type = 'email';
        registerCheckEmail = true;
    } else {
        registerCheckEmail = false;
    }

    lbl.text(label);
    nameField.attr('placeholder', placeholder);
    nameField.attr('type', type);
    lbl.addClass('w3-animate-zoom');
    nameField.addClass('w3-animate-zoom');
}

function changeRegistrationLabel_1() {
    let accountType = getSelectInputVal('select-587c');
    let label = `Email`,
        placeholder = `Enter your email`;
    switch (accountType) {
        case 'MEMBER':
            label = 'Email/Member Number/National ID'
            placeholder = `Email/Member Number/National ID`
            break;
        case 'PENSIONER':
            label = 'Email/Member Number/National ID'
            placeholder = `Email/Member Number/National ID`
            break;

        case 'SPONSOR':
            label = 'Email / Ref. Number'
            placeholder = `Email / Ref. Number`
            break;

        default:

    }

    $('#label-5a14').text(label);
    $('#name-5a14').attr('placeholder', placeholder);
}

/**
 * @param fieldIDs array
 * @param isRequired boolean
 */
function setFieldsRequired(fieldIDs, isRequired = true) {
    if (fieldIDs != null) {
        for (let i = 0; i < fieldIDs.length; i++) {
            if (isRequired) {
                getField(fieldIDs[i]).addClass('required');
                getField(fieldIDs[i]).attr('required', 'required');
            } else {
                getField(fieldIDs[i]).removeClass('required');
                getField(fieldIDs[i]).removeAttr('required');
            }
        }
    }
}

function processOtherLocationDetails() {
    let country = getInputVal('country');
    switch (country) {
        case 'Malawi':
            let fields = [
                'pobPlaceOfBirth', 'pobTraditionalAuthority', 'pobVillage',
                'phmPlaceOfBirth', 'phmTraditionalAuthority', 'phmVillage'
            ];
            setFieldsRequired(fields);
            // processDistricts(country);
            loadDistricts();
            break;
        default:
    }
}

function processDistricts(country) {
    let content = `<option selected disabled>Select...</option>`;
    let districts = null;
    switch (country) {
        case 'Malawi':
            districts = districts_malawi.Sheet1;
            for (let i = 0; i < districts.length; i++) {
                let district = districts[i];
                content += `<option value="${district.CODE}">${district.NAME}</option>`
            }
            break;
        default:
    }
    // $('#district').html(content);
    // $('#addressPlaceOfBirth').html(content);
    $('#pobPlaceOfBirth').html(content);
    $('#phmPlaceOfBirth').html(content);
}

// function processTraditionalAuthorities(districtId,callBack) {
//     let country = getInputVal('country'),
//         districtCode = getInputVal('district');
//     let content = `<option selected disabled>Select...</option>`;
//     let tras = null;
//     switch (country) {
//         case 'Malawi':
//             tras = traditional_authorities_malawi.Sheet1;
//             for (let i = 0; i < tras.length; i++) {
//                 let tra = tras[i];
//                 if (tra.DISTRICTCODE === districtCode)
//                     content += `<option value="${tra.CODE}">${tra.NAME}</option>`
//             }
//             break;
//         default:
//     }
//     $('#traditionalAuthority').html(content);
// }
function processTraditionalAuthorities(districtId, callBack) {
    let country = getInputVal('country'),
        districtCode = getInputVal(districtId);
    let content = `<option selected disabled>Select...</option>`;
    let tras = null;
    switch (country) {
        case 'Malawi':
            tras = traditional_authorities_malawi.Sheet1;
            for (let i = 0; i < tras.length; i++) {
                let tra = tras[i];
                if (tra.DISTRICTCODE === districtCode)
                    content += `<option value="${tra.CODE}">${tra.NAME}</option>`
            }
            break;
        default:
    }
    callBack(content);
}

// function processVillages() {
//     let country = getInputVal('country'),
//         districtCode = getInputVal('district'),
//         traditionalAuthorityCode = getInputVal('traditionalAuthority');
//     let content = `<option selected disabled>Select...</option>`;
//     let villages = null;
//     switch (country) {
//         case 'Malawi':
//             villages = villages_malawi.Sheet1;
//             for (let i = 0; i < villages.length; i++) {
//                 let village = villages[i];
//                 if (village.TRADITIONALAUTHCODE === traditionalAuthorityCode)
//                     content += `<option value="${village.NAME}">${village.NAME}</option>`
//             }
//             break;
//         default:
//     }
//     $('#village').html(content);
// }

function processVillages(traditionalAuthorityId, callBack) {
    let country = getInputVal('country'),
        // districtCode = getInputVal('district'),
        traditionalAuthorityCode = getInputVal(traditionalAuthorityId);
    let content = `<option selected disabled>Select...</option>`;
    let villages = null;
    switch (country) {
        case 'Malawi':
            villages = villages_malawi.Sheet1;
            for (let i = 0; i < villages.length; i++) {
                let village = villages[i];
                if (village.TRADITIONALAUTHCODE === traditionalAuthorityCode)
                    content += `<option value="${village.CODE}">${village.NAME}</option>`
            }
            break;
        default:
    }
    callBack(content)
}

/**
 *
 */
function onAddressPlaceOfBirthChange() {
    processTraditionalAuthorities('addressPlaceOfBirth', function (content) {
        $('#addressTraditionalAuthority').html(content)
    })
}

function onAddressTraditionalAuthorityChange() {
    processVillages('addressTraditionalAuthority', function (content) {
        $('#addressVillage').html(content)
    })
}

function onPobPlaceOfBirthChange() {
    processTraditionalAuthorities('pobPlaceOfBirth', function (content) {
        $('#pobTraditionalAuthority').html(content)
    })
}

function onPobTraditionalAuthorityChange() {
    processVillages('pobTraditionalAuthority', function (content) {
        $('#pobVillage').html(content)
    })
}

function onPhmPlaceOfBirthChange() {
    processTraditionalAuthorities('phmPlaceOfBirth', function (content) {
        $('#phmTraditionalAuthority').html(content)
    })
}

function onPhmTraditionalAuthorityChange() {
    processVillages('phmTraditionalAuthority', function (content) {
        $('#phmVillage').html(content)
    })
}

function changeFavicon(link) {
    let $favicon = document.querySelector('link[rel="icon"]')
    // If a <link rel="icon"> element already exists,
    // change its href to the given link.
    if ($favicon !== null) {
        $favicon.href = link
        // Otherwise, create a new element and append it to <head>.
    } else {
        $favicon = document.createElement("link")
        $favicon.rel = "icon"
        $favicon.href = link
        document.head.appendChild($favicon)
    }
}

function changePageTitle(title) {
    if (title)
        document.title = `${title}`;
}

function getDistrictsHtml(callBack) {
    getDistricts(function (rows) {
        let content = `<option selected disabled>Select...</option>`;
        for (let i = 0; i < rows.length; i++) {
            let row = rows[i];
            content += `<option value="${row.id}">${row.name}</option>`
        }
        callBack(content)
    })
}

function getDistricts(callBack) {
    let url = `${baseUrl}/api/district`;
    $.getJSON(url, function (data) {
        if (data.success) {
            let rows = data.data;
            if (rows) {
                callBack(rows);
            }
        }
    })
}

function loadDistricts() {
    getDistrictsHtml(function (content) {
        // $('#addressPlaceOfBirth').html(content);
        $('#pobPlaceOfBirth').html(content);
        $('#phmPlaceOfBirth').html(content);
    })
}

function loadTraditionalAuthorities(districtId, callBack) {
    let url = `${baseUrl}/api/traditionalAuthority/${districtId}`;
    $.getJSON(url, function (data) {
        if (data.success) {
            let rows = data.data;
            if (rows) {
                let content = `<option selected disabled>Select...</option>`;
                for (let i = 0; i < rows.length; i++) {
                    let row = rows[i];
                    content += `<option value="${row.id}">${row.name}</option>`
                }

                callBack(content)
            }
        }
    })
}

function loadVillages(traditionalAuthorityId, callBack) {
    let url = `${baseUrl}/api/village/${traditionalAuthorityId}`;
    $.getJSON(url, function (data) {
        if (data.success) {
            let rows = data.data;
            if (rows) {
                let content = `<option selected disabled>Select...</option>`;
                for (let i = 0; i < rows.length; i++) {
                    let row = rows[i];
                    content += `<option value="${row.id}">${row.name}</option>`
                }
                callBack(content)
            }
        }
    })
}


function getTraditionalAuthorities(districtId, traditionalIdField) {
    loadTraditionalAuthorities(districtId, function (c) {
        getField(traditionalIdField).html(c)
    })
}

function getVillages(traditionalAuthorityId, villageIdField) {
    loadVillages(traditionalAuthorityId, function (c) {
        getField(villageIdField).html(c)
    })
}


function err(msg = `Error encountered, please try again`, callBack = null) {
    // $('#gif').css('display', 'none');
    // $('#msg').css('display', 'none');
    // $('#msg').html(`<h4>${msg}</h4>`);
    // setTimeout(function () {
    //     off();
    //     resetOverlay()
    // }, 3000);
    off();
    resetOverlay()
    if (callBack == null)
        alertify.alert('Sorry', msg);
    else
        alertify.alert('Sorry', msg, function () {
            callback();
        });
}

function success(msg, callback) {
    off();
    resetOverlay()
    alertify.alert('Success', msg, function () {
        if (callback != null)
            callback();
    });
}

function on() {
    document.getElementById("overlay").style.display = "block";
}

function off() {
    document.getElementById("overlay").style.display = "none";
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function saveEmailTemplate() {
    let template = $('.summernote').summernote('code');
    let smsTemplate = getInputVal('smsTemplate');
    let category = getInputVal('category');
    let title = getInputVal('subject')
    if (category.length < 1) return;
    if (title.length < 1) return;
    if (template.length < 1) return;

    let params = {
        "category": category,
        "title": title,
        "template": template,
        "smsTemplate": smsTemplate
    };
    on();
    setTimeout(function () {
        $.ajax({
            url: `${baseUrl}/api/emailTemplate`,
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(params),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            success: function (data) {
                if (data.success) {
                    success("Template successfully saved", function () {
                        $('.summernote').summernote('reset');
                        $('#subject').val('');
                        $('#smsTemplate').val('');
                        $('#namedKeys').html('');
                    })
                    return;
                }

                err('Error encountered, please try again')
            },
            error: function (jqXHR, textStatus, errorThrown) {
                try {
                    let json = jqXHR.responseJSON;
                    if (json) {
                        let msg = (json.msg);
                        if (msg) {
                            err(msg)
                            return;
                        }
                    }
                } catch (e) {
                }
                err('Error encountered')
            }
        });

    }, 500);
}

function loadEmailTemplate() {
    let category = getInputVal('category');
    if (category) {
        $.getJSON(`${baseUrl}/api/emailTemplate/${category}`, function (obj) {
            if (obj.success) {
                let data = obj.data,
                    template = data.template,
                    smsTemplate = data.smsTemplate,
                    title = data.title;

                $('#subject').val(title);
                $('#smsTemplate').val(smsTemplate);
                $('.summernote').summernote('reset');
                $('.summernote').summernote('code', template);

                let content = `Keys required: <b>`;
                let namedKeys = data.namedKeysList;
                if (namedKeys)
                    namedKeys.forEach(function (item,index) {
                        content += `#${item} `;
                    })
                content += '</b>';
                $('#namedKeys').html(content);
            }
        })
    }
}

function getEmailTemplateEnums(callBack) {
    $.getJSON(`${baseUrl}/api/getEmailTemplatesEnum`, function (obj) {
        if (obj.success) {
            let data = obj.data,
                content = `<option disabled selected>Select...</option>`;
            data.forEach(function (temp,index) {
                content += `<option value="${temp.category}"><b>${temp.title}</b> <span style="color: #8b0404">[${temp.description}]</span></option>`
            });
            $('#category').html(content);
            if (callBack != null) {
                callBack()
            }
        }
    })
}

function getCountryCodes(callBack) {
    $.getJSON(`${baseUrl}/api/getAllCountryCode`, function (obj) {
        if (obj.success) {
            let data = obj.data,
                content = `<option disabled selected>Select...</option>`;
            for (let i = 0; i < data.length; i++) {
                let temp = data[i];
                content += `<option value="${temp.countryCode}">${temp.country}</option>`
            }
            $('#select-999pn').html(content);
            if (callBack != null) {
                callBack()
            }
        }
    });
}

function getField(id) {
    return $(`#${id}`);
}

function toggleEmployment() {
    let employed = getInputVal("employed"),
        fields = [
            'staffNo',
            'designation',
            'dateOfAppointment',
            'currentMonthlySalary'
        ];

    setFieldsRequired(fields, (employed && employed === "YES"))
}

function getCountriesHTML(callBack, defaultCountry = null) {
    let ctry = ``;
    for (let i = 0; i < country_list.length; i++) {
        let country = country_list[i];
        if (defaultCountry != null) {
            ctry += `<option value="${defaultCountry}">${defaultCountry}</option>`
        }
        ctry += `<option value="${country}">${country}</option>`
    }
    if (callBack != null)
        callBack(ctry)
}



