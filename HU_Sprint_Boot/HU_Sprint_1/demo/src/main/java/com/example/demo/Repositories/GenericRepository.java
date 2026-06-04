package com.example.demo.Repositories;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public interface GenericRepository<T, ID> {

    //al poner T , ID como parametro le estoy diciendo que vamos a trabajar con cualquier tipo de objeto no solo con coders

    List<T> findAll();

    T findById(ID id);

    void save(T entity);

    boolean delete(ID id);

    T update (ID id , T entity);
}
