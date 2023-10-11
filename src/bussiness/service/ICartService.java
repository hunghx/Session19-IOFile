package bussiness.service;

import bussiness.entity.Order;
import bussiness.entity.OrderDetail;

import java.util.List;

public interface ICartService extends IGeneric<OrderDetail,Integer> {
    List<OrderDetail> getCartByUserLogin();
    int getNewId();
    OrderDetail findByProductId(int productId);
    void clear();
    void checkOut(IFoodService foodService);
}
