package bussiness.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime estimatedDelivery;
    private String address;
    private int customerId;
    private OrderStatus orderStatus;
    private boolean type;

    public Order() {
    }

    public Order(int id, LocalDateTime createdDate, LocalDateTime estimatedDelivery, String address, int customerId, OrderStatus orderStatus) {
        this.id = id;
        this.createdDate = createdDate;
        this.estimatedDelivery = estimatedDelivery;
        this.address = address;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
