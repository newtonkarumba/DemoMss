<%--
  Created by IntelliJ IDEA.
  User: Aviator
  Date: 4/6/21
  Time: 12:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! String key; %>
<%
    if (!request.getParameterMap().containsKey("key")) {
        response.sendRedirect("./");
        return;
    }
     key = request.getParameter("key");
%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Reset Password</title>

    <link href="template/css/bootstrap.min.css" rel="stylesheet">
    <link href="template/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="template/css/animate.css" rel="stylesheet">
    <link href="template/css/style.css" rel="stylesheet">
    <!-- include the style -->
    <link rel="stylesheet" href="alertifyjs/css/alertify.min.css" />
    <!-- include a theme -->
    <link rel="stylesheet" href="alertifyjs/css/themes/default.min.css" />
    <link rel="stylesheet" href="app.css">

</head>

<body class="gray-bg" oncontextmenu="return false">

<div class="ibox middle-box text-center loginscreen animated fadeInDown" style="width: 350px !important">
    <div class="ibox-content">
        <a href="./" id="sitelogo">
<%--            <img src="images/SystechLogo.PNG" width="auto" height="88">--%>
        </a>
        <h2>Reset Password</h2>
        <p></p>
        <p>Your password should be easy to remember and keep it private</p>
        <form class="m-t" role="form" action="" onsubmit="return false">
            <div class="form-group" style="display: none">
                <input name="key" type="text" class="form-control" value="<%=key%>" required="">
            </div>
            <div class="form-group">
                <input name="newpwd" type="password" class="form-control" placeholder="New Password" required="">
            </div>
            <div class="form-group">
                <input name="cpwd" type="password" class="form-control" placeholder="Confirm Password" required="">
            </div>
            <button id="btnResetPwd" type="submit" class="btn btn-primary block full-width m-b"
                    style="background-color: #025b80">Submit
            </button>

            <a href="#"><small></small></a>
            <p class="text-muted text-center"><small></small></p>
            <a class="btn btn-sm btn-white btn-block" href="./" style="background-color: #f27f00;color: white">Account
                Login</a>
        </form>
        <p class="m-t"><small>&copy; 2021</small></p>
    </div>

    <div id="overlay">
        <div id="text">
            <img id="gif"  src="images/lock.gif" height="120" width="120">
            <div id="msg" style="display: none;">
                <h4>Error encountered, please try again</h4>
            </div>
        </div>
    </div>

</div>

<!-- Mainly scripts -->
<script src="template/js/jquery-3.1.1.min.js"></script>
<script src="template/js/popper.min.js"></script>
<script src="template/js/bootstrap.js"></script>
<!-- include the script -->
<script src="alertifyjs/alertify.min.js"></script>
<script type="text/javascript" src="app.js"></script>
<script type="text/javascript">
    getLandingPageContent();
</script>
</body>

</html>

