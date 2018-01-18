package org.softdevelop.samples.spring.jdbctemplate.dao;

import org.softdevelop.samples.spring.jdbctemplate.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> todos();

    Integer cantidad();

    Product buscarporID(Integer id);

    List<Product> buscarporNombre(String nombre);

    int insertarconQuery(Product product);

    long insertarSimple(int id, String name, Double price);

    void actualizar(Product product);

    void eliminar(Integer id);

    void insertarbatch(List<Product> products) throws SQLException;
}
