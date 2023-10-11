package bussiness.service.impl;

import bussiness.config.IOFile;
import bussiness.entity.Order;
import bussiness.service.IOrderService;

import java.util.Comparator;
import java.util.List;

public class OrderService implements IOrderService {
    // note false là giỏ hàng
    private List<Order> orders ;

    @Override
    public Order findCartByUserId(int userid) {

//        nếu chưa có thì tạo mới
        for (Order o:orders
             ) {
            if(o.getCustomerId() == userid && !o.isType()){
                return o;
            }
        }

        // tạo mới giỏ hàng
        Order newCart = new Order();
        newCart.setId(getNewId());
        newCart.setCustomerId(userid);
        save(newCart);

        return newCart;
    }

    public OrderService() {
        orders = IOFile.readFromFile(IOFile.ORDER_PATH);
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean save(Order order) {
        if (findById(order.getId())!=null){
            // đã tồn tại -> chỉnh sửa
            orders.set(orders.indexOf(findById(order.getId())),order);
        }else {
            // thêm mới
            orders.add(order);
        }
        // thay đổi dữ liệu
        // lưu vào file
        IOFile.writeToFile(IOFile.ORDER_PATH,orders);
        return true;
    }

    @Override
    public Order findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public int getNewId() {
        int idMax = orders.stream().map(c->c.getId())
                .max(Comparator.comparingInt(a->a)).orElse(0);
        return idMax+1;
    }
}
