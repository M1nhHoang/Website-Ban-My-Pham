<%-- 
    Document   : category
    Created on : May 7, 2023, 5:18:55 PM
    Author     : HP
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:forEach var="category" items="${categorys}">
    <div class="col-lg-3 col-md-4 col-sm-6 pb-1">
        <a class="text-decoration-none" href="?danhmuc=${category.getCategory_id()}">
            <div class="cat-item d-flex align-items-center mb-4">
                <div class="overflow-hidden" style="width: 100px; height: 100px;">
                    <img class="img-fluid" src="public/img/${category.getImage()}" alt="">
                </div>
                <div class="flex-fill pl-3">
                    <h6>${category.getCategory_name()}</h6>
                </div>
            </div>
        </a>
    </div>
</c:forEach>