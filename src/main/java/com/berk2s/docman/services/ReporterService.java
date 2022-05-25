package com.berk2s.docman.services;

import com.berk2s.docman.web.exceptions.ReporterException;
import com.berk2s.docman.web.models.ReportRequestDto;

/**
 * ReporterService basically generates reporter under a reporter library
 * Does abstraction for using 3rd party reporeter library
 */
public interface ReporterService {

    /**
     * Generates report as PDF
     * @param reportRequest Contains information about report request
     * @throws ReporterException will be thrown when an error occurred
     * @return bytes of PDF file
     */
    byte[] generateReportAsPdf(ReportRequestDto reportRequest) throws ReporterException;

}
