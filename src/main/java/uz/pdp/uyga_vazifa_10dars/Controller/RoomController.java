package uz.pdp.uyga_vazifa_10dars.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uyga_vazifa_10dars.Repositroy.HotelRepository;
import uz.pdp.uyga_vazifa_10dars.Repositroy.RoomRepository;
import uz.pdp.uyga_vazifa_10dars.entity.Hotel;
import uz.pdp.uyga_vazifa_10dars.entity.Room;
import uz.pdp.uyga_vazifa_10dars.payload.RoomDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> all(){
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public Room get(@PathVariable Integer id){
        Optional<Room> optional = roomRepository.findById(id);
        return optional.orElseGet(Room::new);
    }

    @GetMapping("/page")
    public Page<Room> getAllHotelPage(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,5);
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/forHotelId/{id}")
    public Page<Room> getRoomList(@PathVariable Integer id, @RequestParam int page){
        Pageable pageable = PageRequest.of(page,5);
        return roomRepository.findAllByHotelId(id, pageable);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isEmpty()){
            return "Room not found";
        }
        roomRepository.deleteById(id);
        return "Room deleted";
    }

    @PostMapping
    public String add(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isEmpty()){
            return "Hotel not found";
        }

        boolean existHome = roomRepository.existsRoomByFloorAndNumberAndHotelId(roomDto.getFloor(), roomDto.getNumber(), roomDto.getHotelId());
        if (existHome){
            return "Home already exist";
        }

        Hotel hotel = optionalHotel.get();
        Room room = new Room();
        room.setHotel(hotel);
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        roomRepository.save(room);
        return "Room added";

    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody RoomDto roomDto){

        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()){
            return "Room not found";
        }

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isEmpty()){
            return "Hotel not found";
        }
        for (Room room : roomRepository.findAll()) {
            if (!room.getId().equals(id)
                    && room.getNumber().equals(roomDto.getNumber())
                    && room.getFloor().equals(roomDto.getFloor())
                    && room.getHotel().getId().equals(roomDto.getHotelId())){
                return "Room already exist";
            }
        }

        Room room = optionalRoom.get();
        Hotel hotel = optionalHotel.get();
        room.setHotel(hotel);
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        roomRepository.save(room);
        return "Room updated";

    }


}
