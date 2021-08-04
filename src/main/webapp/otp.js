function showModal() {
    setTimeout(function () {
        $('#otp').modal('show');
    }, 500);
}

function hideModal() {
    setTimeout(function () {
        $('#otp').modal('hide');
    }, 500);
}

function userLogins() {
    let login = sessionStorage.getItem('login'),
        pwd = sessionStorage.getItem('pwd'),
        tkn = sessionStorage.getItem('tkn');
    return {
        login: login,
        pwd: pwd,
        tkn: tkn
    };
}

function onOtpPageLoaded(callBack) {
    //get email, phone or notification device
    let url = `${baseUrl}/api/otp-init/${(userLogins()).login}`;
    $.getJSON(url, function (data) {
        let content = ``;
        if (data.success) {
            let list = data.data;
            list.forEach(function (listItem, index) {
                content += `<li class="list-group-item">
                                ${listItem.otpIdentifier} (${listItem.value})
                                <span class="badge badge-primary"></span>
                                <button type="button" class="btn btn-primary btn-xs" onclick="sendOtp('${listItem.otpIdentifier}')">Send</button>
                            </li>`;
            })
        }
        callBack(content);
    })
}

let miIdentifier;

function sendOtp(identifier) {
    miIdentifier = identifier;
    let url = `${baseUrl}/api/otp-send/${(userLogins()).login}/${identifier}`;
    on()
    $.getJSON(url, function (data) {
        off()
        if (data.success) {
            // success(data.data,function () {});
            showModal();
        } else {
            err(data.msg);
            setTimeout(function () {
                showModal();
            }, 1000)
        }
    })
}

function resendOtp() {
    hideModal()
    sendOtp(miIdentifier);
}

function verifyToken() {
    let otp = $('#otpToken').val();
    if (otp) {
        let url = `${baseUrl}/api/otp-v`;

        hideModal();
        on();
        setTimeout(function () {
            let midata = {
                "otp": otp,
                "username": (userLogins()).login,
                "pwd": (userLogins()).pwd
            }

            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(midata),
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                success: function (data) {
                    if (data.success) {
                        loginSuccess(data);
                        return;
                    }
                    err('Error encountered, please try again');
                    setTimeout(function () {
                        showModal();
                    }, 1000)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    try {
                        let json = jqXHR.responseJSON;
                        if (json) {
                            let msg = (json.msg);
                            if (msg) {
                                err(msg);
                                setTimeout(function () {
                                    showModal();
                                }, 1000)
                                return;
                            }
                        }
                    } catch (e) {
                    }
                    err('Error encountered, please try again');
                    setTimeout(function () {
                        showModal();
                    }, 1000)
                }
            });

        }, 500);
    }
}