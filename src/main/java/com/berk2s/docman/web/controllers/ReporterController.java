package com.berk2s.docman.web.controllers;

import com.berk2s.docman.services.ReporterService;
import com.berk2s.docman.web.models.ErrorResponseDto;
import com.berk2s.docman.web.models.ReportRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

/**
 * ReporterController handles incoming request to /report to corresponding HTTP method types
 */
@Tag(name = "Reporter Endpoints", description = "Generate dynamic reports")
@RequiredArgsConstructor
@RequestMapping(ReporterController.ENDPOINT)
@RestController
public class ReporterController {

    /**
     * Reporter Endpoint
     */
    public static final String ENDPOINT = "/report";

    /**
     * ReporterService injection
     */
    private final ReporterService reporterService;

    /**
     * Handles incoming create report request, and creates report as pdf then returns it
     *
     * @param reportRequestDto Request body that contains template fields
     * @return PDF as btye
     */
    @Operation(method = "POST",
            summary = "Creates report as PDF",
            description = "Creates report file in PDF format. Since reporter template already created, you must pass values that will be displayed in PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report is created", content = {
                    @Content(mediaType = "application/octet-stream")
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input or malformed data", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> createReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", reportRequestDto.getReportName() + "-" + Instant.now().getEpochSecond() + ".pdf");

        return new ResponseEntity<>(reporterService.generateReportAsPdf(reportRequestDto), headers, HttpStatus.OK);
    }


}
