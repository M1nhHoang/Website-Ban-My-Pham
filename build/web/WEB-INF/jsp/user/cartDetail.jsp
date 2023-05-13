<%-- 
    Document   : cartDetail
    Created on : May 11, 2023, 2:24:31 PM
    Author     : HP
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Cart Start -->
<div class="container-fluid">
    <div class="row px-xl-5">
        <div class="col-lg-8 table-responsive mb-5">
            <table class="table table-light table-borderless table-hover text-center mb-0">
                <thead class="thead-dark">
                    <tr>
                        <th>Mã sản phẩm</th>
                        <th>Tên sản phẩm</th>
                        <th>Mô tả</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Số lượng còn lại</th>
                        <th>Danh mục</th>
                        <th>Xóa sản phẩm</th>
                    </tr>
                </thead>
                <tbody class="align-middle">
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td class="align-middle">${order.getProduct().getProduct_id()}</td>
                            <td class="align-middle"><img src="public/img/${order.getProduct().getImage()}" alt="" style="width: 50px;"> ${order.getProduct().getProduct_name()}</td>
                            <td class="align-middle">${order.getProduct().getDescription()}</td>
                            <td class="align-middle">$${order.getProduct().getPrice()}</td>
                            <td class="align-middle">
                                <div class="input-group quantity mx-auto" style="width: 100px;">
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-primary btn-minus" >
                                        <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <input disabled="true" type="text" class="form-control form-control-sm bg-secondary border-0 text-center" value="${order.getQuantity()}">
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-primary btn-plus">
                                            <i class="fa fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                            </td>
                            <td class="align-middle">${order.getProduct().getQuantity()}</td>
                            <td class="align-middle">${order.getProduct().getCategory().getCategory_name()}</td>
                            <td class="align-middle"><button class="btn btn-sm btn-danger"><i class="fa fa-times"></i></button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-lg-4">
            <h5 class="section-title position-relative text-uppercase mb-3"><span class="bg-secondary pr-3">Cart Summary</span></h5>
            <div class="bg-light p-30 mb-5">
                <div class="border-bottom pb-2">
                    <div class="d-flex justify-content-between mb-3">
                        <h6>Subtotal</h6>
                        <h6 id="subtotal">$0</h6>
                    </div>
                    <div class="d-flex justify-content-between">
                        <h6 class="font-weight-medium">Shipping</h6>
                        <h6 class="font-weight-medium" id="shipping">$0</h6>
                    </div>
                </div>
                <div class="pt-2">
                    <div class="d-flex justify-content-between mt-2">
                        <h5>Total</h5>
                        <h5 id="total">$0</h5>
                    </div>
                    <button id="check-out" class="btn btn-block btn-primary font-weight-bold my-3 py-3">Proceed To Checkout</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Cart End -->


<script>
    // Get the table and subtotal elements
    const table = document.querySelector('.table');
    const subtotalElement = document.querySelector('#subtotal');
    const shippingElement = document.querySelector('#shipping');
    const totalElement = document.querySelector('#total');

    // Add event listeners to the table
    table.addEventListener('click', (event) => {
        const target = event.target;
        const quantityElement = target.parentNode.parentNode.parentNode.querySelector('.form-control');
        var quantity = parseInt(quantityElement.value);
        const isBtnPlus = target.parentNode.classList.contains('btn-plus') || target.parentNode.querySelector('.btn-plus');
        const isBtnMinus = target.parentNode.classList.contains('btn-minus') || target.parentNode.querySelector('.btn-minus');
        const isBtnRmove = target.parentNode.classList.contains('btn-danger') || target.parentNode.querySelector('.btn-danger');
        
        if (isBtnPlus) {
            var maxQuantity = 0;
            try {
                maxQuantity = parseInt(target.parentNode.parentNode.parentNode.parentNode.querySelector('.align-middle:nth-child(6)').textContent);
            }
            catch {
                maxQuantity = parseInt(target.parentNode.parentNode.parentNode.parentNode.parentNode.querySelector('.align-middle:nth-child(6)').textContent);
            }
            
            if (quantity > maxQuantity) {
                quantityElement.value = maxQuantity; 
            }
            calculateSubtotalAndTotal();
        } else if (isBtnMinus) {
            if (quantity > 0) {
                quantityElement.value = quantity - 1;
                calculateSubtotalAndTotal();
            }
        } else if (isBtnRmove) {
            var product;
            try {
                product = target.parentNode.parentNode.querySelector('.align-middle:first-child').textContent;
            }
            catch {
                product = target.parentNode.parentNode.parentNode.querySelector('.align-middle:first-child').textContent;
            }
            removeProduct(product);
        }
    });

    // Calculate and update the subtotal and total
    function calculateSubtotalAndTotal() {
        const rows = table.querySelectorAll('tbody tr');
        let subtotal = 0;

        rows.forEach((row) => {
            const price = parseFloat(row.querySelector('.align-middle:nth-child(4)').textContent.substring(1));
            const quantity = parseInt(row.querySelector('.form-control').value);
            subtotal += price * quantity;
        });
        
        const shipping = parseFloat(shippingElement.textContent.substring(1));
        const total = subtotal + shipping;

        subtotalElement.textContent = '$' + subtotal.toFixed(2);
        totalElement.textContent = '$' + total.toFixed(2);
    }

    // Initial calculation of subtotal and total
    calculateSubtotalAndTotal();

    // Remove a product from the cart
    function removeProduct(product) {
        const products = [{ 'product': product }];
        fetch('/WebsiteBanMyPham/cartDetail?action=remove', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(products)
            })
            .then(response => {
                if (response.ok)
                    // Xử lý phản hồi thành công
                    window.location = "/WebsiteBanMyPham/cartDetail";
            })
            .catch(error => console.error(error));
    }

    // Proceed to checkout
    const checkoutButton = document.querySelector('#check-out');
    checkoutButton.addEventListener('click', () => {
        const rows = table.querySelectorAll('tbody tr');
        const products = [];

        rows.forEach((row) => {
            const product = row.querySelector('.align-middle:first-child').textContent;
            const quantity = parseInt(row.querySelector('.form-control').value);
            products.push({ 'product': product, 'quantity': quantity });
        });
            fetch('/WebsiteBanMyPham/cartDetail?action=order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(products)
            })
            .then(response => {
                if (response.ok)
                    // Xử lý phản hồi thành công
                    window.location = "/WebsiteBanMyPham/cartDetail";
            })
            .catch(error => console.error(error));
    });
</script>