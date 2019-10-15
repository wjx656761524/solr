package com.hhlt.service;

import com.hhlt.dao.JdDao;
import com.hhlt.pojo.ProductModel;
import org.apache.solr.client.solrj.SolrServerException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @description:
 * @author: Wujx
 * @time: 2019/9/27 0027 14:52
 */
@Service
public class JdServiceImpl implements JdService {
    @Autowired
    private JdDao jdDao;
    public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name, String price, String sort) throws SolrServerException {
       return jdDao.selectProductModelListByQuery(queryString,catalog_name,price,sort);

    }
}
