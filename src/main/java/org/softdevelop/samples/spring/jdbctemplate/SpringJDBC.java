package org.softdevelop.samples.spring.jdbctemplate;

import org.softdevelop.samples.spring.jdbctemplate.dao.ProductDao;
import org.softdevelop.samples.spring.jdbctemplate.model.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.sql.SQLException;

public class SpringJDBC {

    public static void main(String[] args) throws SQLException {

        AbstractApplicationContext ctx
                = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        ProductDao product = ctx.getBean(ProductDao.class);

        // crear un nuevo producto
        Product p0 = new Product(25, "Computer", 800.25);

        // insertar nuevo producto
        product.insertarconQuery(p0);

        // crear un nuevo producto
        Product p1 = new Product(35, "Computer 2", 802.25);

        // insertar nuevo producto
        //product.insertarconQuery(p1);
        product.insertarSimple(35, "Computer 2", Double.valueOf("802.25"));


        // crear un nuevo producto
        Product p2 = new Product(111, "Computer 3", 803.25);

        // insertar nuevo producto
        product.insertarconQuery(p2);


        System.out.println("Cantidad de productos: " + product.cantidad());
        System.out.println("Producto con ID = 25: " + product.buscarporID(25));


        // bucar el producto con ID = 35
        p1 = product.buscarporID(35);
        p1.setName("Server Computer");
        p1.setPrice(1001.25);

        // actualizar el producto con ID = 35
        product.actualizar(p1);

        // listar todos los productos
        System.out.println("--------Listando todos los productos-------");
        product.todos().forEach(System.out::println);

        // eliminar el producto con ID = 111
        product.eliminar(p2.getId());

        // listar todos los productos
        System.out.println("--------Listando todos los productos-------");
        product.todos().forEach(System.out::println);

        ctx.close();
    }
}
