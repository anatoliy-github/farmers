package ru.net.pakhomov.farmers.api.model.farmer;

import ru.net.pakhomov.farmers.api.model.district.District;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "legal_form", nullable = false)
    @Convert(converter = LegalFormConverter.class)
    private LegalForm legalForm;

    @Column(name = "inn", nullable = false, unique = true)
    private String inn;

    @Column
    private String kpp;

    @Column
    private String ogrn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District registerDistrict;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "farmer_district",
            joinColumns = @JoinColumn(name = "farmer_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id")
    )
    private List<District> sowingFields;

    @Column(name = "archive")
    private boolean archive;

    public List<String> getListDistrictNameSowingFields() {
        return sowingFields.stream().map(District::getName).collect(Collectors.toList());
    }

    @CreationTimestamp
    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

}
