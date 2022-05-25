package com.berk2s.docman.services;

import com.berk2s.docman.services.impl.ReporterServiceImpl;
import com.berk2s.docman.web.models.Report;
import com.berk2s.docman.web.models.ReportRequestDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReporterServiceTest {

    @InjectMocks
    ReporterServiceImpl reporterService;

    @DisplayName("Generates report successfully")
    @Test
    void generateReportAsPdf() throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("title", "my title");
        map.put("FIRST_NAME", "first name");
        map.put("LAST_NAME", "last name");

        ReportRequestDto reportRequest = ReportRequestDto.builder()
                .reportName(Report.CC)
                .reportData(map)
                .build();

        byte[] pdfAsByte = reporterService.generateReportAsPdf(reportRequest);

        PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfAsByte));

        try {
            var pdf = new PDFTextStripper().getText(pdfDocument);
            assertThat(pdf).contains("first name").contains("last name");
        } finally {
            pdfDocument.close();
        }
    }
}