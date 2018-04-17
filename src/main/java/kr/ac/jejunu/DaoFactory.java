package kr.ac.jejunu;

public class DaoFactory {
    public ProductDao getProductDao() {
        return new ProductDao(getConnectionMaker());
    }

    public ConnectionMaker getConnectionMaker(){
        return new JejuConnectionMaker();
    }
}
