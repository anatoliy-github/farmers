package ru.net.pakhomov.farmers.api.model.district;

import ru.net.pakhomov.farmers.api.model.farmer.Farmer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "archive")
    private boolean archive;

    @OneToMany(mappedBy = "registerDistrict", fetch=FetchType.EAGER)
    private List<Farmer> farmers;

}
