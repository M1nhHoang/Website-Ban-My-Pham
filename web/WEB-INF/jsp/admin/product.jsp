<%-- 
    Document   : product
    Created on : May 9, 2023, 5:44:55 PM
    Author     : HP
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
    table{
        margin: auto
    }
    
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
        text-align: center;
    }
</style>

<div id="add" style="display: ${(param.action != null)?"none":"block"};">
    <div class="center_title_bar" style="background-color: #FFD700; color: #000; padding: 10px;">Thêm sản phẩm</div>
    <form method="POST" action="/WebsiteBanMyPham/admin/product" enctype="multipart/form-data" style="margin: 20px; margin-left: 50px">
        <input type="hidden" name="action" value="add">
        
        <label for="productName" style="color: #000;">Tên sản phẩm:</label><br>
        <input type="text" name="productName" id="productName" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productPrice" style="color: #000;">Đơn giá:</label><br>
        <input type="text" name="productPrice" id="productPrice" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productQuantity" style="color: #000;">Số lượng:</label><br>
        <input type="text" name="productQuantity" id="productQuantity" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productImage" style="color: #000;">Hình ảnh:</label><br>
        <input type="file" name="productImage" id="productImage" required><br><br>

        <label for="productDescription" style="color: #000;">Mô tả:</label><br>
        <textarea name="productDescription" id="productDescription" style="width: 50%; height: 100px; border: 1px solid #000; padding: 5px;" required></textarea><br><br>
        
        <label for="category" style="color: #000;">Danh Mục:</label><br>
        <select id="category" name="category" style="width: 200px; height: 30px; border: 1px solid #000; padding: 5px;" required>
            <c:forEach var="category" items="${categorys}">
                <option value="${category.getCategory_id()}" style="color: #000;">${category.getCategory_name()}</option>
            </c:forEach>
        </select><br><br>
        
        <button type="submit" style="background-color: #FFD700; color: #000; border: none; padding: 10px 20px; cursor: pointer;">Thêm sản phẩm</button>
    </form>
</div>

<div id="add" style="display: ${(param.action != null)?"block":"none"};">
    <div class="center_title_bar" style="background-color: #FFD700; color: #000; padding: 10px;">Sửa Sản Phẩm</div>
    <form method="POST" action="/WebsiteBanMyPham/admin/product" enctype="multipart/form-data" style="margin: 20px; margin-left: 50px">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="last_img" value="${product_Detail.getImage()}">
        <input type="hidden" name="productId" value="${product_Detail.getProduct_id()}">
        
        <label for="productName" style="color: #000;">Tên sản phẩm:</label><br>
        <input type="text" name="productName" id="productName" value="${product_Detail.getProduct_name()}" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productPrice" style="color: #000;">Đơn giá:</label><br>
        <input type="text" name="productPrice" id="productPrice" value="${product_Detail.getPrice()}" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productQuantity" style="color: #000;">Số lượng:</label><br>
        <input type="text" name="productQuantity" id="productQuantity" value="${product_Detail.getQuantity()}" required style="border: 1px solid #000; padding: 5px;"><br>

        <label for="productImage" style="color: #000;">Hình ảnh:</label><br>
        <input type="file" name="productImage" id="productImage"><br><br>

        <label for="productDescription" style="color: #000;">Mô tả:</label><br>
        <textarea name="productDescription" id="productDescription" style="width: 50%; height: 100px; border: 1px solid #000; padding: 5px;" required>${product_Detail.getDescription()}</textarea><br><br>
        
        <label for="category" style="color: #000;">Danh Mục:</label><br>
        <select id="category" name="category" style="width: 200px; height: 30px; border: 1px solid #000; padding: 5px;" required>
            <c:forEach var="category" items="${categorys}">
                <option value="${category.getCategory_id()}" ${(product_Detail.getCategory().getCategory_id()==category.getCategory_id())?"selected":""} style="color: #000;">${category.getCategory_name()}</option>
            </c:forEach>
        </select><br><br>
        
        <button type="submit" style="background-color: #FFD700; color: #000; border: none; padding: 10px 20px; cursor: pointer;">Sửa sản phẩm</button>
    </form>
</div>

<div class="center_title_bar" style="background-color: #FFD700; color: #000; padding: 10px;">Danh Sách Sản Phẩm</div>
<table style="width: 90%; margin-top: 10px">
    <tr>
        <th>Tên Sản Phẩm</th>
        <th>Đơn Giá</th>
        <th>Số Lượng</th>
        <th>Mô Tả</th>
        <th>Danh Mục</th>
        <th>Hình Ảnh</th>
        <th></th>
    </tr>
<c:forEach var="product" items="${products}">
    <tr>
        <td>${product.getProduct_name()}</td>
        <td>${product.getPrice()}</td>
        <td>${product.getQuantity()}</td>
        <td>${product.getDescription()}</td>
        <td>${product.getCategory().getCategory_name()}</td>
        <td><img src="/WebsiteBanMyPham/public/img/${product.getImage()}" style="width: 100px; height: 100px"></td>
        <td>
            <a href="/WebsiteBanMyPham/detail?sanpham=${product.getProduct_id()}">Xem</a> <br>
            <a href="/WebsiteBanMyPham/admin/product?action=edit&id=${product.getProduct_id()}">Sửa</a> <br>
            <a href="/WebsiteBanMyPham/admin/product?action=delete&id=${product.getProduct_id()}">Xóa</a> <br>
        </td>
    </tr>
</c:forEach>
</table>