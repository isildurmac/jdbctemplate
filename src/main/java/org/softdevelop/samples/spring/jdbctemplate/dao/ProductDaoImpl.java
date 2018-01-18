package org.softdevelop.samples.spring.jdbctemplate.dao;

import org.softdevelop.samples.spring.jdbctemplate.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simplejdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("product");
    }

    @Override
    public List<Product> todos() {

        String sql = "select * from product";

        return this.jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int i) throws SQLException {
                Product p = new Product();
                p.setId(rs.getInt("ID"));
                p.setName(rs.getString("NAME"));
                p.setPrice(rs.getDouble("PRICE"));
                return p;
            }
        });
    }

    @Override
    public Integer cantidad() {
        String sql = "select count(*) from product";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Product buscarporID(Integer id) {
        String sql = "select * from product where id = ?";
        return this.jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }

    @Override
    public List<Product> buscarporNombre(String nombre) {
        String sql = "select * from product where name = ?";
        return this.jdbcTemplate.query(sql, new ProductRowMapper(), nombre);
    }

    @Override
    public int insertarconQuery(Product product) {
        String sql = "insert into product (ID, NAME, PRICE) values (?, ?, ?)";
        this.jdbcTemplate.update(sql,
                product.getId(),
                product.getName(),
                product.getPrice());
        return product.getId();

    }

    public long insertarSimple(int id, String name, Double price) {
        System.out.println("valores a insertar " + "name: " + name + "price: " + price);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("name", name);
        parameters.put("price", price);
        this.simplejdbcInsert.execute(parameters);
        return id;

    }

    @Override
    public void actualizar(Product product) {
        String sql = "update product set NAME = ?, PRICE = ? where ID = ?";
        this.jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getId());
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "delete from product where ID = ?";
        this.jdbcTemplate.update(sql, id);
    }

    @Override
    public void insertarbatch(List<Product> products) throws SQLException {

        String sql = "INSERT INTO product (ID, NAME, PRICE) values (?, ?, ?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Product product = products.get(i);
                ps.setLong(1, product.getId());
                ps.setString(2, product.getName());
                ps.setDouble(3, product.getPrice());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });

    }


    class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            Product p = new Product();
            p.setId(rs.getInt("ID"));
            p.setName(rs.getString("NAME"));
            p.setPrice(rs.getDouble("PRICE"));
            return p;
        }

    }

}
