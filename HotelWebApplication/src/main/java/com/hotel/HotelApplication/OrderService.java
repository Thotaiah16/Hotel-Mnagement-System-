package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Map<String, Object> orderData) {
        Order order = new Order();
        
        order.setCustomerName((String) orderData.get("customerName"));
        order.setCustomerPhone((String) orderData.get("customerPhone"));
        order.setTableNumber((String) orderData.get("tableNumber"));
        order.setSpecialInstructions((String) orderData.get("specialInstructions"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) orderData.get("items");
        
        for (Map<String, Object> itemData : items) {
            String itemName = (String) itemData.get("itemName");
            String itemType = (String) itemData.get("itemType");
            Double price = Double.valueOf(itemData.get("price").toString());
            Integer quantity = Integer.valueOf(itemData.get("quantity").toString());
            
            OrderItem orderItem = new OrderItem(itemName, itemType, price, quantity);
            order.addItem(orderItem);
        }
        
        order.calculateTotal();
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getTodayOrders() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        return orderRepository.findByOrderDateBetween(startOfDay, endOfDay);
    }
}
