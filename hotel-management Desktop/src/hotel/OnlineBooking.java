package hotel;

public class OnlineBooking {
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
    public String customerPhone;
    public String roomNumber;  
    public String bedType;
    
    
    public OnlineBooking() {
    }
    
    
    public OnlineBooking(Long id, Integer userId, Integer roomId, String checkIn, String checkOut, 
                        String bookingDate, String status, Double totalPrice, String customerName, 
                        String customerEmail, String customerPhone, String roomNumber, String bedType) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookingDate = bookingDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.roomNumber = roomNumber;
        this.bedType = bedType;
    }
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    
    @Override
    public String toString() {
        return "OnlineBooking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
