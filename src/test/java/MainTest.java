package test.java;

import org.junit.Test;
import test.java.api.ListingApiHandlerTest;
import test.java.api.MainAPIHandlerTest;
import test.java.dao.DaoTest;
import test.java.error.InvalidListingLoggerTest;
import test.java.ftp.FtpHandlerTest;
import test.java.report.ReportHandlerTest;

public class MainTest {

    @Test
    public void Test() throws Exception {
        MainAPIHandlerTest.Test();
        ListingApiHandlerTest.Test();
        InvalidListingLoggerTest.Test();
        DaoTest.Test();
        ReportHandlerTest.Test();
        FtpHandlerTest.Test();
    }
}
