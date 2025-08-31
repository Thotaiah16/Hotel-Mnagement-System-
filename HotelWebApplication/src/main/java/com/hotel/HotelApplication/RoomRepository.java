package com.hotel.HotelApplication;

import com.hotel.HotelApplication.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     *
     * @param roomNo
     * @return
     */
    public Optional<Room> findByRoomNo(Integer roomNo);
};