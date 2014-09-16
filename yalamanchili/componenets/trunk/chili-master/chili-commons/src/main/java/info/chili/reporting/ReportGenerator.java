/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.reporting;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import info.chili.commons.PDFUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author anuyalamanchili
 */
public class ReportGenerator {
//TODO support for email report

    public static <T> Response generateReport(List<T> data, String reportName, String format, String location) {
        DynamicReport dynamicReport = null;
        if (Strings.isNullOrEmpty(reportName)) {
            reportName = "report";
        }
        String filename = reportName + UUID.randomUUID().toString() + "." + format;
        if (data.size() > 0) {
            dynamicReport = new ReflectiveReportBuilder(data).build();
            try {
                dynamicReport.setTitle(data.get(0).getClass().getSimpleName());
                JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), data);
                if (format.equalsIgnoreCase("pdf")) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, location + filename);
                }
                if (format.equalsIgnoreCase("html")) {
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, location + filename);
                }
                if (format.equalsIgnoreCase("xml")) {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, location + filename, false);
                }
                if (format.equalsIgnoreCase("xls")) {
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                            jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                            location + filename);
                    exporter.exportReport();
                }
            } catch (JRException ex) {
                ex.printStackTrace();
                return Response.serverError().build();
            }
        }
        return Response.ok(filename.getBytes()).build();
    }

    public static <T> String generateExcelReport(List<T> data, String reportName, String location) {
        DynamicReport dynamicReport = null;
        String filename = reportName + UUID.randomUUID().toString() + ".xls";
        if (data.size() > 0) {
            dynamicReport = new ReflectiveReportBuilder(data).build();
            try {
                dynamicReport.setTitle(data.get(0).getClass().getSimpleName());
                JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), data);
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                        jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                        location + filename);
                exporter.exportReport();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return filename;
    }

    /**
     * converts html generated from templating service or other to pdf
     *
     * @param html
     * @param reportName
     * @return
     */
    public static Response generatePDFReportFromHtml(String html, String reportName) {
        byte[] pdf = PDFUtils.convertToPDF(html);
        return Response
                .ok(pdf)
                .header("content-disposition", "filename = " + reportName + ".pdf")
                .header("Content-Type", "application/pdf")
                .header("Content-Length", pdf.length)
                .build();
    }

    /**
     * converts html generated from templating service or other to pdf repot on
     * server and return the name
     *
     * @param html
     * @param reportName
     * @return
     */
    public static Response createPDFReportFromHtml(String html, String reportName, String reportLocation) {
        byte[] pdf = PDFUtils.convertToPDF(html);
        File file = new File(reportLocation + reportName + UUID.randomUUID().toString() + ".pdf");
        try {
            Files.write(pdf, file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return Response.ok(reportName + ".pdf".getBytes()).header("content-disposition", "inline; filename = " + reportName + ".pdf")
                .header("Content-Length", reportName + ".pdf".length()).build();
    }

}
