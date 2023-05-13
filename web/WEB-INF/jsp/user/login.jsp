<%-- 
    Document   : login
    Created on : May 9, 2023, 4:17:25 PM
    Author     : LE QUANG BAO CUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
    body {
        background-color: #fff; /* Trắng */
        color: #000; /* Đen */
    }

    #login-form {
        max-width: 300px;
        margin: 0 auto;
        padding: 20px;
        background-color: #fff; /* Trắng */
        border: 1px solid #000; /* Đen */
        border-radius: 5px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        color: #000; /* Đen */
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 5px;
        margin-bottom: 10px;
        border: 1px solid #000; /* Đen */
        border-radius: 3px;
    }

    input[type="submit"] {
        background-color: #ff0; /* Vàng */
        color: #000; /* Đen */
        padding: 10px 15px;
        border: none;
        border-radius: 3px;
        cursor: pointer;
    }

    span {
        color: red;
        font-size: 12px;
    }
</style>

<form id="login-form" action="login" method="post">
    <label for="username"> Tên đăng nhập: </label>
    <input type="text" id="username" name="username" required>
    <label for="password"> Mật khẩu: </label>
    <input type="password" id="password" name="password" required>
    <span>${(loginError != null) ? loginError : ""}</span>
    <input type="submit" value="Đăng nhập">
</form>

