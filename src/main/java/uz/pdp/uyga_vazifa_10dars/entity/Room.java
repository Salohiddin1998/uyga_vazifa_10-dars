package uz.pdp.uyga_vazifa_10dars.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number;

    private Integer floor;

    private Integer size;

    @ManyToOne
    private Hotel hotel;
}
