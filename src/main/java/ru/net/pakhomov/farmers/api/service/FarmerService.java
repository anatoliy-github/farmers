package ru.net.pakhomov.farmers.api.service;

import ru.net.pakhomov.farmers.api.model.district.District;
import ru.net.pakhomov.farmers.api.model.farmer.LegalForm;
import ru.net.pakhomov.farmers.api.dto.FarmerDTO;
import ru.net.pakhomov.farmers.api.dto.FarmerMapper;
import ru.net.pakhomov.farmers.api.exception.farmer.FarmerInnAlreadyTakenException;
import ru.net.pakhomov.farmers.api.model.farmer.Farmer;
import ru.net.pakhomov.farmers.api.repository.DistrictRepository;
import ru.net.pakhomov.farmers.api.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.net.pakhomov.farmers.api.specification.FarmerSpecification;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FarmerService {
    private final FarmerRepository farmerRepository;
    private final DistrictRepository districtRepository;
    private final FarmerMapper farmerMapper;

    public Page<FarmerDTO> findAll(Optional<String> name,
                                   Optional<String> inn,
                                   Optional<Long> district,
                                   Optional<Date> date,
                                   Optional<Boolean> archive,
                                   Pageable pageable) {
        return farmerRepository.findAll(FarmerSpecification
                .filtersQuery(name, inn, district, date, archive), pageable)
                .map(farmerMapper::convertToDto);
    }

    public FarmerDTO getById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Farmer not found"));
        return farmerMapper.convertToDto(farmer);
    }

    public FarmerDTO createFarmer(FarmerDTO farmerDTO) {
        if(farmerRepository.existsByInn(farmerDTO.getInn())) {
            throw new FarmerInnAlreadyTakenException(farmerDTO.getInn());
        }
        District district = districtRepository.findByNameContaining(farmerDTO.getRegisterDistrict())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"District not found"));
        List<String> namesSowingFields = farmerDTO.getSowingFields().stream().collect(Collectors.toList());
        List<District> sowingFields = new ArrayList<>();
        for(String name : namesSowingFields) {
            District sowingField = districtRepository.findByNameContaining(name)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"District not found"));
            sowingFields.add(sowingField);
        }
        Farmer farmer = farmerMapper.convertToEntity(farmerDTO);

        farmer.setRegisterDistrict(district);
        farmer.setSowingFields(sowingFields);

        return farmerMapper.convertToDto(farmerRepository.save(farmer));
    }

    public FarmerDTO updateFarmer(Long id, FarmerDTO farmerDTO) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Farmer not found"));
        District district = districtRepository.findByNameContaining(farmerDTO.getRegisterDistrict())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"District not found"));
        farmer.setName(farmerDTO.getName());
        farmer.setInn(farmerDTO.getInn());
        farmer.setLegalForm(LegalForm.fromCode(farmerDTO.getLegalForm()));
        farmer.setRegisterDistrict(district);

        List<String> namesSowingFields = farmerDTO.getSowingFields();
        List<District> sowingFields = new ArrayList<>();
        for (String name : namesSowingFields) {
            District currentDistrict = districtRepository.findByNameContaining(name)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"District not found"));
            if(currentDistrict != null) {
                sowingFields.add(currentDistrict);
            }
        }

        farmer.setSowingFields(sowingFields);

        return farmerMapper.convertToDto(farmerRepository.save(farmer));
    }

    public void deleteFarmer(Long id) {
        farmerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Farmer not found"));
        farmerRepository.setArchiveTrue(id);
    }

}
