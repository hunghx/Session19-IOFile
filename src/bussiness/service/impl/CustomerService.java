package bussiness.service.impl;

import bussiness.config.IOFile;
import bussiness.entity.Customer;
import bussiness.service.ICustomerService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CustomerService implements ICustomerService {
    private List<Customer> customers;

    public CustomerService() {
        // đọc dữ liệu từ file
        this.customers = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public boolean save(Customer customer) {
        if (findById(customer.getId())!=null){
            // đã tồn tại -> chỉnh sửa
            customers.set(customers.indexOf(findById(customer.getId())),customer);
        }else {
            // thêm mới
            customers.add(customer);
        }
        // thay đổi dữ liệu
        // lưu vào file
        IOFile.writeToFile(IOFile.CUSTOMER_PATH,customers);
        return true;
    }

    @Override
    public Customer findById(Integer id) {
//        for (Customer c:customers
//             ) {
//            if (c.getId()==id){
//                return c;
//            }
//        }
//        return null;
        Optional<Customer> optional =customers.stream()
                .filter(c->c.getId()==id).findFirst();
        return optional.orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        customers.remove(findById(id));
        IOFile.writeToFile(IOFile.CUSTOMER_PATH,customers);
    }

    @Override
    public int getNewId() {
//        int idMax = 0;
//        for (Customer c:customers
//             ) {
//            if(idMax< c.getId()){
//                idMax = c.getId();
//            }
//        }
        int idMax = customers.stream().map(c->c.getId())
                 .max(Comparator.comparingInt(a->a)).orElse(0);
        return idMax+1;
    }

    @Override
    public Customer login(String username, String pass) {
//        for (Customer c:customers
//             ) {
//            if (c.getUsername().equals(username)&&c.getPassword().equals(pass)){
//                c.setPassword(null);
//                return c;
//            }
//        }
        return customers.stream()
                .filter(c->c.getUsername().equals(username)&&c.getPassword().equals(pass))
                .findFirst().orElse(null);
    }
}
