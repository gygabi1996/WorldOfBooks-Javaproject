package test.java.report;

import main.java.dao.DbHandler;
import main.java.entity.ReportEntity;
import main.java.report.ReportHandler;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ReportHandlerTest {

    @Test
    public static void Test() throws SQLException, IOException, ClassNotFoundException {
        /**
         * Get datas from database and save into the report.json
         */
        ReportEntity reportEntity = DbHandler.getReportDatasFromDatabase();
        assertNotNull(reportEntity);

        ReportHandler.createReport(reportEntity);

        /**
         * Check the file is created
         */
        File file = new File("json/report.JSON");
        assertNotNull(file);
    }
}
