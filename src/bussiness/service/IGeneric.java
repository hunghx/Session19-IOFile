package bussiness.service;

import java.util.List;

public interface IGeneric <T,E>{
    List<T> findAll();
    boolean save(T t);
    T findById(E id);
    void deleteById(E id);
}
