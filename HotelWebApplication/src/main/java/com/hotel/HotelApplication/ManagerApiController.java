package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*")
public class ManagerApiController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bookings")
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponse> response = new ArrayList<>();
        
        for (Booking booking : bookings) {
            BookingResponse br = new BookingResponse();
            br.id = booking.getId();
            br.userId = booking.getUserId();
            br.roomId = booking.getRoomId();
            br.checkIn = booking.getCheckIn().toString();
            br.checkOut = booking.getCheckOut().toString();
            br.bookingDate = booking.getBookingDate().toString();
            br.status = booking.getStatus();
            br.totalPrice = booking.getTotalPrice();
            
            
            try {
                var user = userRepository.findById(Long.valueOf(booking.getUserId()));
                br.customerName = user.isPresent() ? user.get().getUsername() : "Unknown";
                br.customerEmail = user.isPresent() ? user.get().getEmail() : "";
            } catch (Exception e) {
                br.customerName = "Unknown";
                br.customerEmail = "";
            }
            
            response.add(br);
        }
        
        return response;
    }

    @GetMapping("/food-orders")
    public List<FoodOrderResponse> getAllFoodOrders() {
        List<FoodOrder> orders = foodOrderRepository.findAll();
        List<FoodOrderResponse> response = new ArrayList<>();
        
        for (FoodOrder order : orders) {
            FoodOrderResponse fr = new FoodOrderResponse();
            fr.id = order.getId();
            fr.userId = order.getUserId();
            fr.foodName = order.getFoodName();
            fr.quantity = order.getQuantity();
            fr.totalPrice = order.getTotalPrice();
            fr.orderDate = order.getOrderDate().toString();
            fr.status = order.getStatus();
            fr.specialInstructions = order.getSpecialInstructions();
            
            
            try {
                var user = userRepository.findById(Long.valueOf(order.getUserId()));
                fr.customerName = user.isPresent() ? user.get().getUsername() : "Unknown";
                fr.customerEmail = user.isPresent() ? user.get().getEmail() : "";
            } catch (Exception e) {
                fr.customerName = "Unknown";
                fr.customerEmail = "";
            }
            
            response.add(fr);
        }
        
        return response;
    }

    
    public static class BookingResponse {
        public Long id;
        public Integer userId;
        public Integer roomId;
        public String checkIn;
        public String checkOut;
        public String bookingDate;
        public String status;
        public Double totalPrice;
        public String customerName;
        public String customerEmail;
    }

    public static class FoodOrderResponse {
        public Long id;
        public Integer userId;
        public String foodName;
        public Integer quantity;
        public Double totalPrice;
        public String orderDate;
        public String status;
        public String specialInstructions;
        public String customerName;
        public String customerEmail;
    }
}
