/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Order {
    private int order_id;
    private Cart cart;
    private Product product;
    private int quantity;
    private Date createAt;

    public Order(int order_id, Cart cart, Product product, int quantity, Date createAt) {
        this.order_id = order_id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.createAt = createAt;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Order{" + "order_id=" + order_id + ", cart=" + cart + ", product=" + product + ", quantity=" + quantity + ", createAt=" + createAt + '}';
    }
}