package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.dto.UnitDto;
import com.example.medimateserver.entity.Product;
import com.example.medimateserver.entity.Unit;
import com.example.medimateserver.filter.Pagination;
import com.example.medimateserver.filter.ProductFilter;
import com.example.medimateserver.repository.ProductRepository;
import com.example.medimateserver.service.ProductService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        System.out.println(products.get(0).getUnit().getName());
        return products.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProductDto> findAllWithPage(Integer page) {
//        Pageable pageable = PageRequest.of(page, 10);
//        Page<Product> productList = productRepository.findAll(pageable);
//        return productList
//                .stream()
//                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
//                .collect(Collectors.toList());
//    }
    @Override
    public List<ProductDto> findAllWithPage() {
//        Pageable pageable = PageRequest.of(page, 10);
        List<Product> productList = productRepository.findAll();
        return productList
                .stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findWithFilter(ProductFilter productFilter) {
        Pageable pageable = PageRequest.of(productFilter.getCurrentPage()-1, 10);
        List<Product> productList = productRepository.findWithFilterTraditional(productFilter.getIdCategory(),productFilter.getMinPrice(), productFilter.getMaxPrice(), productFilter.getKeySearch());
        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProductDto> findWithFilterTraditional(ProductFilter productFilter, Integer id) {
//        // Giả sử productFilter chứa các giá trị lọc
//        int currentPage = id+1; // Trang hiện tại (bắt đầu từ 1)
//        int pageSize = 10; // Số phần tử trên mỗi trang
//        int offset = (currentPage - 1) * pageSize;
//        List<Product> productList = productRepository.findWithFilterTraditional(productFilter.getIdCategory(),productFilter.getMinPrice(), productFilter.getMaxPrice(), productFilter.getKeySearch(), pageSize, offset);
//
//        return productList.stream()
//                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
//                .collect(Collectors.toList());
//    }
    @Override
    public List<ProductDto> findWithFilterTraditional(ProductFilter productFilter) {
        List<Product> productList = productRepository.findWithFilterTraditional(productFilter.getIdCategory(),productFilter.getMinPrice(), productFilter.getMaxPrice(), productFilter.getKeySearch());

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getNewProduct() {

        List<Product> productList = productRepository.getNewProduct();

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getBestSellersProduct() {

        List<Product> productList = productRepository.getBestSellersProduct();

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getHaveSoldProduct() {
        List<Product> productList = productRepository.getHaveSoldProduct();

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getBestPromotionProduct() {

        List<Product> productList = productRepository.getBestPromotionProduct();

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getHavePromotionProduct() {
        List<Product> productList = productRepository.getHavePromotionProduct();

        return productList.stream()
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Integer id) {
        return productRepository.findById(id)
                .map(product -> ConvertUtil.gI().toDto(product, ProductDto.class))
                .orElse(null);
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = ConvertUtil.gI().toEntity(productDto, Product.class);
        product = productRepository.save(product);
        return ConvertUtil.gI().toDto(product, ProductDto.class);
    }

    @Override
    public ProductDto update(Integer id, ProductDto product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // Cập nhật các thuộc tính từ categoryDto sang existingCategory
        existingProduct.setName(product.getName()); // Ví dụ, gán name mới
        // Cập nhật các thuộc tính khác nếu cần

        Product updatedCategory = productRepository.save(existingProduct);
        return ConvertUtil.gI().toDto(updatedCategory, ProductDto.class);
    }

    @Override
    public void deleteById(Integer id) {

    }


}
