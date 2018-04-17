package kr.ac.jejunu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public ProductDao productDao() {
        return new ProductDao(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker(){
        return new JejuConnectionMaker();
    }
}
