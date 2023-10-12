package bussiness.service;

import bussiness.entity.Customer;
import bussiness.entity.Order;

import java.util.List;

public interface ICustomerService extends IGeneric<Customer,Integer> {
    int getNewId();
    Customer login(String username, String pass);

}
