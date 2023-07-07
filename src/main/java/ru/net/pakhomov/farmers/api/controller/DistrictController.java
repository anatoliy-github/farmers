package ru.net.pakhomov.farmers.api.controller;

import ru.net.pakhomov.farmers.api.dto.DistrictDTO;
import ru.net.pakhomov.farmers.api.service.DistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/districts")
@RequiredArgsConstructor
@Tag(name = "Районы", description = "Получение, создание, изменение и удаление(в архив) районов")
public class DistrictController {
    private final DistrictService districtService;

    @GetMapping
    @Operation(summary = "Получить всех районов с пагинацией, а также фильтр по name, code")
    public List<DistrictDTO> getAll(
            @RequestParam(value = "page", defaultValue = "${app.pagination.district.first}", required = false) int page,
            @RequestParam(value = "size", defaultValue = "${app.pagination.district.size}", required = false) int size,
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "code", required = false) Optional<String> code,
            @RequestParam(value = "archive", required = false) Optional<Boolean> archive) {
        PageRequest pageable = PageRequest.of(page - 1, size);

        //by default archive = false
        if(!archive.isPresent()) {
            archive = Optional.of(false);
        }

        return districtService.findAll(archive, name, code, pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить район по его ID")
    public ResponseEntity<DistrictDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(districtService.getDistrictById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать новый район")
    public ResponseEntity<DistrictDTO> create(@RequestBody DistrictDTO district) {
        return new ResponseEntity<>(districtService.createDistrict(district), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить район по его ID")
    public ResponseEntity<DistrictDTO> update(@PathVariable Long id, @RequestBody DistrictDTO district) {
        return new ResponseEntity<>(districtService.updateDistrict(id, district), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Отправить район в архив по его ID")
    public ResponseEntity<String> sendToArchive(@PathVariable Long id) {
        districtService.sendToArchive(id);
        return new ResponseEntity<>("District successfully send to archive", HttpStatus.OK);
    }
}
