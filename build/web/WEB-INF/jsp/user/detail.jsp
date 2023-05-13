<%-- 
    Document   : detail
    Created on : May 8, 2023, 2:41:13 PM
    Author     : HP
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container-fluid pb-5">
    <div class="row px-xl-5">
        <div class="col-lg-5 mb-30">
            <div id="products-carousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner bg-light">
                    <div class="carousel-item active">
                        <img class="w-100 h-100" src="public/img/${products.getImage()}" alt="Image">
                    </div>
                    <div class="carousel-item">
                        <img class="w-100 h-100" src="public/img/${products.getImage()}" alt="Image">
                    </div>
                    <div class="carousel-item">
                        <img class="w-100 h-100" src="public/img/${products.getImage()}" alt="Image">
                    </div>
                    <div class="carousel-item">
                        <img class="w-100 h-100" src="public/img/${products.getImage()}" alt="Image">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#products-carousel" data-slide="prev">
                    <i class="fa fa-2x fa-angle-left text-dark"></i>
                </a>
                <a class="carousel-control-next" href="#products-carousel" data-slide="next">
                    <i class="fa fa-2x fa-angle-right text-dark"></i>
                </a>
            </div>
        </div>

        <div class="col-lg-7 h-auto mb-30">
            <div class="h-100 bg-light p-30">
                <h3>${products.getProduct_name()}</h3>
                <div class="d-flex mb-3">
                    <small class="pt-1">Danh mục: ${products.getCategory().getCategory_name()}</small>
                </div>
                <h3 class="font-weight-semi-bold mb-4">$${products.getPrice()}</h3>
                <p class="mb-4">${products.getDescription()}</p>
                <div class="d-flex mb-3">
                    <small class="pt-1">Số lượng còn lại ${products.getQuantity()}</small>
                </div>
                <div class="d-flex align-items-center mb-4 pt-2">
                    <div class="input-group quantity mr-3" style="width: 130px;">
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-minus">
                                <i class="fa fa-minus"></i>
                            </button>
                        </div>
                        <input id="quantity" type="text" class="form-control bg-secondary border-0 text-center" value="1">
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-plus">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>
                    <button id="add-to-cart" class="btn btn-primary px-3"><i class="fa fa-shopping-cart mr-1"></i> Add To
                        Cart</button>
                </div>
                <div class="d-flex pt-2">
                    <strong class="text-dark mr-2">Share on:</strong>
                    <div class="d-inline-flex">
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-pinterest"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
                
<script>
    // Bắt sự kiện khi nhấp vào nút "Add To Cart"
    const addToCartButton = document.querySelector('#add-to-cart');
    addToCartButton.addEventListener('click', () => {
        let quantity = document.querySelector('#quantity').value;
        // Gửi yêu cầu POST
        fetch('/WebsiteBanMyPham/cartDetail?action=add&masanpham=${products.getProduct_id()}&quantity='+quantity, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok)
                // Xử lý phản hồi thành công
                window.location = "/WebsiteBanMyPham/cartDetail";
        })
        .catch(error => console.error(error));
    });
</script>