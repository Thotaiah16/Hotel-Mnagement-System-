package com.hotel.HotelApplication;
import jakarta.persistence.*;
@Entity @Table(name = "restaurant")
public class RestaurantItem {
    @Id
    private Integer itemNo;
    private String itemName;
    @Column(name = "Price")
    private Integer price;
    @Column(name = "Type")
    private String type;
    // Getters and Setters
    public Integer getItemNo() { return itemNo; }
    public void setItemNo(Integer itemNo) { this.itemNo = itemNo; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}