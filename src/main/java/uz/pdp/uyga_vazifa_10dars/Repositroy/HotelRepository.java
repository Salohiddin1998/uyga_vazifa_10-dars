package uz.pdp.uyga_vazifa_10dars.Repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uyga_vazifa_10dars.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    boolean existsHotelByName(String name);

}
