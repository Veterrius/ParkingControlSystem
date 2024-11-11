package by.dlstudio.jlaynor.parking.model.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "parkings")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "location", nullable = false, unique = true)
    private String location;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "working_time", nullable = false)
    private String workingTime;

    @Column(name = "total_number_of_space", nullable = false)
    private Integer totalNumberOfSpace;

    @Column(name = "free_number_of_space", nullable = false)
    private Integer freeNumberOfSpace;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> listOfAdministrators = new HashSet<>();
}
