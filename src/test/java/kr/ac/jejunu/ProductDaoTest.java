package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest {
    @Before
    public void setup(){

    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Long id = 1L;

        ProductDao productDao = new ProductDao();
        Product product = productDao.get(id);
        assertEquals(product.getId(), product.getId());
        assertEquals(product.getTitle(), product.getTitle());
        assertEquals(product.getPrice(), product.getPrice());
    }
}
