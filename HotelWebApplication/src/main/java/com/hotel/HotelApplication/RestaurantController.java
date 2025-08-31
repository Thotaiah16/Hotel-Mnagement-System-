package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {

    @Autowired
    private FoodItemRepository foodItemRepository; // or RestaurantRepository

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/order-food")
    public String showFoodMenu(Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            model.addAttribute("username", username);
            
            List<FoodItem> foodItems = foodItemRepository.findAll();
            
            
            for (FoodItem item : foodItems) {
                String itemName = item.getName() != null ? item.getName().toLowerCase() : "";
                String category = item.getCategory() != null ? item.getCategory().toLowerCase() : "";
                
                if (itemName.contains("paneer")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1631452180539-96aca7d48617?w=400&h=300&fit=crop");
                    item.setDescription("Rich and creamy paneer curry");
                } else if (itemName.contains("chocolate")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1511381939415-e44015466834?w=400&h=300&fit=crop");
                    item.setDescription("Decadent chocolate dessert");
                } else if (itemName.contains("biryani") || itemName.contains("mutton")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1563379091339-03246963d51a?w=400&h=300&fit=crop");
                    item.setDescription("Aromatic basmati rice with spices");
                } else if (itemName.contains("chicken")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1544025162-d76694265947?w=400&h=300&fit=crop");
                    item.setDescription("Tender chicken preparation");
                } else if (itemName.contains("tea")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400&h=300&fit=crop");
                    item.setDescription("Hot aromatic tea");
                } else if (itemName.contains("coffee") || itemName.contains("cappuccino")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=300&fit=crop");
                    item.setDescription("Rich coffee blend");
                } else if (itemName.contains("juice") || itemName.contains("mojito") || itemName.contains("sprite")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1541701494587-cb58502866ab?w=400&h=300&fit=crop");
                    item.setDescription("Fresh fruit juice");
                } else if (category.contains("drink")) {
                    item.setImageUrl("https://images.unsplash.com/photo-1514362545857-3bc16c4c76ea?w=400&h=300&fit=crop");
                    item.setDescription("Refreshing beverage");
                } else {
                    item.setImageUrl("https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400&h=300&fit=crop");
                    item.setDescription("Delicious food item");
                }
            }
            
            model.addAttribute("foodItems", foodItems);
            return "order-food";
            
        } catch (Exception e) {
            System.out.println("Error fetching food items: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("foodItems", List.of());
            model.addAttribute("error", "Unable to load food menu");
            return "order-food";
        }
    }

    
    @PostMapping("/order-food")
    public String orderFood(@RequestParam Long foodItemId,
                           @RequestParam Integer quantity,
                           @RequestParam(value = "specialInstructions", required = false) String specialInstructions,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                redirectAttributes.addFlashAttribute("error", "Please login to order food");
                return "redirect:/login";
            }

            if (quantity == null || quantity <= 0 || quantity > 10) {
                redirectAttributes.addFlashAttribute("error", "Invalid quantity. Please order between 1-10 items.");
                return "redirect:/order-food";
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            Optional<FoodItem> foodItemOpt = foodItemRepository.findById(foodItemId);

            if (userOpt.isPresent() && foodItemOpt.isPresent()) {
                User user = userOpt.get();
                FoodItem foodItem = foodItemOpt.get();

                Double itemPrice = foodItem.getPrice();
                if (itemPrice == null || itemPrice <= 0) {
                    redirectAttributes.addFlashAttribute("error", "Invalid price for " + foodItem.getName());
                    return "redirect:/order-food";
                }

                FoodOrder foodOrder = new FoodOrder();
                foodOrder.setUserId(user.getId().intValue());
                foodOrder.setFoodItemId(foodItem.getId());
                foodOrder.setFoodName(foodItem.getName());
                foodOrder.setQuantity(quantity);
                foodOrder.setTotalPrice(itemPrice * quantity);
                foodOrder.setStatus("CONFIRMED");
                foodOrder.setOrderDate(LocalDateTime.now());
                foodOrder.setSpecialInstructions(specialInstructions != null ? specialInstructions : "");

                foodOrderRepository.save(foodOrder);

                redirectAttributes.addFlashAttribute("success", 
                    "✅ Order Confirmed! " + foodItem.getName() + " x" + quantity + 
                    " - Total: ₹" + String.format("%.2f", foodOrder.getTotalPrice()));

                return "redirect:/order-food";
            } else {
                redirectAttributes.addFlashAttribute("error", "Food item or user not found.");
                return "redirect:/order-food";
            }
        } catch (Exception e) {
            System.out.println("Food order error: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Order failed. Please try again.");
            return "redirect:/order-food";
        }
    }
}
