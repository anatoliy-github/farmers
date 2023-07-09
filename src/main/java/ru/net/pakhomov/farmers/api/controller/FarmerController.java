package ru.net.pakhomov.farmers.api.controller;

import ru.net.pakhomov.farmers.api.dto.FarmerDTO;
import ru.net.pakhomov.farmers.api.service.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/farmers")
@RequiredArgsConstructor
@Tag(name = "Фермеры", description = "Получение, создание, изменение и удаление(в архив) фермеров")
public class FarmerController {
    private final FarmerService farmerService;

    @GetMapping
    @Operation(summary = "Получить всех фермеров с пагинацией, а также поиск по name, inn, district", description = "Для поиска используйте параметр search. Например: ?search=inn=123&kpp=567")
    public List<FarmerDTO> getAll(
            @RequestParam(value = "page", defaultValue = "${app.pagination.farmer.first}", required = false) int page,
            @RequestParam(value = "size", defaultValue = "${app.pagination.farmer.size}", required = false) int size,
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "inn", required = false) Optional<String> inn,
            @RequestParam(value = "district", required = false) Optional<Long> district,
            @RequestParam(value = "date", required = false) Optional<Date> date,
            @RequestParam(value = "archive", required = false) Optional<Boolean> archive) {
        PageRequest pageable = PageRequest.of(page - 1, size);

        //by default archive = false
        if(!archive.isPresent()) {
            archive = Optional.of(false);
        }

        return farmerService.findAll(name, inn, district, date, archive, pageable).getContent();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получить фермера по ID")
    public ResponseEntity<FarmerDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(farmerService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать нового фермера")
    public ResponseEntity<FarmerDTO> create(@RequestBody FarmerDTO farmerDTO) {
        return new ResponseEntity<>(farmerService.createFarmer(farmerDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Изменить данные фермера по его ID")
    public ResponseEntity<FarmerDTO> update(@PathVariable Long id, @RequestBody FarmerDTO farmerDTO) {
        return new ResponseEntity<>(farmerService.updateFarmer(id, farmerDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Отправить фермера в архив по его ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return new ResponseEntity<>("Farmer successfully send to archive", HttpStatus.OK);
    }
}
