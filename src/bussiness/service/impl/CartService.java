package bussiness.service.impl;

import bussiness.config.IOFile;
import bussiness.entity.Customer;
import bussiness.entity.Food;
import bussiness.entity.Order;
import bussiness.entity.OrderDetail;
import bussiness.service.ICartService;
import bussiness.service.IFoodService;
import bussiness.service.IOrderService;
import run.ShopManager;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartService implements ICartService {
    private List<OrderDetail> orderDetailList;
    private IOrderService orderService;
    private Customer userLogin;

    public CartService(IOrderService orderService,Customer userLogin) {
        orderDetailList = IOFile.readFromFile(IOFile.ORDERDETAIL_PATH);
        this.orderService = orderService;
        this.userLogin = userLogin;
    }

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public List<OrderDetail> getCartByUserLogin() {
        // qua userId tìm được giỏ hàng (orderID)
//        Order cart = ShopManager.orderService.findCartByUserId(userId);
        Order cart = orderService.findCartByUserId(userLogin.getId());

        return orderDetailList.stream()
                .filter(c->c.getOrderId()==cart.getId()).collect(Collectors.toList());
    }

    @Override
    public boolean save(OrderDetail orderDetail) {
        if (findById(orderDetail.getId())!=null){
            // đã tồn tại -> chỉnh sửa
            orderDetailList.set(orderDetailList.indexOf(findById(orderDetail.getId())),orderDetail);
        }else {
            // thêm mới vào giỏ hàng
            OrderDetail oldItem  = findByProductId(orderDetail.getFoodId());
            if (oldItem!=null){
                // tăng số lượng lên
                oldItem.setQuantity(oldItem.getQuantity()+orderDetail.getQuantity());
                orderDetailList.set(orderDetailList.indexOf(findById(oldItem.getId())),oldItem);

            }else {
                // thêm mới
                orderDetailList.add(orderDetail);
            }
        }
        // thay đổi dữ liệu
        // lưu vào file
        IOFile.writeToFile(IOFile.ORDERDETAIL_PATH,orderDetailList);
        return true;
    }

    @Override
    public void checkOut(IFoodService foodService) {
        Order cart = orderService.findCartByUserId(userLogin.getId());
        List<OrderDetail> orderDetails = orderDetailList.stream()
                .map(o->{
                    if (o.getOrderId()==cart.getId()){
                    Food f = foodService.findById(o.getFoodId());
                    o.setPrice(f.getPrice());
                   return o;
                    }else {
                        return o;
                    }
                }).collect(Collectors.toList());
        orderDetailList = orderDetails; // cập nhật
        IOFile.writeToFile(IOFile.ORDERDETAIL_PATH,orderDetailList);
    }

    @Override
    public void clear() {
        Order cart = orderService.findCartByUserId(userLogin.getId());
        List<OrderDetail> list = orderDetailList.stream()
                .filter(a->a.getOrderId()!=cart.getId()).collect(Collectors.toList());
        orderDetailList = list; // cập nhật
        IOFile.writeToFile(IOFile.ORDERDETAIL_PATH,orderDetailList);
    }

    @Override
    public OrderDetail findById(Integer id) {
        Optional<OrderDetail> optional =orderDetailList.stream()
                .filter(c->c.getId()==id).findFirst();
        return optional.orElse(null);
    }

    @Override
    public OrderDetail findByProductId(int foodId) {
        Optional<OrderDetail> optional =orderDetailList.stream()
                .filter(c->c.getFoodId()==foodId).findFirst();
        return optional.orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        orderDetailList.remove(findById(id));
        IOFile.writeToFile(IOFile.ORDERDETAIL_PATH,orderDetailList);
    }

    @Override
    public int getNewId() {
        int idMax = orderDetailList.stream().map(c->c.getId())
                .max(Comparator.comparingInt(a->a)).orElse(0);
        return idMax+1;
    }
}
