package org.softdevelop.samples.spring.jdbctemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softdevelop.samples.spring.jdbctemplate.dao.ProductDao;
import org.softdevelop.samples.spring.jdbctemplate.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jorge on 18/01/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@Sql("reset.sql")
public class ProductDaoTest {


    @Autowired
    private ProductDao productDao;

    @Test
    public void testAll() {
        assertEquals(3, productDao.todos().size());
    }

    @Test
    public void testInsertQuery() {
        productDao.insertarSimple(4, "test4", Double.valueOf("123456"));
        assertEquals(4, productDao.todos().size());
    }

    @Test
    public void testInsert() {
        Product product = new Product(5, "test5", Double.valueOf("123"));
        productDao.insertarconQuery(product);
        Product producto = productDao.buscarporNombre("test5").get(0);
        assertEquals(5, (long) producto.getId());
    }

    @Test
    public void testFind() {
        List<Product> list = productDao.buscarporNombre("PC");
        assertEquals(1, list.size());
        assertEquals("PC", list.get(0).getName());
    }

    @Test
    public void testFindById() {
        List<Product> list = productDao.buscarporNombre("PC");
        assertEquals("PC", productDao.buscarporID(list.get(0).getId()).getName());
    }

    @Test
    public void insertBatch() throws SQLException {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(6, "nombre6", Double.valueOf(123456));
        products.add(product1);
        Product product2 = new Product(7, "nombre6", Double.valueOf(123457));
        products.add(product2);
        Product product3 = new Product(8, "nombre6", Double.valueOf(123458));
        products.add(product3);
        productDao.insertarbatch(products);
        assertEquals(productDao.buscarporNombre("nombre6").size(), 3);
    }

    @Test
    public void testEliminarById() {
        productDao.eliminar(2);
        assertEquals(2, (long) productDao.cantidad());
    }

    @Test
    public void testModificar() {
        Product producto = new Product(1, "PC Modificada", Double.valueOf("123"));
        productDao.actualizar(producto);
        assertEquals("PC Modificada", productDao.buscarporID(1).getName());
    }
}
