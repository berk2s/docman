package com.berk2s.docman.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * ReportRequestDto
 * Requests that incoming to reporter endpoint must contain this format and schema
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequestDto {

    /**
     * Report name indicates the name of report that will be generated
     */
    @JsonProperty("reportName")
    @NotNull(message = "Report name is mandatory")
    private Report reportName;

    /**
     * Contains key-value pair
     * Key holds reporter template field, whereas value is corresponding value of key
     */
    @JsonProperty("reportData")
    @NotNull(message = "Report data is mandatory")
    @Size(min = 1, max = 99, message = "Report data must be between 1 and 99")
    private Map<String, Object> reportData;

}
