package com.hundsun.ctim.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.hundsun.ctim.dao.product.ProductSampleDao;

@Service
public class ProductSampleServiceImp extends BaseService {
    @Autowired
    private ProductSampleDao entityDao;

}