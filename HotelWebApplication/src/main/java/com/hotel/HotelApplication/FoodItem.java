package com.hotel.HotelApplication;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant") 
public class FoodItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemNo") 
    private Long id;
    
    @Column(name = "itemName") 
    private String name;
    
    @Column(name = "Price") 
    private Double price;
    
    @Column(name = "Type") 
    private String category;
    
   
    @Transient
    private String imageUrl;
    
    @Transient
    private String description;
    
    @Transient
    private Boolean available = true;
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
