package ru.net.pakhomov.farmers.api.service;

import ru.net.pakhomov.farmers.api.dto.DistrictDTO;
import ru.net.pakhomov.farmers.api.exception.district.DistrictCodeIsAlreadyTakenException;
import ru.net.pakhomov.farmers.api.exception.district.DistrictIsAlreadyTakenException;
import ru.net.pakhomov.farmers.api.exception.district.DistrictNameIsAlreadyTakenException;
import ru.net.pakhomov.farmers.api.model.district.District;
import ru.net.pakhomov.farmers.api.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.net.pakhomov.farmers.api.specification.DistrictSpecification;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    public Page<DistrictDTO> findAll(Optional<Boolean> archive, Optional<String> name, Optional<String> code, Pageable pageable) {
        return districtRepository
                .findAll(DistrictSpecification.filtersQuery(archive, name, code), pageable)
                .map(district -> modelMapper.map(district, DistrictDTO.class));
    }

    public DistrictDTO getDistrictById(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District Not found"));
        return modelMapper.map(district, DistrictDTO.class);
    }

    public DistrictDTO createDistrict(DistrictDTO districtDTO) {
        if (districtRepository.existsByCode(districtDTO.getCode()) && districtRepository.existsByName(districtDTO.getName())) {
            throw new DistrictIsAlreadyTakenException(districtDTO.getName(), districtDTO.getCode());
        } else if (districtRepository.existsByCode(districtDTO.getCode())) {
            throw new DistrictCodeIsAlreadyTakenException(districtDTO.getCode());
        } else if (districtRepository.existsByName(districtDTO.getName())) {
            throw new DistrictNameIsAlreadyTakenException(districtDTO.getName());
        }
        District district = modelMapper.map(districtDTO, District.class);
        return modelMapper.map(districtRepository.save(district), DistrictDTO.class);
    }

    public DistrictDTO updateDistrict(Long id, DistrictDTO districtDTO) {
        District existingDistrict = districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District Not found"));
        existingDistrict.setName(districtDTO.getName());
        existingDistrict.setCode(districtDTO.getCode());
        return modelMapper.map(districtRepository.save(existingDistrict), DistrictDTO.class);
    }

    public void sendToArchive(Long id) {
        districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District Not found"));
        districtRepository.setArchiveTrue(id);
    }
}
