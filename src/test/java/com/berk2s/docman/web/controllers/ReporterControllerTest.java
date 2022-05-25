package com.berk2s.docman.web.controllers;

import com.berk2s.docman.web.models.Report;
import com.berk2s.docman.web.models.ReportRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReporterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ReportRequestDto reportRequestDto;

    @BeforeEach
    void setUp() {
        reportRequestDto = ReportRequestDto.builder()
                .reportName(Report.CC)
                .reportData(Map.of("title", "my title", "FIRST_NAME", "my first name", "LAST_NAME", "my last name"))
                .build();
    }

    @DisplayName("When request is valid then generate response successfully")
    @Test
    void whenRequestIsValidThenGenerateResponseSuccessfully() throws Exception {
        mockMvc.perform(post(ReporterController.ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportRequestDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk());
    }

}