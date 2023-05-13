<%-- 
    Document   : register
    Created on : May 10, 2023, 1:36:57 PM
    Author     : Văn Bảo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    form {
        margin: 0px 10px;
    }

    h2 {
        margin-top: 2px;
        margin-bottom: 2px;
    }

    .container {
        max-width: 360px;
        
    }

    .divider {
        text-align: center;
        margin-top: 20px;
        margin-bottom: 5px;
    }

    .divider hr {
        margin: 7px 0px;
        width: 35%;
    }

    .left {
        float: left;
    }

    .right {
        float: right;
    }

</style>
<div class="container">
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-body">
                <form method="post" action="register" role="form">
                    <div class="form-group">
                        <h2>Create account</h2>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="signupName">Your name</label>
                        <input name="name" id="signupName" type="text" maxlength="50" class="form-control">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="signupPassword">Password</label>
                        <input name="pass" id="signupPassword" type="password" maxlength="25" class="form-control" length="40">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="signupPasswordagain">Password again</label>
                        <input name="repass" id="signupPasswordagain" type="password" maxlength="25" class="form-control">
                    </div>
                    <span style="color: red; font-size: 12px">${(registrationError != null)?registrationError:""}</span>
                    <div class="form-group">
                        <button id="signupSubmit" type="submit" class="btn btn-info btn-block">Create your account</button>
                    </div>
                    <p class="form-group">By creating an account, you agree to our <a href="#">Terms of Use</a> and our <a href="#">Privacy Policy</a>.</p>
                    <hr>
                    <p></p>Already have an account? <a href="/WebsiteBanMyPham/login">Sign in</a></p>
                </form>
            </div>
        </div>
    </div>
</div>
