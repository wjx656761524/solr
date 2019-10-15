package com.hhlt.controller;

import com.hhlt.pojo.ProductModel;
import com.hhlt.service.JdService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description:
 * @author: Wujx
 * @time: 2019/9/27 0027 14:19
 */
@Controller
public class JDController {
    @Autowired
    JdService jdService;
    //商品列表
    @RequestMapping(value="list.action")
    public String list(String queryString, String catalog_name, String price, String sort, Model model) throws SolrServerException {
        //通过上面4个条件查询商品的结果集

        List<ProductModel> productModels = jdService.selectProductModelListByQuery(queryString, catalog_name, price, sort);
        model.addAttribute("productModels",productModels);
        model.addAttribute("queryString",queryString);
        model.addAttribute("catalog_name",catalog_name);
        model.addAttribute("price",price);
        model.addAttribute("sort",sort);

        return "product_list";


    }
}
