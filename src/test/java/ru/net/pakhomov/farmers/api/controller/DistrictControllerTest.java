package ru.net.pakhomov.farmers.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.net.pakhomov.farmers.api.model.district.District;
import ru.net.pakhomov.farmers.api.service.DistrictService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistrictController.class)
class DistrictControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    DistrictService districtService;
    @Autowired
    ObjectMapper mapper;
    private District district1;
    private District district2;
    private DistrictDTO districtDTO1;
    private DistrictDTO districtDTO2;
    private List<District> districts = new ArrayList<>();
    private List<DistrictDTO> districtsDTO = new ArrayList<>();

    @BeforeEach
    public void init() {
        district1 = new District(1L, "Алексеевский", "001", false, null);
        district2 = new District(2L, "Андреевский", "002", false, null);
        districtDTO1 = new DistrictDTO(1L, "Алексеевский", "001");
        districtDTO2 = new DistrictDTO(2L, "Андреевский", "002");
        districts.add(district1);
        districts.add(district2);
        districtsDTO.add(districtDTO1);
        districtsDTO.add(districtDTO2);
    }

    @Test
    void getAllDistricts_ReturnPageDTO() throws Exception {
        Page<DistrictDTO> districtDTOPage = mock(Page.class);
        when(districtDTOPage.getContent()).thenReturn(districtsDTO);
        when(districtService
                .findAll(ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Optional.class), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(districtDTOPage);

        ResultActions response = mockMvc.perform(get("/api/districts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(districtsDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Алексеевский"));
    }

    @Test
    void getDistrictById_ReturnDTO() throws Exception {
        when(districtService.getDistrictById(1L)).thenReturn(districtDTO1);

        ResultActions response = mockMvc.perform(get("/api/districts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(districtDTO1)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("001"));
    }

    @Test
    void createDistrict_Return_201() throws Exception {
        given(districtService.createDistrict(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/districts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(districtDTO1)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Алексеевский"))
                .andExpect(jsonPath("$.code").value("001"));
    }

    @Test
    void updateDistrictById_ReturnDTO() throws Exception {
        DistrictDTO editedDistrict01 = new DistrictDTO(null, "Волковский", "003");
        DistrictDTO updatedDistrict01 = new DistrictDTO(1L, "Волковский", "003");
        when(districtService.updateDistrict(1L, editedDistrict01)).thenReturn(updatedDistrict01);

        ResultActions response = mockMvc.perform(put("/api/districts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedDistrict01)));

        response.andExpect(status().isOk());
    }

    @Test
    void delete_sendToArchive() {

    }
}