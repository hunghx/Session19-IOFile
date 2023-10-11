package run;

import bussiness.entity.Customer;
import bussiness.entity.Food;
import bussiness.entity.Order;
import bussiness.entity.OrderDetail;
import bussiness.service.ICartService;
import bussiness.service.ICustomerService;
import bussiness.service.IFoodService;
import bussiness.service.IOrderService;
import bussiness.service.impl.CartService;
import bussiness.service.impl.CustomerService;
import bussiness.service.impl.FoodService;
import bussiness.service.impl.OrderService;

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
            System.out.println("8. Đăng xuất");
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
                  break;
                case 5:
                  break;
                case 6:
                  break;
                case 7:
                  break;
                case 8:
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

        Customer customerLogin= customerService.login(userName,password);
        if (customerLogin!=null){
            System.out.println("đăng nhập thành công");
            currentLogin = customerLogin;
            cartService = new CartService(orderService,currentLogin);
            System.out.println("CHào mừng "+customerLogin.getFullName()+" đến với shop");

            // Menu cửa hàng
            shop();
        }else {
            System.out.println("Sai tài khoản hoặc mật khẩu");
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
