package uz.pdp.uyga_vazifa_10dars.Repositroy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uyga_vazifa_10dars.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsRoomByFloorAndNumberAndHotelId(Integer floor, Integer number, Integer hotel_id);

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);




}
