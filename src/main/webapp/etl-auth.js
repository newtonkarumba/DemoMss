function ETL_memberLogin() {
    let phone=phoneInput.getNumber(),
        pwd=getInputVal('pwd');
    if (phone && pwd){
        doLogin(phone,pwd);
    }
}

function hideM(){
    $('#resetCode').modal('hide');
}

function showM(){
    $('#resetCode').modal('show');
}

function ETL_Login(usernameFieldId,pwdFieldId) {
    let username=getInputVal(usernameFieldId),
        pwd=getInputVal(pwdFieldId);
    if (username && pwd){
        doLogin(username,pwd);
    }
}

function ETL_RequestResetPwdPhone(type) {
    let phone=phoneInput.getNumber();
    if (phone){
        requestPwdCodeEtl(phone,type);
    }
}

function ETL_RequestResetPWD(usernameFieldId,type) {
    let username=getInputVal(usernameFieldId);
    if (username){
        requestPwdCodeEtl(username,type);
    }
}

function requestPwdCodeEtl(username,type) {
    sessionStorage.setItem('usr',username);
    on()
    $.getJSON(`${baseUrl}/api/account/etl-rpwd/${username}/${type}`,function (data) {
        setTimeout(function () {
            off()
            if (data.success){
                $('#someMessage').html(data.msg);
                showM()
            }else {
                err(data.msg);
            }
        },500);
    })
}

function verifyResetCode() {
    let otp=getInputVal('resetToken'),
        username=sessionStorage.getItem('usr');
    hideM();
    setTimeout(function () {
        on()
        $.getJSON(`${baseUrl}/api/account/etl-v/${username}/${otp}`,function (data) {
            setTimeout(function () {
                off()
                setTimeout(function () {
                    if (data.success){
                        window.location.href='./etl-newpwd.html'
                        return
                    }
                    err(data.msg);
                },500)
            },500)
        })
    },500);
}

function etlResetPwdFinish() {
    let username=sessionStorage.getItem('usr');
    let newpwd = $('input[name="newpwd"]').val();
    let cpwd = $('input[name="cpwd"]').val();
    if (isBlank(newpwd)) return;
    if (newpwd !== cpwd) {
        $('input[name="cpwd"]').css('border', '1px solid red');
        return;
    }
    on()
    $.getJSON(`${baseUrl}/api/account/etl-newpwd/${username}/${newpwd}`,function (data) {
        setTimeout(function () {
            off()
            if (data.success){
                success('You have successfully reset your password')
                setTimeout(function () {
                    window.location.href='./etl-auth.html'
                },3000)
                return
            }
            err(data.msg);
        },500)
    })
}