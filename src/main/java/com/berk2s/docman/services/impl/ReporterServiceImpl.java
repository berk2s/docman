package com.berk2s.docman.services.impl;

import com.berk2s.docman.services.ReporterService;
import com.berk2s.docman.web.exceptions.ReporterException;
import com.berk2s.docman.web.models.ReportRequestDto;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * Implementation of ReporterService
 * Uses Jasper library for reporting purposes
 */
@Slf4j
@Service
public class ReporterServiceImpl implements ReporterService {

    /**
     * @throws ReporterException
     */
    @Override
    public byte[] generateReportAsPdf(ReportRequestDto reportRequest) {
        try {
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(JasperCompileManager
                                    .compileReport(ResourceUtils.getFile("classpath:simple-template.jrxml").getAbsolutePath()),
                            reportRequest.getReportData(),
                            new JREmptyDataSource());

            jasperPrint.setName(reportRequest.getReportName().name());

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (FileNotFoundException | JRException e) {
            log.info("Error while generating report [message: {}]", e.getMessage());
            throw new ReporterException("Error occurred while generating report");
        }
    }

}
