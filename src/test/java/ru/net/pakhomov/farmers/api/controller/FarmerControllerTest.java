package ru.net.pakhomov.farmers.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.net.pakhomov.farmers.api.dto.DistrictDTO;
import ru.net.pakhomov.farmers.api.dto.FarmerDTO;
import ru.net.pakhomov.farmers.api.model.district.District;
import ru.net.pakhomov.farmers.api.model.farmer.Farmer;
import ru.net.pakhomov.farmers.api.model.farmer.LegalForm;
import ru.net.pakhomov.farmers.api.service.FarmerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FarmerController.class)
class FarmerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    FarmerService farmerService;
    private District district1;
    private District district2;
    private DistrictDTO districtDTO1;
    private DistrictDTO districtDTO2;
    private Farmer farmer1;
    private Farmer farmer2;
    private List<Farmer> farmers = new ArrayList<>();
    private FarmerDTO farmerDTO1;
    private FarmerDTO farmerDTO2;
    private List<FarmerDTO> farmersDTO = new ArrayList<>();
    private List<District> sowingFields = new ArrayList<>();
    private List<String> sowingFieldsDTO = new ArrayList<>();

    @BeforeEach
    public void init() {
        district1 = new District(1L, "Алексеевский", "001", false, null);
        district2 = new District(2L, "Андреевский", "002", false, null);
        districtDTO1 = new DistrictDTO(1L, "Алексеевский", "001");
        districtDTO2 = new DistrictDTO(2L, "Андреевский", "002");
        sowingFields.add(district1);
        sowingFields.add(district2);
        sowingFieldsDTO.add("Алексеевский");
        sowingFieldsDTO.add("Андреевский");
        farmer1 = new Farmer(1L, "Агрофирма", LegalForm.COMPANY, "7859340912", "307569456", "23954595864343", district1, sowingFields, false, new Date(2022,12,12));
        farmer2 = new Farmer(2L, "Иванов Иван", LegalForm.INDIVIDUAL, "7864340912", "307569434", "23954595864357", district2, sowingFields, false, new Date(2021,06,06));
        farmerDTO1 = new FarmerDTO(1L, "Агрофирма", "ЮЛ", "7859340912", "307569456", "23954595864343", "Алексевский", sowingFieldsDTO, "2022-12-12");
        farmerDTO2 = new FarmerDTO(2L, "Иванов Иван", "ИП", "7864340912", "307569434", "23954595864357", "Андреевский", sowingFieldsDTO, "2021-06-06");
        farmers.add(farmer1);
        farmers.add(farmer2);
        farmersDTO.add(farmerDTO1);
        farmersDTO.add(farmerDTO2);
    }

    @Test
    void createFarmer_Return_201() throws Exception {
        given(farmerService.createFarmer(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/farmers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(farmerDTO1)));
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(farmerDTO1.getName())))
                .andExpect(jsonPath("$.legal_form", CoreMatchers.is(farmerDTO1.getLegalForm())))
                .andExpect(jsonPath("$.inn", CoreMatchers.is(farmerDTO1.getInn())))
                .andExpect(jsonPath("$.kpp", CoreMatchers.is(farmerDTO1.getKpp())))
                .andExpect(jsonPath("$.ogrn", CoreMatchers.is(farmerDTO1.getOgrn())))
                .andExpect(jsonPath("$.register_district", CoreMatchers.is(farmerDTO1.getRegisterDistrict())));
    }

    @Test
    void getAllFarmers_ReturnPageDTO() throws Exception {
        Page<FarmerDTO> farmerDTOPage = mock(Page.class);
        when(farmerDTOPage.getContent()).thenReturn(farmersDTO);
        when(farmerService.findAll(ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Pageable.class))).thenReturn(farmerDTOPage);

        ResultActions response = mockMvc.perform(get("/api/farmers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(farmersDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", CoreMatchers.is(farmerDTO1.getName())));
    }

    @Test
    void getFarmerById_ReturnDTO() throws Exception {
        when(farmerService.getById(1L)).thenReturn(farmerDTO1);

        ResultActions response = mockMvc.perform(get("/api/farmers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(farmerDTO1)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(farmerDTO1.getName())));
    }

    @Test
    void updateFarmerById_ReturnOk() throws Exception {
        when(farmerService.updateFarmer(2L, farmerDTO1)).thenReturn(farmerDTO1);

        ResultActions response = mockMvc.perform(put("/api/farmers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(farmerDTO1)));

        response.andExpect(status().isOk());
    }

    @Test
    void delete_sendToArchive() {

    }
}