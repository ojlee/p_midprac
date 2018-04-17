package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;

public class ProductDaoTest {

    private ProductDao productDao;
    private DaoFactory daoFactory;

    @Before
    public void setup(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        productDao = applicationContext.getBean("productDao", ProductDao.class);

    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Long id = 1L;
        String title = "제주감귤";
        Integer price = 15000;

        Product product = productDao.get(id);
        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(price, product.getPrice());
    }
    @Test
    public void add() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertedProductTest(product);

        Product insertedProduct = productDao.get(id);
        assertEquals(insertedProduct.getId(), id);
        assertEquals(insertedProduct.getTitle(), product.getTitle());
        assertEquals(insertedProduct.getPrice(), product.getPrice());
    }
    @Test
    public void update() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertedProductTest(product);

        product.setId(id);
        product.setTitle("흑돼지고기");
        product.setPrice(40000);
        productDao.update(product);

        Product updatedProduct = productDao.get(id);
        assertEquals(updatedProduct.getId(), product.getId());
        assertEquals(updatedProduct.getTitle(), product.getTitle());
        assertEquals(updatedProduct.getPrice(), product.getPrice());
    }

    private Long insertedProductTest(Product product) throws SQLException, ClassNotFoundException {
        product.setTitle("다금바리");
        product.setPrice(200000);
        return productDao.insert(product);
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertedProductTest(product);

        productDao.delete(id);

        Product deletedProduct = productDao.get(id);
        assertEquals(deletedProduct, null);
    }

}
