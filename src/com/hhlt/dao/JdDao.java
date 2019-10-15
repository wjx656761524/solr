package com.hhlt.dao;

import com.hhlt.pojo.ProductModel;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.ui.Model;

import java.util.List;

public interface JdDao {

    public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name, String price, String sort) throws SolrServerException;
}
