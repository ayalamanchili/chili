/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.reporting;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author anuyalamanchili
 */
public class ReportGenerator {

    public static <T> void generateReport(List<T> data, String format, String filepath) throws JRException {
        DynamicReport dynamicReport = null;
        if (data.size() > 0) {
            dynamicReport = new ReflectiveReportBuilder(data).build();
            dynamicReport.setTitle(data.get(0).getClass().getSimpleName());
            JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), data);
            if (format.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, filepath);
            }
            if (format.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, filepath);
            }
            if (format.equalsIgnoreCase("xml")) {
                JasperExportManager.exportReportToXmlFile(jasperPrint, filepath, false);
            }

        }
    }
}
