package ru.net.pakhomov.farmers.api.dto;

import ru.net.pakhomov.farmers.api.model.farmer.Farmer;
import ru.net.pakhomov.farmers.api.model.farmer.LegalForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FarmerMapper {
    @Autowired
    private ModelMapper mapper;

    public FarmerDTO convertToDto(Farmer farmer) {
        String code = farmer.getLegalForm().getCode();
        Date registrationDate = farmer.getRegistrationDate();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(registrationDate);
        return mapper.typeMap(Farmer.class, FarmerDTO.class)
                .addMappings(m -> {
                    m.map(src -> code, FarmerDTO::setLegalForm);
                    m.map(src -> src.getRegisterDistrict().getName(), FarmerDTO::setRegisterDistrict);
                    m.map(Farmer::getListDistrictNameSowingFields, FarmerDTO::setSowingFields);
                    m.map(src -> date, FarmerDTO::setRegistrationDate);
                }).map(farmer);
    }

    public Farmer convertToEntity(FarmerDTO farmerDTO) {
        String code = farmerDTO.getLegalForm();
        return mapper.typeMap(FarmerDTO.class, Farmer.class)
                .addMappings(m -> {
                    m.map(src -> LegalForm.fromCode(code), Farmer::setLegalForm);
                }).map(farmerDTO);
    }
}
