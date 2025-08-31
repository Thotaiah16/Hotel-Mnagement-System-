package com.hotel.HotelApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        try {
            List<Room> rooms = roomRepository.findAll();
            System.out.println("✅ RoomService: Found " + rooms.size() + " rooms");
            return rooms;
        } catch (Exception e) {
            System.out.println("❌ RoomService Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public Room saveRoom(Room room) {
        try {
            Room savedRoom = roomRepository.save(room);
            System.out.println("✅ RoomService: Saved room " + savedRoom.getRoomNo());
            return savedRoom;
        } catch (Exception e) {
            System.out.println("❌ RoomService Save Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

   
    public Optional<Room> findByRoomNo(Integer roomNo) {
        return roomRepository.findByRoomNo(roomNo);
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
        System.out.println("✅ RoomService: Deleted room with ID " + id);
    }
    
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }
}