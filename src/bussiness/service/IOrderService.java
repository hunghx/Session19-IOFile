package bussiness.service;

import bussiness.entity.Order;

public interface IOrderService extends IGeneric<Order,Integer> {
    Order findCartByUserId(int userid);
    int getNewId();
}
