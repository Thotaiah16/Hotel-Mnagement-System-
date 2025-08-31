package hotel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiClient {
    
    private static final String BASE_URL = "http://localhost:8080/api/manager";
    
    public List<OnlineBooking> getAllBookings() {
        List<OnlineBooking> bookings = new ArrayList<>();
        
        try {
            String jsonResponse = makeGetRequest(BASE_URL + "/bookings");
            JSONArray jsonArray = new JSONArray(jsonResponse);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                OnlineBooking booking = new OnlineBooking();
                
                booking.id = obj.optLong("id");
                booking.userId = obj.optInt("userId");
                booking.roomId = obj.optInt("roomId");
                booking.checkIn = obj.optString("checkIn");
                booking.checkOut = obj.optString("checkOut");
                booking.bookingDate = obj.optString("bookingDate");
                booking.status = obj.optString("status");
                booking.totalPrice = obj.optDouble("totalPrice");
                booking.customerName = obj.optString("customerName");
                booking.customerEmail = obj.optString("customerEmail");
                
                bookings.add(booking);
            }
            
        } catch (Exception e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public List<OnlineFoodOrder> getAllFoodOrders() {
        List<OnlineFoodOrder> orders = new ArrayList<>();
        
        try {
            String jsonResponse = makeGetRequest(BASE_URL + "/food-orders");
            JSONArray jsonArray = new JSONArray(jsonResponse);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                OnlineFoodOrder order = new OnlineFoodOrder();
                
                order.id = obj.optLong("id");
                order.userId = obj.optInt("userId");
                order.foodName = obj.optString("foodName");
                order.quantity = obj.optInt("quantity");
                order.totalPrice = obj.optDouble("totalPrice");
                order.orderDate = obj.optString("orderDate");
                order.status = obj.optString("status");
                order.specialInstructions = obj.optString("specialInstructions");
                order.customerName = obj.optString("customerName");
                order.customerEmail = obj.optString("customerEmail");
                
                orders.add(order);
            }
            
        } catch (Exception e) {
            System.out.println("Error fetching food orders: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orders;
    }
    
    private String makeGetRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            
            return content.toString();
        } else {
            throw new Exception("API call failed with response code: " + responseCode);
        }
    }
}
