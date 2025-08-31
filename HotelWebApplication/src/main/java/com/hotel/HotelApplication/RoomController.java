package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    // GET mapping to VIEW the room booking page
    @GetMapping("/room-booking")
    public String showRoomBookingPage(Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                return "redirect:/login";
            }
            
            List<Room> rooms = roomRepository.findAll();
            System.out.println("Rooms loaded: " + rooms.size());
            
            model.addAttribute("rooms", rooms);
            model.addAttribute("username", username);
            
            return "room-booking";
        } catch (Exception e) {
            System.out.println("Error loading rooms: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Unable to load rooms");
            model.addAttribute("rooms", List.of());
            return "room-booking";
        }
    }

    
    @GetMapping("/book-room")
    public String redirectToBookingPage() {
        return "redirect:/room-booking";
    }

    // POST mapping to PROCESS the room booking
    @PostMapping("/book-room")
    public String bookRoom(@RequestParam Long roomId,
                           @RequestParam String checkIn,
                           @RequestParam String checkOut,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            String username = (String) session.getAttribute("loggedInUser");
            if (username == null) {
                redirectAttributes.addFlashAttribute("error", "Please login to book a room");
                return "redirect:/login";
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            Optional<Room> roomOpt = roomRepository.findById(roomId);

            if (userOpt.isPresent() && roomOpt.isPresent()) {
                User user = userOpt.get();
                Room room = roomOpt.get();

                LocalDate checkInDate = LocalDate.parse(checkIn);
                LocalDate checkOutDate = LocalDate.parse(checkOut);
                long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                if (days <= 0) days = 1;

                Double roomPrice = room.getPrice();
                if (roomPrice == null || roomPrice <= 0) {
                    redirectAttributes.addFlashAttribute("error", "Invalid room price");
                    return "redirect:/room-booking";
                }

                Booking booking = new Booking();
                booking.setUserId(user.getId().intValue());
                booking.setRoomId(room.getId().intValue());
                booking.setCheckIn(checkInDate);
                booking.setCheckOut(checkOutDate);
                booking.setBookingDate(LocalDate.now());
                booking.setStatus("CONFIRMED");
                booking.setTotalPrice(roomPrice * (double) days);

                bookingRepository.save(booking);

                redirectAttributes.addFlashAttribute("success", 
                    "✅ Room " + room.getRoomNo() + " booked successfully! " +
                    days + " day(s) - Total: ₹" + String.format("%.2f", booking.getTotalPrice()));

                return "redirect:/room-booking";
            } else {
                redirectAttributes.addFlashAttribute("error", "Room or user not found");
                return "redirect:/room-booking";
            }
        } catch (Exception e) {
            System.out.println("Booking error: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
            return "redirect:/room-booking";
        }
    }
}