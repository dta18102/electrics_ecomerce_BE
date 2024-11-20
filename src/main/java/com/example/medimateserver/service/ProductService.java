package com.example.medimateserver.service;

import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.filter.Pagination;
import com.example.medimateserver.filter.ProductFilter;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {

    List<ProductDto> findAll();
    List<ProductDto> findAllWithPage();
    List<ProductDto> findWithFilter(ProductFilter productFilter);
    List<ProductDto> findWithFilterTraditional(ProductFilter productFilter);
    List<ProductDto> getNewProduct();
    List<ProductDto> getBestSellersProduct();
    List<ProductDto> getHaveSoldProduct();
    List<ProductDto> getBestPromotionProduct();
    List<ProductDto> getHavePromotionProduct();
    ProductDto findById(Integer id);
    ProductDto save(ProductDto productDto);
    ProductDto update(Integer id, ProductDto productDto);
    void deleteById(Integer id);
}
