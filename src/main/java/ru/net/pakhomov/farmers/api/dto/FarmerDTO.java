package ru.net.pakhomov.farmers.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность фермера")
public class FarmerDTO implements Serializable {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String name;
    @JsonProperty("legal_form")
    private String legalForm;
    private String inn;
    private String kpp;
    private String ogrn;
    @JsonProperty("register_district")
    private String registerDistrict;
    @JsonProperty("sowing_fields")
    private List<String> sowingFields;
    @JsonProperty("registration_date")
    private String registrationDate;
}
