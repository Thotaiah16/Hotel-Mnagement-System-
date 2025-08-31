package com.hotel.HotelApplication;

import com.hotel.HotelWebApp.EmailService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        try {
            model.addAttribute("showRegisterForm", true);
            return "register";
        } catch (Exception e) {
            System.out.println("Registration form error: " + e.getMessage());
            model.addAttribute("errorMessage", "Unable to load registration form.");
            return "register";
        }
    }

    @PostMapping("/register")
    public String handleRegistration(@RequestParam String username, 
                                   @RequestParam String email, 
                                   @RequestParam String mobile, 
                                   HttpSession session, 
                                   Model model) {
        try {
            // Validate input
            if (username == null || username.trim().isEmpty()) {
                model.addAttribute("errorMessage", "Username is required.");
                model.addAttribute("showRegisterForm", true);
                return "register";
            }

            if (email == null || email.trim().isEmpty()) {
                model.addAttribute("errorMessage", "Email is required.");
                model.addAttribute("showRegisterForm", true);
                return "register";
            }

            
            if (userRepository.findByUsername(username).isPresent() || 
                userRepository.findByEmail(email).isPresent()) {
                model.addAttribute("errorMessage", "A user with that username or email already exists.");
                model.addAttribute("showRegisterForm", true);
                return "register";
            }

            // Generate OTP
            String otp = String.format("%06d", new Random().nextInt(999999));
            
            
            boolean emailSent = false;
            try {
                emailSent = EmailService.sendOtpEmail(email, otp);
            } catch (Exception e) {
                System.out.println("Email service not available: " + e.getMessage());
                
                emailSent = false;
            }

            if (emailSent) {
                session.setAttribute("registrationUsername", username);
                session.setAttribute("registrationEmail", email);
                session.setAttribute("registrationMobile", mobile);
                session.setAttribute("generatedOtp", otp);
                model.addAttribute("showOtpForm", true);
                return "register";
            } else {
                
                session.setAttribute("registrationUsername", username);
                session.setAttribute("registrationEmail", email);
                session.setAttribute("registrationMobile", mobile);
                model.addAttribute("showPasswordForm", true);
                return "register";
            }
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            model.addAttribute("showRegisterForm", true);
            return "register";
        }
    }

    @PostMapping("/verify-otp")
    public String handleOtpVerification(@RequestParam String otp, 
                                       HttpSession session, 
                                       Model model) {
        try {
            String generatedOtp = (String) session.getAttribute("generatedOtp");
            
            if (otp != null && otp.equals(generatedOtp)) {
                model.addAttribute("showPasswordForm", true);
            } else {
                model.addAttribute("errorMessage", "Invalid OTP. Please try again.");
                model.addAttribute("showOtpForm", true);
            }
            return "register";
        } catch (Exception e) {
            System.out.println("OTP verification error: " + e.getMessage());
            model.addAttribute("errorMessage", "OTP verification failed.");
            model.addAttribute("showPasswordForm", true); 
            return "register";
        }
    }

    @PostMapping("/set-password")
    public String handleSetPassword(@RequestParam String password, 
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (password == null || password.length() < 6) {
                redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 6 characters long.");
                return "redirect:/register";
            }

            String username = (String) session.getAttribute("registrationUsername");
            String email = (String) session.getAttribute("registrationEmail");
            String mobile = (String) session.getAttribute("registrationMobile");

            if (username == null || email == null || mobile == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Session expired. Please register again.");
                return "redirect:/register";
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setMobileNumber(mobile);
            newUser.setPasswordHash(hashedPassword);
            newUser.setRole("customer");

            userRepository.save(newUser);
            session.invalidate();

            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";

        } catch (Exception e) {
            System.out.println("Set password error: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create account.");
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, 
                               @RequestParam(required = false) String registrationSuccess) {
        if ("true".equals(registrationSuccess)) {
            model.addAttribute("successMessage", "Registration successful! Please login.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, 
                             @RequestParam String password, 
                             HttpSession session, 
                             Model model) {
        try {
            if (username == null || username.trim().isEmpty()) {
                model.addAttribute("loginError", "Username is required.");
                return "login";
            }

            if (password == null || password.trim().isEmpty()) {
                model.addAttribute("loginError", "Password is required.");
                return "login";
            }

            Optional<User> optionalUser = userRepository.findByUsername(username.trim());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (BCrypt.checkpw(password, user.getPasswordHash())) {
                    session.setAttribute("loggedInUser", user.getUsername());
                    session.setAttribute("userRole", user.getRole());
                    return "redirect:/dashboard";
                }
            }

            model.addAttribute("loginError", "Invalid username or password.");
            return "login";

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("loginError", "Login failed. Please try again.");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                return "redirect:/login";
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);
                model.addAttribute("username", username);
                model.addAttribute("activePage", "dashboard");
                return "dashboard";
            } else {
                session.invalidate();
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("Dashboard error: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/user-profile")
    public String showProfile(HttpSession session, Model model) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                return "redirect:/login";
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Get user's bookings and food orders with error handling
                List<Booking> bookings = List.of();
                List<FoodOrder> foodOrders = List.of();
                
                try {
                    bookings = bookingRepository.findByUserId(user.getId().intValue());
                } catch (Exception e) {
                    System.out.println("Error loading bookings: " + e.getMessage());
                }
                
                try {
                    foodOrders = foodOrderRepository.findByUserId(user.getId().intValue());
                } catch (Exception e) {
                    System.out.println("Error loading food orders: " + e.getMessage());
                }

                model.addAttribute("user", user);
                model.addAttribute("bookings", bookings);
                model.addAttribute("foodOrders", foodOrders);
                model.addAttribute("username", username);
                model.addAttribute("activePage", "profile");
                
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

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            session.invalidate();
            redirectAttributes.addFlashAttribute("successMessage", "Logged out successfully.");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
            return "redirect:/login";
        }
    }
}