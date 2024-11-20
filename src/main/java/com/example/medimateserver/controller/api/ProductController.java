package com.example.medimateserver.controller.api;

import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.filter.ProductFilter;
import com.example.medimateserver.service.ProductService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product", produces = "application/json")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.findAllWithPage();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/new_product")
    public ResponseEntity<?> getNewProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.getNewProduct();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/best_sellers")
    public ResponseEntity<?> getBestSellersProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.getBestSellersProduct();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/have_sold")
    public ResponseEntity<?> getHaveSoldProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.getHaveSoldProduct();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/best_promotion")
    public ResponseEntity<?> getBestPromotionProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.getBestPromotionProduct();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/have_promotion")
    public ResponseEntity<?> getHavePromotionProduct() throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.getHavePromotionProduct();
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getFilterProduct(@RequestBody ProductFilter productFilter) throws JsonProcessingException {
        try {
            List<ProductDto> productList = productService.findWithFilterTraditional(productFilter);
            String jsons = GsonUtil.gI().toJson(productList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody ProductDto productDto) {
        ProductDto savedCategory = productService.save(productDto);
        return ResponseUtil.success();
    }

    // Update a category
    @PutMapping
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody ProductDto category) {
        return ResponseUtil.success();
    }

    // Delete a category
    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            ProductDto product = productService.findById(id);
            product.setStatus(0);
            productService.save(product);
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
