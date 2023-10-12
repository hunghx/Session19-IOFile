package run;

import bussiness.entity.*;
import bussiness.service.ICartService;
import bussiness.service.ICustomerService;
import bussiness.service.IFoodService;
import bussiness.service.IOrderService;
import bussiness.service.impl.CartService;
import bussiness.service.impl.CustomerService;
import bussiness.service.impl.FoodService;
import bussiness.service.impl.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ShopManager {
    private static final ICustomerService customerService = new CustomerService();
    public  static  Customer currentLogin ;
    public static final IOrderService orderService = new OrderService();
    public static final IFoodService foodService = new FoodService();
    public static ICartService cartService ;
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true){
            System.out.println("================SHOP================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Thoát");
            System.out.println("Nhập lựa chọn");
            byte choice = Byte.parseByte(input.nextLine());
            switch (choice){
                case 1:
                    login();
                    break;

                case 2:
                    register();
                    break;

                case 3:
                    System.exit(0);

                default:
                    System.err.println("Nhập sai");
                    break;

            }
        }
    }
    public static void shop(){
        while (true){
            System.out.println("=================Menu================");
            System.out.println("1. danh sách sản phẩm của cửa hàng");
            System.out.println("2. thêm sp vào giỏ hàng");
            System.out.println("3. hiển thị giỏ hàng");
            System.out.println("4. chỉnh sửa số lượng");
            System.out.println("5. xóa sản phẩm trong giỏ hàng");
            System.out.println("6. Xóa toàn bộ giỏ hàng");
            System.out.println("7. Thanh toán");
            System.out.println("8. Lịch sử mua hàng");
            System.out.println("9. Đăng xuất");
            System.out.println("Nhập lưạ chọn");
            byte choice = Byte.parseByte(input.nextLine());
            switch (choice){
                case 1:
                    // hiển thị danh sách sp
                    System.out.println("Danh sách món ăn");
                    for (Food f: foodService.findAll()) {
                        System.out.println(f);
                    }
                    break;
                case 2:
//                    thêm sp vào giỏ hàng
                    System.out.println("Nhập id sản phẩm muốn mua");
                    int proId = Integer.parseInt(input.nextLine());
                    if (foodService.findById(proId)==null){
                        System.err.println("ko tồn tại món ăn");
                    }else {
                        System.out.println("Nhập số lượng muốn mua");
                        int quantity = Integer.parseInt(input.nextLine());
                        // tao 1 đối tượng orderDetail
                        OrderDetail cartItem = new OrderDetail();
                        // lấy ra giỏ hàng
                        Order cart = orderService.findCartByUserId(currentLogin.getId());
                        cartItem.setId(cartService.getNewId());
                        cartItem.setOrderId(cart.getId());
                        cartItem.setFoodId(proId);
                        cartItem.setQuantity(quantity);

                        cartService.save(cartItem);
                        System.out.println("Thêm thành công");
                    }

                    break;
                case 3:
                    // hiển thị
                    System.out.println("Danh sachs giỏ hàng");
                    for (OrderDetail od: cartService.getCartByUserLogin()
                         ) {
                        Food food =foodService.findById(od.getFoodId());
                        System.out.printf("ID : %d - Tên sp : %s - Đơn giá : %.2f $ - số lượng : %d \n"
                                ,od.getId(),food.getName(),food.getPrice(),od.getQuantity());
                    }
                    System.out.println("------------------------------------------------");
                    double total = cartService.getCartByUserLogin().stream()
                            .map(od->od.getQuantity()*foodService.findById(od.getFoodId()).getPrice())
                            .reduce(0D,Double::sum);
                    System.out.println("Tổng tiền trong gio hang la :"+ total);

                  break;
                case 4:
                    System.out.println("Nhập id muốn chỉnh sửa (orderdetail)");
                    int orderDetail = Integer.parseInt(input.nextLine());
                    OrderDetail cartItem = cartService.findById(orderDetail);
                    if (cartItem==null){
                        // ko tồn tại
                        System.err.println("kotonf tại id chỉnh sửa");
                    }else {
                        System.out.println("Nhập số lượng mới ");
                        int quantity =Integer.parseInt(input.nextLine());
                        cartItem.setQuantity(quantity);
                        cartService.save(cartItem);
                    }
                  break;
                case 5:
                    // xóa
                    System.out.println("Nhập id muốn xóa  (orderdetail)");
                    int orderDetailId = Integer.parseInt(input.nextLine());
                    if (  cartService.findById(orderDetailId)==null){
                        // ko tồn tại
                        System.err.println("kotonf tại id muốn xóa");
                    }else {
                        cartService.deleteById(orderDetailId);
                        System.out.println("Xóa thành công");
                    }
                  break;
                case 6:
                    cartService.clear();
                    System.out.println("đã xóa toàn bộ");
                  break;
                case 7:
                    // thanh toán
                    // nhập địa chỉ
                    System.out.println("Nhập địa chỉ giao hàng");
                    String address = input.nextLine();

                    // tính tiên
                    List<OrderDetail> carts =cartService.getCartByUserLogin();
                    double totalAmount = carts.stream()
                            .map(od->od.getQuantity()*foodService.findById(od.getFoodId()).getPrice())
                            .reduce(0D,Double::sum);
                    Order  cart = orderService.findCartByUserId(currentLogin.getId());
                    LocalDateTime day = LocalDateTime.now();
                    cart.setAddress(address);
                    cart.setCreatedDate(day);
                    cart.setEstimatedDelivery(day.plus(4, ChronoUnit.DAYS));
                    cart.setOrderStatus(OrderStatus.WAITING);
                    cart.setType(true);

                    cart.setTotal(totalAmount);
                    cartService.checkOut(foodService); // cập nhật giá tiền

                    System.out.println("Thành công");
                    orderService.save(cart);
                  break;
                case 8:
                    DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
//                    lịch sử mua hàng người đang đăng nhập
                    System.out.println("----------Danh sách lịch sử mua hàng");
                    List<Order> orders = orderService.getListOrderByCustomerId(currentLogin.getId());
                    for (Order o:orders){
                        System.out.println("------------Đơn hàng-------------");
                        System.out.println("ID : "+o.getId() + " | CreatedDate : "+dft.format(o.getCreatedDate()));
                        System.out.println("EstimatedDelivery : "+dft.format(o.getEstimatedDelivery()));
                        System.out.println("Receiver : "+customerService.findById(o.getCustomerId()).getFullName());
                        System.out.println("Address : " +o.getAddress()+ "| phone : "+customerService.findById(o.getCustomerId()).getPhone());
                        System.out.println("Total : "+o.getTotal() + " $");
                        System.out.println("Status : "+o.getOrderStatus());
                        System.out.println("---------------------------------");
                    }

                    break;
                case 9:
                    // đăng xuất
                    currentLogin = null;
                  break;

                default:
                    System.err.println("Nhập sai");
                    break;

            }
            if (choice ==8){
                break;
            }
        }
    }

    public static  void login(){
        System.out.println("==================LOGIN======================");
        // nhập username
        System.out.println("Nhập username ");
        String userName = input.nextLine();
        System.out.println("Nhập password ");
        String password = input.nextLine();

        if (Objects.equals(userName, "admin") && Objects.equals(password,"admin")){
//            addmin

        }else {
//           người dùng
            Customer customerLogin = customerService.login(userName, password);
            if (customerLogin != null) {
                System.out.println("đăng nhập thành công");
                currentLogin = customerLogin;
                cartService = new CartService(orderService, currentLogin);
                System.out.println("CHào mừng " + customerLogin.getFullName() + " đến với shop");

                // kiểm tra quyền

                // Menu cửa hàng
                shop();
            } else {
                System.out.println("Sai tài khoản hoặc mật khẩu");
            }
        }
    }
    public static void register(){
        System.out.println("==================REGISTER======================");
        // nhập username
        System.out.println("Nhập username ");
        String userName = input.nextLine();
        System.out.println("Nhập password ");
        String password = input.nextLine();
        System.out.println("Nhập phone ");
        String phone = input.nextLine();
        System.out.println("Nhập fullname ");
        String fullName = input.nextLine();
        // tạo 1 đối tượng Customer
        Customer newCustomer = new Customer(customerService.getNewId(),userName,password,fullName,phone);
        customerService.save(newCustomer);
        System.out.println("Đăng kí thành công");
        login();
    }
}
