package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.ProductCollectDao;
import com.hongluomeng.model.ProductCollect;

public class ProductCollectService extends BaseService {

	private ProductCollectDao productCollectDao = new ProductCollectDao();

    public Integer count() {
        return productCollectDao.count();
    }


    public List<ProductCollect> list(Integer m, Integer n) {
        return productCollectDao.list(m, n);
    }

    public List<ProductCollect> listByUser_id(String user_id, Integer m, Integer n) {
        return productCollectDao.listByUser_id(user_id, m, n);
    }

    public void save(ProductCollect productCollect, String request_user_id) {
        productCollectDao.save(productCollect, request_user_id);
    }

    public void delete(String product_collect_id, String request_user_id) {
        productCollectDao.delete(product_collect_id, request_user_id);
    }

    public void deleteByProduct_id(String product_id, String request_user_id) {
        productCollectDao.deleteByProduct_id(product_id, request_user_id);
    }

}
