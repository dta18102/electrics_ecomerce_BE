package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.*;
import com.example.medimateserver.entity.*;
import com.example.medimateserver.repository.*;
import com.example.medimateserver.service.OrderService;
import com.example.medimateserver.util.ConvertUtil;
import com.example.medimateserver.util.GsonUtil;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CouponDetailRepository couponDetailRepository;
    @Autowired
    UserRepository userRepository; // Sử dụng private cho đúng nguyên tắc

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Override
    public List<OrderDto> findAll() {
        List<Orders> ordersList = orderRepository.findAll();
        return ordersList.stream()
                .map(order -> ConvertUtil.gI().toDto(order, OrderDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public OrderDto findById(Integer id) {
        return orderRepository.findById(id)
                .map(order -> ConvertUtil.gI().toDto(order, OrderDto.class))
                .orElse(null);
    }
    @Transactional
    @Override
    public OrderDto save(PaymentDto paymentDto) {
            Integer total = 0;
            Integer discountProduct = 0;
            Integer discountCoupon = 0;
            Integer point = 0;

            // Tinh toan va kiem tra du lieu gui len
            List<CartDetail> cartDetailList = cartDetailRepository.findAll();

            for (CartDetailDto cartDetail : paymentDto.getCartDetailDtoList()) {
                Optional<Product> product = productRepository.findById(cartDetail.getProduct().getId());
                CartDetail.CartDetailId newId = new CartDetail.CartDetailId(paymentDto.getIdUser(), product.get().getId());
                Optional<CartDetail> cartDetail1 = cartDetailRepository.findById(newId);
                if (!cartDetail1.isPresent() || !product.isPresent() || cartDetail.getQuantity() > product.get().getQuantity() && product.get().getStatus() == 0) {
                    throw new IllegalArgumentException("Sản phẩm không đủ hoặc không bán nữa " + GsonUtil.gI().toJson(product.get()));
                }
                // Sản phẩm gửi lên thoả mãn trong csdl thì bắt đầu tính tổng tiền, một khi khác là báo lỗi
                if (cartDetail.getProduct().getId().toString().equals(product.get().getId().toString())) {
                    Integer discountFromProduct = Integer.parseInt(((int) product.get().getPrice()*product.get().getDiscountPercent()/100)+"");
                    discountProduct += discountFromProduct;
                    total += cartDetail.getQuantity() * product.get().getPrice() - discountFromProduct;
                } else {
                    throw new IllegalArgumentException("Sản phẩm không đủ hoặc không bán nữa " + GsonUtil.gI().toJson(product.get()));
                }
            }

            // Neu co khuyen mai thi tinh tien giam tu khuyen mai k thi thoi
            Date now = new Date();
            if (paymentDto.getCouponDetailId() != null) {
                Optional<CouponDetail> couponDetail = couponDetailRepository.findById(paymentDto.getCouponDetailId());
                if (couponDetail.isPresent() && couponDetail.get().getIdUser() == paymentDto.getIdUser() && couponDetail.get().getStatus() == 1) {
                    Date date = couponDetail.get().getEndTime();
                    // Kiểm tra xem ngày hiện tại có sau ngày hết hạn k, nếu sau, trả về true, lỗi
                    boolean isAfter = now.after(date);
                    if (isAfter) {
                        throw new IllegalArgumentException("Khuyến mãi hết hạn!");
                    }
                    discountCoupon = Integer.parseInt(((int)total * couponDetail.get().getCoupon().getDiscountPercent()/100)+"");
                    total -= discountCoupon;
                } else {
                    throw new IllegalArgumentException("Khuyến mãi đã sử dụng hoặc khng đng!");
                }
            }

            point = Integer.parseInt(((int)total*1/1000) + "");

            Optional<User> userDb = userRepository.findById(paymentDto.getIdUser());
            if (!userDb.isPresent()) {
                throw new IllegalArgumentException("User lỗi!");
            }
            User savedUser = userDb.get();

            Orders order = new Orders();
            String code = savedUser.getId()+""+now.getTime();
            order.setCode("MDH"+code.hashCode()+"");
            order.setIdUser(paymentDto.getIdUser());
            order.setDiscountCoupon(discountCoupon);
            order.setDiscountProduct(discountProduct);
            order.setOrderTime(now);
            order.setPoint(point);
            order.setNote(paymentDto.getOrder().getNote());
            order.setTotal(total);
            order.setPaymentMethod(paymentDto.getOrder().getPaymentMethod());
            order.setUserAddress(paymentDto.getOrder().getUserAddress());
            order.setStatus(2);
            order = orderRepository.save(order);


            // Lưu lại cartdetail
            for (CartDetailDto cartDetail : paymentDto.getCartDetailDtoList()) {
                Optional<Product> product = productRepository.findById(cartDetail.getProduct().getId());
                if (!product.isPresent() || cartDetail.getQuantity() > product.get().getQuantity() && product.get().getStatus() == 0) {
                    throw new IllegalArgumentException("Sản phẩm không đủ hoặc không bán nữa " + GsonUtil.gI().toJson(product.get()));
                }
                // Xoá sản phẩm trong cart detail và tạo orderDetail
                if (cartDetail.getProduct().getId().toString().equals(product.get().getId().toString())) {
                    CartDetail.CartDetailId newId = new CartDetail.CartDetailId(paymentDto.getIdUser(), product.get().getId());
                    cartDetailRepository.deleteById(newId);
                    Integer discountFromProduct = Integer.parseInt(((int) product.get().getPrice()*product.get().getDiscountPercent()/100)+"");
                    OrderDetail.OrderDetailId newOrderDetailId = new OrderDetail.OrderDetailId(order.getId(), product.get().getId());
                    OrderDetail newOrder = new OrderDetail(newOrderDetailId, product.get().getPrice(), discountFromProduct, cartDetail.getQuantity(), false);
                    newOrder.setOrders(order);
                    newOrder.setProduct(product.get());
                    orderDetailRepository.save(newOrder);
                }
            }

            // Lưu lại số lượng sản phẩm
            List<Product> productList = new ArrayList<>();
            for (CartDetailDto cartDetail : paymentDto.getCartDetailDtoList()) {
                Optional<Product> product = productRepository.findById(cartDetail.getProduct().getId());
                product.get().setQuantity(product.get().getQuantity() - cartDetail.getQuantity());
                productList.add(product.get());
            }
            productRepository.saveAll(productList);

            // Lưu lại trạng thái coupon
            if (paymentDto.getCouponDetailId() !=null) {
                Optional<CouponDetail> couponDetail = couponDetailRepository.findById(paymentDto.getCouponDetailId());
                if (couponDetail.isPresent() && couponDetail.get().getIdUser() == paymentDto.getIdUser() && couponDetail.get().getStatus() == 1) {
                    CouponDetail savedCoupon = couponDetail.get();
                    savedCoupon.setIdOrder(order.getId());
                    savedCoupon.setStatus(0);
                    couponDetailRepository.save(savedCoupon);
                }
            }

            // Lưu lại điểm người dùng
            savedUser.setPoint(savedUser.getPoint()+order.getPoint());
            userRepository.save(savedUser);

            return ConvertUtil.gI().toDto(order, OrderDto.class);
    }


    private List<OrderDetail> createOrderDetails(PaymentDto paymentDto, Integer discountProduct) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetailDto cartDetail : paymentDto.getCartDetailDtoList()) {
            Optional<Product> product = productRepository.findById(cartDetail.getProduct().getId());
            if (!product.isPresent()) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại " + GsonUtil.gI().toJson(cartDetail.getProduct()));
            }
            Integer discountFromProduct = Integer.parseInt(((int)product.get().getPrice()*product.get().getDiscountPercent()/100)+"");
            OrderDetail.OrderDetailId newId = new OrderDetail.OrderDetailId(paymentDto.getIdUser(), product.get().getId());
            OrderDetail newOrder = new OrderDetail(newId, product.get().getPrice(), discountFromProduct, cartDetail.getQuantity(), false);
            orderDetails.add(newOrder);
        }
        return orderDetails;
    }

    @Override
    public List<OrderDto> findByIdUser(Integer id) {
        List<Orders> ordersList = orderRepository.findByIdUser(id);
        return ordersList
                .stream()
                .map(order -> ConvertUtil.gI().toDto(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto saveWithOrderDetail(PaymentDto paymentDto) {
        return null;
    }

    @Override
    public OrderDto confirmOrder(Integer id) {
        Optional<Orders> newOrder = orderRepository.findById(id);
        Orders uOrder = newOrder.get();
        uOrder.setStatus(1);
        OrderDto orderDto = ConvertUtil.gI().toDto(orderRepository.save(uOrder),OrderDto.class);
        return orderDto;
    }


}
