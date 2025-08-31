package com.hotel.HotelApplication;

import jakarta.persistence.*;

@Entity
@Table(name = "room") // Your actual table name
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "roomNo") // Your actual column name
    private Integer roomNo;
    
    @Column(name = "roomType") // Your actual column name
    private String roomType;
    
    @Column(name = "bedType") // Your actual column name
    private String bedType;
    
    @Column(name = "Price") // Your actual column name (capital P)
    private Double price;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getRoomNo() { return roomNo; }
    public void setRoomNo(Integer roomNo) { this.roomNo = roomNo; }
    
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    
    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}