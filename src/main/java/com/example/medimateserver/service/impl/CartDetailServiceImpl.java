package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.CartDetailDto;
import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.CartDetail;
import com.example.medimateserver.entity.Product;
import com.example.medimateserver.entity.User;
import com.example.medimateserver.repository.CartDetailRepository;
import com.example.medimateserver.repository.ProductRepository;
import com.example.medimateserver.repository.UserRepository;
import com.example.medimateserver.service.CartDetailService;
import com.example.medimateserver.util.ConvertUtil;
import com.example.medimateserver.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<CartDetailDto> findAll() {
        List<CartDetail> cartDetailList = cartDetailRepository.findAll();
        return cartDetailList.stream()
                .map(this::toDto) // Sử dụng hàm toDto trong cùng lớp
                .collect(Collectors.toList());
    }

    public List<CartDetailDto> findByIdUser(Integer id) {
        List<CartDetail> CartDetails = cartDetailRepository.findByIdUser(id);
        return CartDetails.stream()
                .map(this::toDto) // Sử dụng hàm toDto trong cùng lớp
                .collect(Collectors.toList());
    }

    @Override
    public void saveCartDetail(CartDetailDto cartDetailDto) {

        CartDetail.CartDetailId id = new CartDetail.CartDetailId(cartDetailDto.getUser().getId(), cartDetailDto.getProduct().getId());

        Optional<Product> productOptional = productRepository.findById(id.getIdProduct());
        Optional<User> userOptional = userRepository.findById(id.getIdUser()); // Sửa id.getIdUser()

        // Kiểm tra xem product và user có tồn tại không
        if (productOptional.isPresent() && userOptional.isPresent()) {
            Product uProduct = productOptional.get();
            User uUser = userOptional.get();

            Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);

            // Nếu chi tiết giỏ hàng đã tồn tại, cập nhật số lượng
            if (cartDetailOptional.isPresent()) {
                CartDetail uCartDetail = cartDetailOptional.get();
                if (uProduct.getQuantity() - (uCartDetail.getQuantity() + cartDetailDto.getQuantity()) >= 0) {
                    uCartDetail.setQuantity(uCartDetail.getQuantity() + cartDetailDto.getQuantity());
                    cartDetailRepository.save(uCartDetail);
                } else {
                    throw new IllegalArgumentException("Không đủ số lượng sản phâẩm");
                }

            } else {
                // Nếu chi tiết giỏ hàng chưa tồn tại, lưu mới
                CartDetail newCartDetail = new CartDetail(id, uProduct.getPrice() * uProduct.getDiscountPercent() / 100, 1);
                newCartDetail.setProduct(uProduct);
                newCartDetail.setUser(uUser);
                newCartDetail.setId(id);
                if (uProduct.getQuantity() - cartDetailDto.getQuantity() >= 0 && cartDetailDto.getQuantity() > 0) {
                    newCartDetail.setQuantity(cartDetailDto.getQuantity());
                    cartDetailRepository.save(newCartDetail);
                } else {
                    throw new IllegalArgumentException("Không đủ số lượng sản phâẩm");
                }
            }
        } else {
            // Xử lý trường hợp không tìm thấy product hoặc user
            // Ví dụ: ném ngoại lệ hoặc trả về thông báo lỗi
        }
    }


    @Override
    public void updateCartDetail(CartDetailDto cartDetailDto) {
        CartDetail.CartDetailId id = new CartDetail.CartDetailId(cartDetailDto.getUser().getId(), cartDetailDto.getProduct().getId());

        Optional<Product> productOptional = productRepository.findById(id.getIdProduct());
        Optional<User> userOptional = userRepository.findById(id.getIdUser()); // Sửa id.getIdUser()

        // Kiểm tra xem product và user có tồn tại không
        if (productOptional.isPresent() && userOptional.isPresent()) {
            Product uProduct = productOptional.get();
            User uUser = userOptional.get();

            Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);

            // Nếu chi tiết giỏ hàng đã tồn tại, cập nhật số lượng
            System.out.println("123");
            if (cartDetailOptional.isPresent()) {
                // Nếu số lượng <= 0 xoá luôn
                if (cartDetailDto.getQuantity() <= 0) {
                    CartDetail newCartDetail = new CartDetail(id, uProduct.getPrice() * uProduct.getDiscountPercent() / 100, 1);
                    newCartDetail.setProduct(uProduct);
                    newCartDetail.setUser(uUser);
                    newCartDetail.setId(id);
                    cartDetailRepository.delete(newCartDetail);
                    return;
                }

                CartDetail uCartDetail = cartDetailOptional.get();
                if (uProduct.getQuantity() - cartDetailDto.getQuantity() >= 0) {
                    uCartDetail.setQuantity(cartDetailDto.getQuantity());
                    cartDetailRepository.save(uCartDetail);
                    return;
                }
                System.out.println("321");
            } else {
                throw new IllegalArgumentException("Sai lỗi 1");
            }
        } else {
            throw new IllegalArgumentException("Sai lỗi 2");
        }
    }

    @Override
    public void deleteCartDetail(Integer idUser, Integer idProduct) {
        CartDetail.CartDetailId id = new CartDetail.CartDetailId(idUser, idProduct);
        cartDetailRepository.deleteById(id);
    }

    @Override
    public void deleteCartDetail(Integer idUser, List<CartDetailDto> cartDetailDto) {
        for (CartDetailDto cart: cartDetailDto) {
            CartDetail.CartDetailId id = new CartDetail.CartDetailId(idUser, cart.getProduct().getId());
            cartDetailRepository.deleteById(id);
        }
    }


    public CartDetailDto toDto(CartDetail CartDetail) {
        CartDetailDto dto = new CartDetailDto();
        dto.setUser(ConvertUtil.gI().toDto(CartDetail.getUser(), UserDto.class));
        dto.setProduct(ConvertUtil.gI().toDto(CartDetail.getProduct(), ProductDto.class));
//        dto.setDiscountPrice(CartDetail.getDiscountPrice());
        dto.setQuantity(CartDetail.getQuantity());
        dto.setProduct(ConvertUtil.gI().toDto(CartDetail.getProduct(), ProductDto.class));
        dto.setUser(ConvertUtil.gI().toDto(CartDetail.getUser(), UserDto.class));
        return dto;
    }

    public CartDetail toEntity(CartDetailDto dto) {
        CartDetail.CartDetailId id = new CartDetail.CartDetailId(dto.getUser().getId(), dto.getProduct().getId()); // Cách 2, sau khi thêm static
        return new CartDetail(id, dto.getQuantity());
    }

}
