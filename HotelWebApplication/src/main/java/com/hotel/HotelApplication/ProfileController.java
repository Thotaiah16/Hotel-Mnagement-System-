package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                return "redirect:/login";
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                
                List<Booking> bookings = bookingRepository.findByUserId(user.getId().intValue());
                System.out.println("Loaded " + bookings.size() + " bookings for user: " + username);
                
                 
                List<FoodOrder> foodOrders = foodOrderRepository.findByUserId(user.getId().intValue());
                System.out.println("Loaded " + foodOrders.size() + " food orders for user: " + username);

                model.addAttribute("user", user);
                model.addAttribute("bookings", bookings);
                model.addAttribute("foodOrders", foodOrders);
                model.addAttribute("username", username);
                
                return "profile";
            } else {
                session.invalidate();
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("Profile error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Unable to load profile.");
            return "redirect:/dashboard";
        }
    }
}
