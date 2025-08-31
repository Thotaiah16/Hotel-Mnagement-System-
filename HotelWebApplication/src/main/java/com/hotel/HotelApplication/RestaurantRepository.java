package com.hotel.HotelApplication;

import com.hotel.HotelApplication.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<FoodItem, Long> {
    
    // Find by category (Type column in your database)
    @Query("SELECT f FROM FoodItem f WHERE f.category = :category")
    List<FoodItem> findByCategory(@Param("category") String category);
    
    // Find meals only
    @Query("SELECT f FROM FoodItem f WHERE f.category = 'MEAL'")
    List<FoodItem> findMeals();
    
    // Find drinks only
    @Query("SELECT f FROM FoodItem f WHERE f.category = 'DRINK'")
    List<FoodItem> findDrinks();
    
    // Find by name containing (for search)
    @Query("SELECT f FROM FoodItem f WHERE f.name LIKE %:name%")
    List<FoodItem> findByNameContaining(@Param("name") String name);
}
