package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.Product;
import com.example.medimateserver.filter.ProductFilter;
import com.example.medimateserver.service.ProductService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
            List<ProductDto> prods = productList.stream()
                    .filter(prod -> prod.getStatus() == 1)  //filter status == 1
                    .toList();
            String jsons = GsonUtil.gI().toJson(prods);
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
    public ResponseEntity<?> createProduct(HttpServletRequest request, @RequestBody ProductDto productDto) {

        try {
            productDto.setDiscountPercent(10);
            productDto.setStatus(1);
            ProductDto savedCategory = productService.save(productDto);
//            ProductDto find = productService.save(savedCategory);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedCategory));
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }

    }

    // Update a category
    @PutMapping
    public ResponseEntity<?> updateProduct( @RequestBody ProductDto productUpdate) {
        try{
            ProductDto prod = productService.findById(productUpdate.getId());

            if(productUpdate.getIdCategory() != null){
                prod.setIdCategory(productUpdate.getIdCategory());
            }
            if(productUpdate.getName() != null){
                prod.setName(productUpdate.getName());
            }
            if(productUpdate.getDescription() != null){
                prod.setDescription(productUpdate.getDescription());
            }
            if(productUpdate.getDiscountPercent() != null){
                prod.setDiscountPercent(productUpdate.getDiscountPercent());
            }
            if(productUpdate.getPrice() != null){
                prod.setPrice(productUpdate.getPrice());
            }
            if(productUpdate.getQuantity() != null){
                prod.setQuantity(productUpdate.getQuantity());
            }
            if(productUpdate.getImage() != null){
                prod.setImage(productUpdate.getImage());
            }
            ProductDto savedProduct = productService.save(prod);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedProduct));
        }catch (Exception ex){
            return ResponseUtil.failed();
        }

    }

    // Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            ProductDto product = productService.findById(id);
            product.setStatus(0);
            ProductDto prod = productService.save(product);
            return ResponseUtil.success(GsonUtil.gI().toJson(prod));
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
