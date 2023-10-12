package bussiness.service;

import bussiness.entity.Order;

import java.util.List;

public interface IOrderService extends IGeneric<Order,Integer> {
    Order findCartByUserId(int userid);
    int getNewId();
    List<Order> getListOrderByCustomerId(int customerId);
}
