<%-- 
    Document   : product
    Created on : May 7, 2023, 5:11:08 PM
    Author     : HP
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:forEach var="product" items="${products}">
    <div class="col-lg-3 col-md-4 col-sm-6 pb-1 paging">
        <div class="product-item bg-light mb-4">
            <div class="product-img position-relative overflow-hidden">
                <img class="img-fluid w-100" src="public/img/${product.getImage()}" alt="">
                <div class="product-action">
                    <a class="btn btn-outline-dark btn-square"=><i class="fa fa-shopping-cart"></i></a>
                    <span hidden="true" id="product-id">${product.getProduct_id()}</span>
                </div>
            </div>
            <div class="text-center py-4">
                <a class="h6 text-decoration-none text-truncate" href="detail?sanpham=${product.getProduct_id()}">${product.getProduct_name()}</a>
                <div class="d-flex align-items-center justify-content-center mt-2">
                    <h5>$${product.getPrice()}</h5>
                </div>
                <div class="d-flex align-items-center justify-content-center mb-1">
                    <small>Số lượng còn lại ${product.getQuantity()}</small>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<script>
    // Bắt sự kiện khi nhấp vào nút "Add To Cart"
    const addToCartButtons = document.querySelectorAll('.product-action');

    addToCartButtons.forEach(addToCartButton => {
        addToCartButton.addEventListener('click', (event) => {
            const product_id = event.target.parentNode.parentNode.querySelector("#product-id").textContent;

            // Gửi yêu cầu POST
            fetch('/WebsiteBanMyPham/cartDetail?action=add&masanpham='+product_id+'&quantity=1', {
                method: 'POST'
            })
                .then(response => {
                    if (response.ok) {
                        // Xử lý phản hồi thành công
                        window.location = "/WebsiteBanMyPham/cartDetail";
                    }
                })
              .catch(error => console.error(error));
        });
    });

</script>