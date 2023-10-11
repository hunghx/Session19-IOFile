package bussiness.service;

import bussiness.entity.Customer;

public interface ICustomerService extends IGeneric<Customer,Integer> {
    int getNewId();
    Customer login(String username, String pass);
}
