package com.hhlt.service;

import com.hhlt.pojo.ProductModel;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.List;

public interface JdService {
    public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name, String price, String sort) throws SolrServerException;

}
