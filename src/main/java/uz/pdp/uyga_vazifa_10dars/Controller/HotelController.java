package uz.pdp.uyga_vazifa_10dars.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uyga_vazifa_10dars.Repositroy.HotelRepository;
import uz.pdp.uyga_vazifa_10dars.entity.Hotel;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> all(){
       return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel get(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()){
            return "Hotel not found";
        }
        hotelRepository.deleteById(id);
        return "Hotel deleted";
    }

    @PostMapping
    public String add(@RequestBody Hotel hotel){
        Hotel saveHotel = new Hotel();
        boolean existsHotelByName = hotelRepository.existsHotelByName(hotel.getName());
        if (existsHotelByName){
            return "Hotel name already exist";
        }
        saveHotel.setName(hotel.getName());
        hotelRepository.save(saveHotel);
        return "Hotel added";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()){
            return "Hotel not found";
        }
        Hotel updateHotel = optionalHotel.get();
        boolean existsHotelByName = hotelRepository.existsHotelByName(hotel.getName());
        if (existsHotelByName){
            return "Hotel already exist";
        }
        updateHotel.setName(hotel.getName());
        hotelRepository.save(updateHotel);
        return "Hotel updated";
    }



}
