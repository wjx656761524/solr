package com.hhlt.dao;

import com.hhlt.pojo.ProductModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Wujx
 * @time: 2019/9/27 0027 14:24
 */
@Repository
public class JdDaoImpl implements  JdDao {

        @Autowired
        private SolrServer solrServer;

          public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name, String price, String sort) throws SolrServerException {
              SolrQuery solrQuery=new SolrQuery();
               solrQuery.set("q","*:*");
                solrQuery.setQuery("product_name:台灯");
              solrQuery.setQuery(queryString);
              //过滤
              if (null!=catalog_name&&!"".equals(catalog_name)){
                  solrQuery.set("fq","product_catalog_name:"+catalog_name);
              }
              if (null!=price&&!"".equals(price)){
                  String[]  p=price.split("-");
                  solrQuery.set("fq","product_price:["+p[0]+" TO "+p[1]+"]");
              }

              if("1".equals(sort)){
                  //排序
                  solrQuery.setSort("product_price", SolrQuery.ORDER.desc );
              }
              else{
                  //排序
                  solrQuery.setSort("product_price", SolrQuery.ORDER.asc );
              }

              //分页
              solrQuery.setStart(0);
              solrQuery.setRows(16);
              //默认域
              solrQuery.set("df","product_keywords");
//              //只查询指定域
//              solrQuery.set("fl","id,product_name,product_price,product_picture");
              //高亮
              //1.打开快关
              solrQuery.setHighlight(true);
              //指定高亮域
              solrQuery.addHighlightField("product_name");
              //前后缀
              solrQuery.setHighlightSimplePre("<span style='color:red'>");
              solrQuery.setHighlightSimplePost("</span>");



              QueryResponse response = solrServer.query(solrQuery);
              Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
              //
              List<ProductModel>productModels =new ArrayList<>();
              //总条数


              SolrDocumentList docs = response.getResults();
              Long numFound=docs.getNumFound();
              System.out.println(numFound);
              Integer num=0;


              for (SolrDocument doc :docs){
                  ProductModel productModel=new ProductModel();
                  productModel.setPid((String) doc.get("id"));
                  Map<String, List<String>> map = highlighting.get((String) doc.get("id"));
                  List<String> list = map.get("product_name");
                  if(null!=list){

                  productModel.setName(list.get(0));}

                  else{
                      productModel.setName((String) doc.get("product_name"));
                  }
                  productModel.setPrice((Float)doc.get("product_price"));
                  productModel.setPicture((String)doc.get("product_picture"));
                  productModels.add(productModel);
              }
              return productModels;
          }
}
